/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.SelectDayQuestion;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;


public class SelectDayPrompt implements SimplePrompt<String> {
    private static final HelpMessages helpMessages = HelpMessages.Util.getInstance();
    private final SafeHtml promptText;

    public SelectDayPrompt(SafeHtml promptText) {
        this.promptText = promptText;
    }

    @Override
    public FlowPanel getInterface(final Callback1<String> onComplete) {
        final FlowPanel content = new FlowPanel();
        SelectDayQuestion selectDayQuestion = new SelectDayQuestion(promptText);
        content.add(selectDayQuestion);

        final Button finish = WidgetFactory.createGreenButton(helpMessages.textarea_continueButtonTitle(), "textareaContinueButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Option<String> answer = selectDayQuestion.getAnswer(true);
                if (!answer.isEmpty())
                    onComplete.call(answer.getOrDie());
            }
        });

        content.add(WidgetFactory.createButtonsPanel(finish));
        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }
}
