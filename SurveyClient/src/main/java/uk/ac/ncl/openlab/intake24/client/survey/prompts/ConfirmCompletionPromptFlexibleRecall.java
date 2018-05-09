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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.Date;

public class ConfirmCompletionPromptFlexibleRecall implements Prompt<Survey, SurveyOperation> {

    private final Integer MIN_SUBMIT_HOUR = 21;
    private final Integer MIN_SUBMIT_MINUTE = 30;

    private final PromptMessages messages = GWT.create(PromptMessages.class);

    private final StateManager stateManager;

    public ConfirmCompletionPromptFlexibleRecall(final StateManager sm) {
        stateManager = sm;
    }

    @Override
    public SurveyStageInterface getInterface(final Callback1<SurveyOperation> onComplete,
                                             final Callback1<Function1<Survey, Survey>> onIntermediateStateChange) {
        final SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(messages.completion_promptText());
        boolean timeIsValid = currentTimeIsValid();

        FlowPanel content = new FlowPanel();

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
        });

        content.add(WidgetFactory.createPromptPanel(promptText));
        if (!timeIsValid) {
            FlowPanel alertPanel = createAlert("Please submit your recall no earlier than " + MIN_SUBMIT_HOUR + ":" + MIN_SUBMIT_MINUTE);
            content.add(alertPanel);
            confirm.getElement().setAttribute("disabled", "disabled");
        }
        content.add(WidgetFactory.createButtonsPanel(confirm));

        return new SurveyStageInterface.Aligned(content, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP, SurveyStageInterface.DEFAULT_OPTIONS, ConfirmCompletionPromptFlexibleRecall.class.getSimpleName());
    }

    @Override
    public String toString() {
        return "Confirm survey completion prompt";
    }

    private boolean currentTimeIsValid() {
        int hour = Integer.parseInt(DateTimeFormat.getFormat("H").format(new Date()));
        int minute = Integer.parseInt(DateTimeFormat.getFormat("m").format(new Date()));
        int todayMonth = Integer.parseInt(DateTimeFormat.getFormat("MM").format(new Date()));
        int todayDay = Integer.parseInt(DateTimeFormat.getFormat("d").format(new Date()));
        Date surveyStartedDate = new Date(stateManager.getCurrentState().startTime);
        int startedDateMonth = Integer.parseInt(DateTimeFormat.getFormat("MM").format(surveyStartedDate));
        int startedDateDay = Integer.parseInt(DateTimeFormat.getFormat("d").format(surveyStartedDate));
        return !(todayMonth <= startedDateMonth && todayDay <= startedDateDay && (MIN_SUBMIT_HOUR > hour || MIN_SUBMIT_HOUR == hour && MIN_SUBMIT_MINUTE > minute));
    }

    private FlowPanel createAlert(String alertText) {
        FlowPanel result = new FlowPanel();
        HTMLPanel promptPanel = new HTMLPanel(alertText);
        promptPanel.addStyleName("intake24-alert");
        result.add(promptPanel);
        return result;
    }
}