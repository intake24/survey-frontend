/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

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

package uk.ac.ncl.openlab.intake24.client.survey;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.RequestTimeoutException;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Callback2;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;
import uk.ac.ncl.openlab.intake24.client.LoadingPanel;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyFollowUp;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyService;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.UxEventsHelper;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.List;

public class FlatFinalPage implements SurveyStage<Survey> {
    private final static PromptMessages messages = PromptMessages.Util.getInstance();
    private final static SurveyMessages surveyMessages = SurveyMessages.Util.getInstance();

    private final String finalPageHtml;
    private final Survey data;
    private final List<String> log;

    public FlatFinalPage(String finalPageHtml, Survey data, List<String> log) {
        this.finalPageHtml = finalPageHtml;
        this.data = data;
        this.log = log;
    }

    @Override
    public SimpleSurveyStageInterface getInterface(Callback1<Survey> onComplete, Callback2<Survey, Boolean> onIntermediateStateChange) {
        final CompletedSurvey finalData = data.finalise(log);
        final FlowPanel contents = new FlowPanel();
        contents.addStyleName("intake24-survey-content-container");

        contents.add(new LoadingPanel(messages.submitPage_loadingMessage()));

        SurveyService.INSTANCE.submitSurvey(EmbeddedData.surveyId, finalData, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                contents.clear();

                if (exception instanceof RequestTimeoutException) {
                    final MethodCallback<Void> outer = this;

                    contents.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.submitPage_timeout())));

                    contents.add(WidgetFactory.createGreenButton(messages.submitPage_tryAgainButton(), "finalPageTryAgainButton", new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            SurveyService.INSTANCE.submitSurvey(EmbeddedData.surveyId, finalData, outer);
                        }
                    }));
                } else {
                    contents.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.submitPage_error())));
                }

                contents.add(new HTMLPanel(finalPageHtml));
            }

            @Override
            public void onSuccess(Method method, Void response) {
                contents.clear();
                contents.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.submitPage_success())));

                SurveyService.INSTANCE.getFollowUpUrl(EmbeddedData.surveyId, new MethodCallback<SurveyFollowUp>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        contents.add(new HTMLPanel(SafeHtmlUtils.fromSafeConstant(messages.submitPage_error())));
                    }

                    @Override
                    public void onSuccess(Method method, SurveyFollowUp surveyFollowUp) {
                        surveyFollowUp.followUpUrl.accept(new Option.SideEffectVisitor<String>() {

                            @Override
                            public void visitSome(String url) {
                                FlowPanel externalLinkDiv = new FlowPanel();

                                externalLinkDiv.add(WidgetFactory.createGreenButton(surveyMessages.finalPage_continueToSurveyMonkey(), "finalPageExternalUrlButton", new ClickHandler() {
                                    @Override
                                    public void onClick(ClickEvent clickEvent) {
                                        Window.Location.replace(url);
                                    }
                                }));

                                contents.add(externalLinkDiv);
                            }

                            @Override
                            public void visitNone() {
                                if (surveyFollowUp.showFeedback) {
                                    UrlBuilder builder = Window.Location.createUrlBuilder();

                                    for (String paramName : Window.Location.getParameterMap().keySet()) {
                                        builder.removeParameter(paramName);
                                    }

                                    builder.setHash("/thanks");

                                    builder.setPath(Window.Location.getPath() + "/feedback");

                                    Window.Location.replace(builder.buildString());
                                }
                            }
                        });

                    }
                });

                contents.add(new HTMLPanel(finalPageHtml));

                UxEventsHelper.cleanSessionId();
                StateManagerUtil.clearLatestState(AuthCache.getCurrentUserId());
            }
        });

        return new SimpleSurveyStageInterface(contents, FlatFinalPage.class.getSimpleName());
    }
}