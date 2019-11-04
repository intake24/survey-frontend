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

package uk.ac.ncl.openlab.intake24.client.ui;


import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import uk.ac.ncl.openlab.intake24.client.CommonMessages;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyMessages;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;


public class LogoutPage extends Composite {

    final private static CommonMessages messages = CommonMessages.Util.getInstance();

    public LogoutPage(String text) {
        FlowPanel container = new FlowPanel();
        container.getElement().addClassName("intake24-survey-container");

        FlowPanel header = new FlowPanel();
        header.getElement().addClassName("intake24-survey-header");

        HTMLPanel h1 = new HTMLPanel("h1", messages.logoutPage_title());
        header.add(h1);

        FlowPanel content = new FlowPanel();
        content.getElement().addClassName("intake24-survey-content");

        HTMLPanel el = new HTMLPanel(SafeHtmlUtils.fromSafeConstant(text));
        content.add(el);

        container.add(header);
        container.add(content);

        initWidget(container);
    }

    public static void logoutWithHelp() {
        Layout.createMainPageLayout();
        Layout.setNavBarLinks(new Anchor(SurveyMessages.INSTANCE.navBar_tutorialVideo(), TutorialVideo.url, "_blank"));
        LogoutPage page = new LogoutPage(messages.logoutPage_closeWithHelp(SafeHtmlUtils.htmlEscape(EmbeddedData.surveySupportEmail)));
        Layout.setMainContent(page);
    }

    public static void logout() {
        Layout.createMainPageLayout();
        Layout.setNavBarLinks(new Anchor(SurveyMessages.INSTANCE.navBar_tutorialVideo(), TutorialVideo.url, "_blank"));
        LogoutPage page = new LogoutPage(messages.logoutPage_close());
        Layout.setMainContent(page);
    }
}
