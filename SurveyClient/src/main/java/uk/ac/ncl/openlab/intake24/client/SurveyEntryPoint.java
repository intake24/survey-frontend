/*
This file is part of Intake24.

Copyright 2015, 2016, 2017 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyParameters;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyService;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyInterfaceManager;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyMessages;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.SurveyScheme;
import uk.ac.ncl.openlab.intake24.client.ui.ErrorPage;
import uk.ac.ncl.openlab.intake24.client.ui.Layout;
import uk.ac.ncl.openlab.intake24.client.ui.TutorialVideo;

import java.util.ArrayList;

public class SurveyEntryPoint implements EntryPoint {

    private native void initComplete() /*-{
        if (typeof $wnd.intake24_initComplete == 'function')
            $wnd.intake24_initComplete();
    }-*/;

    Anchor watchTutorial;
    Anchor logOut;

    private void startSurvey(SurveyParameters params) {
        SurveyInterfaceManager surveyInterfaceManager = new SurveyInterfaceManager(Layout.getMainContentPanel());

        SurveyScheme scheme = SurveyScheme.createScheme(params.schemeId, LocaleInfo.getCurrentLocale().getLocaleName(), surveyInterfaceManager);

        ArrayList<Anchor> navbarLinks = new ArrayList<>();

        navbarLinks.addAll(scheme.navBarLinks());
        navbarLinks.add(watchTutorial);
        navbarLinks.add(logOut);

        Layout.setNavBarLinks(navbarLinks);

        scheme.showNextPage();
    }

    public void onModuleLoad() {

        RootPanel.get("loading").getElement().removeFromParent();

        Defaults.setServiceRoot(EmbeddedData.getApiBaseUrl());

        watchTutorial = new Anchor(SurveyMessages.INSTANCE.navBar_tutorialVideo(), TutorialVideo.url, "_blank");

        logOut = new Anchor(SurveyMessages.INSTANCE.navBar_logOut());

        logOut.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                AuthCache.clear();
                Window.Location.reload();
            }
        });

        SurveyService.INSTANCE.getSurveyParameters(EmbeddedData.getSurveyId(), new MethodCallback<SurveyParameters>() {
            @Override
            public void onFailure(Method method, Throwable exception) {

                Layout.createMainPageLayout();
                Layout.setNavBarLinks(logOut);

                switch (method.getResponse().getStatusCode()) {
                    case 403:
                        ErrorPage.showForbiddenErrorPage();
                        break;
                    default:
                        ErrorPage.showInternalServerErrorPage();
                        break;
                }
            }

            @Override
            public void onSuccess(Method method, SurveyParameters response) {
                Layout.createMainPageLayout();
                Layout.setNavBarLinks(watchTutorial, logOut);

                switch (response.state) {
                    case "running":
                        startSurvey(response);
                        break;
                    case "pending":
                        ErrorPage.showSurveyPendingPage();
                        break;
                    case "finished":
                        ErrorPage.showSurveyFinishedPage();
                        break;
                    case "suspended":
                        ErrorPage.showSurveySuspendedPage(response.suspensionReason);
                        break;
                    default:
                        ErrorPage.showInternalServerErrorPage();
                }
            }
        });
    }
}