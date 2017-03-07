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


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.CommonMessages;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;

public class ErrorPage extends Composite {

    private static final CommonMessages messages = CommonMessages.Util.getInstance();

    public ErrorPage(String title, String text) {

        FlowPanel container = new FlowPanel();

        container.addStyleName("intake24-error-page-container");

        HTMLPanel titlePanel = new HTMLPanel("h2", title);
        HTMLPanel textPanel = new HTMLPanel(text);

        container.add(titlePanel);
        container.add(textPanel);

        initWidget(container);
    }

    private static void showErrorPage(String title, String text) {
        ErrorPage page = new ErrorPage(title, text);
        Layout.setMainContent(page);
    }

    public static void showForbiddenErrorPage() {
        showErrorPage(messages.forbiddenErrorTitle(), messages.forbiddenErrorText(EmbeddedData.getSurveySupportEmail()));
    }

    public static void showInternalServerErrorPage() {
        showErrorPage(messages.serverErrorTitle(), messages.serverErrorText(EmbeddedData.getSurveySupportEmail()));
    }

    public static void showSurveyPendingPage() {
        showErrorPage(messages.surveyPendingTitle(), messages.surveyPendingText(EmbeddedData.getSurveySupportEmail()));
    }

    public static void showSurveyFinishedPage() {
        showErrorPage(messages.surveyFinishedTitle(), messages.surveyFinishedText(EmbeddedData.getSurveySupportEmail()));
    }

    public static void showSurveySuspendedPage(Option<String> reason) {

        String message = reason.map(r -> messages.surveySuspendedReason(r)).getOrElse("") + messages.surveySuspendedText(EmbeddedData.getSurveySupportEmail());

        showErrorPage(messages.surveySuspendedTitle(), messages.surveyFinishedText(EmbeddedData.getSurveySupportEmail()));
    }

}