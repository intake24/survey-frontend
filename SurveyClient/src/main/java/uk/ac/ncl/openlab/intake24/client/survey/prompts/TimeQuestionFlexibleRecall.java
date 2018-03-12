/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.UxEventsHelper;
import uk.ac.ncl.openlab.intake24.client.survey.Time;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;
import uk.ac.ncl.openlab.intake24.client.ui.widgets.TimePicker;

import java.util.Date;

public class TimeQuestionFlexibleRecall extends Composite {

    /***
     * Experimental. Flexible recall. Time question that notifies if selected time is earlier than current time.
     */

    public final FlowPanel promptPanel;
    public final FlowPanel alertPanel;
    public final TimePicker timePicker;
    public final Button confirmButton;
    public final Button skipButton;

    public interface ResultHandler {
        public void handleAccept(Time time);

        public void handleSkip();
    }

    public TimeQuestionFlexibleRecall(final SafeHtml questionText, final String acceptLabel, final String skipLabel,
                                      final Time initialTime, final ResultHandler handler, boolean scarySkipButton) {

        promptPanel = WidgetFactory.createPromptPanel(questionText);
        promptPanel.getElement().setId("intake24-time-question-prompt");

        alertPanel = createAlert("You selected time that is later than the current. " +
                "If you added all meals that you had before now, please come back to add more when you receive a reminder.");

        timePicker = new TimePicker(initialTime);

        timePicker.hourCounter.getElement().setId("intake24-time-question-hours");
        timePicker.minuteCounter.getElement().setId("intake24-time-question-minutes");

        timePicker.hourCounter.setListener(v -> this.checkTime());
        timePicker.minuteCounter.setListener(v -> this.checkTime());

        confirmButton = WidgetFactory.createGreenButton(acceptLabel, "timeQuestionConfirmButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.handleAccept(timePicker.getTime());
            }
        });

        confirmButton.getElement().setId("intake24-time-question-confirm-button");

        ClickHandler skipHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handler.handleSkip();
            }
        };

        if (scarySkipButton)
            skipButton = WidgetFactory.createRedButton(skipLabel, "timeQuestionSkipButton", skipHandler);
        else
            skipButton = WidgetFactory.createButton(skipLabel, skipHandler);

        skipButton.getElement().setId("intake24-time-question-skip-button");

        FlowPanel contents = new FlowPanel();

        contents.add(promptPanel);
        contents.add(alertPanel);
        contents.add(timePicker);
        contents.add(WidgetFactory.createButtonsPanel(skipButton, confirmButton));

        initWidget(contents);

        this.checkTime();
    }

    private void checkTime() {
        Time tpTime = this.timePicker.getTime();
        int hour = Integer.parseInt(DateTimeFormat.getFormat("H").format(new Date()));
        int minute = Integer.parseInt(DateTimeFormat.getFormat("m").format(new Date()));
        if (tpTime.hours > hour || tpTime.hours == hour && tpTime.minutes > minute) {
            this.alertPanel.getElement().getStyle().setDisplay(Style.Display.BLOCK);
            this.skipButton.getElement().setAttribute("disabled", "disabled");
            this.confirmButton.getElement().setAttribute("disabled", "disabled");
            UxEventsHelper.postTimeWidgetLimit();
        } else {
            this.alertPanel.getElement().getStyle().setDisplay(Style.Display.NONE);
            this.skipButton.getElement().removeAttribute("disabled");
            this.confirmButton.getElement().removeAttribute("disabled");
        }
    }

    private FlowPanel createAlert(String alertText) {
        FlowPanel result = new FlowPanel();
        HTMLPanel promptPanel = new HTMLPanel(alertText);
        promptPanel.addStyleName("intake24-alert");
        result.add(promptPanel);
        return result;
    }

}