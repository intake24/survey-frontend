/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.FlowPanel;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class ReminderPrompt implements SimplePrompt<Void> {
    private final SafeHtml promptText;

    public ReminderPrompt(SafeHtml promptText) {
        this.promptText = promptText;
    }

    @Override
    public FlowPanel getInterface(final Callback1<Void> onComplete) {
        final FlowPanel content = new FlowPanel();

        content.add(WidgetFactory.createPromptPanel(promptText));
        content.add(WidgetFactory.createButtonsPanel(WidgetFactory.createGreenButton(PromptMessages.INSTANCE.foodComplete_continueButtonLabel(),
                "reminderContinueButton", clickEvent -> onComplete.call(null))));

        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }
}