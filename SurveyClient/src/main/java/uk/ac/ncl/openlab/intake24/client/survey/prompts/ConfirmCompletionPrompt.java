/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.survey.Prompt;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyStageInterface;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class ConfirmCompletionPrompt implements Prompt<Survey, SurveyOperation> {

    private final PromptMessages messages = GWT.create(PromptMessages.class);

    @Override
    public SurveyStageInterface getInterface(final Callback1<SurveyOperation> onComplete,
                                             final Callback1<Function1<Survey, Survey>> onIntermediateStateChange) {
        final SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(messages.completion_promptText());

        FlowPanel content = new FlowPanel();

        content.add(WidgetFactory.createPromptPanel(promptText));

        Button confirm = WidgetFactory.createGreenButton(messages.completion_submitButtonLabel(), "confirmCompletionButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(SurveyOperation.update(new Function1<Survey, Survey>() {
                    @Override
                    public Survey apply(Survey argument) {
                        return argument.markCompletionConfirmed();
                    }
                }));
            }
        }, "intake24-button-lg");

        content.add(WidgetFactory.createCenteredButtonsPanel(confirm));

        return new SurveyStageInterface.Aligned(content, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP, SurveyStageInterface.DEFAULT_OPTIONS, ConfirmCompletionPrompt.class.getSimpleName());
    }

    @Override
    public String toString() {
        return "Confirm survey completion prompt";
    }
}