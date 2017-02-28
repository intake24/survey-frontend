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
/*
import java.util.Date;
import java.util.logging.Logger;

import org.workcraft.gwt.shared.client.Callback;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Option;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

import net.scran24.common.client.CommonMessages;
import net.scran24.common.client.CurrentUser;
import net.scran24.common.client.LoginForm;
import net.scran24.common.client.LoginServiceAsync;
import net.scran24.common.client.NavigationBar;
import net.scran24.common.client.survey.TutorialVideo;

import net.scran24.user.client.survey.SurveyMessages;
import net.scran24.user.client.survey.prompts.FoodLookupPrompt;
import net.scran24.user.client.surveyscheme.SurveyScheme;
import net.scran24.user.client.surveyscheme.SurveySchemeMap;
*/

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class SurveyEntryPoint implements EntryPoint {
    /* final private Logger log = Logger.getLogger("Init");

    private final SurveyMessages surveyMessages = SurveyMessages.Util.getInstance();
    private final CommonMessages commonMessages = CommonMessages.Util.getInstance();

    private LoginServiceAsync loginService = LoginServiceAsync.Util.getInstance(); */

    // private Element mainContent;

    private native void initComplete() /*-{
        if (typeof $wnd.intake24_initComplete == 'function')
            $wnd.intake24_initComplete();
    }-*/;

	/*public void initPage(final UserInfo userInfo) {
        final RootPanel links = RootPanel.get("navigation-bar");

		Anchor watchTutorial = new Anchor(surveyMessages.navBar_tutorialVideo(), TutorialVideo.url, "_blank");

		Anchor logOut = new Anchor(surveyMessages.navBar_logOut(), "../../common/logout" + Location.getQueryString());

		// These divs are no longer used for content, but this code is left here
		// to handle legacy survey files

		Element se = Document.get().getElementById("suspended");
		if (se != null)
			se.removeFromParent();
		Element ae = Document.get().getElementById("active");
		if (ae != null)
			ae.removeFromParent();
		Element fe = Document.get().getElementById("finished");
		if (fe != null)
			fe.removeFromParent();
		Element ee = Document.get().getElementById("serverError");
		if (ee != null)
			ee.removeFromParent();
		Element fpe = Document.get().getElementById("finalPage");
		if (fpe != null)
			fpe.removeFromParent();

		mainContent = Document.get().getElementById("main-content");
		mainContent.setInnerHTML("");

		HTMLPanel mainContentPanel = HTMLPanel.wrap(mainContent);

		switch (userInfo.surveyParameters.state) {
		case NOT_INITIALISED:
			mainContentPanel.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(surveyMessages.survey_notInitialised())));
			links.add(new NavigationBar(watchTutorial, logOut));
			break;
		case SUSPENDED:
			mainContentPanel.add(new HTMLPanel(SafeHtmlUtils
					.fromSafeConstant(surveyMessages.survey_suspended(SafeHtmlUtils.htmlEscape(userInfo.surveyParameters.suspensionReason)))));
			links.add(new NavigationBar(watchTutorial, logOut));
			break;
		case ACTIVE:
			Date now = new Date();

			if (now.getTime() > userInfo.surveyParameters.endDate) {
				mainContentPanel.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(surveyMessages.survey_finished())));
			} else {
				SurveyInterfaceManager surveyInterfaceManager = new SurveyInterfaceManager(mainContentPanel);

				SurveyScheme scheme = SurveySchemeMap.initScheme(SurveySchemes.schemeForId(userInfo.surveyParameters.schemeName),
						LocaleInfo.getCurrentLocale().getLocaleName(), surveyInterfaceManager);

				links.add(new NavigationBar(scheme.navBarLinks(), watchTutorial, logOut));

				scheme.showNextPage();
			}
			break;
		}

		RootPanel.get("loading").getElement().removeFromParent();

		initComplete();
	}*/

    public void onModuleLoad() {

        Window.alert("hi");


    }

}