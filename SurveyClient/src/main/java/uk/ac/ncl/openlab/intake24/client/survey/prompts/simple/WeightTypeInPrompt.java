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
import com.google.gwt.user.client.ui.*;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class WeightTypeInPrompt implements SimplePrompt<Double> {
    private final static PromptMessages messages = PromptMessages.Util.getInstance();

    final private SafeHtml promptText;
    final private SafeHtml unitLabel;
    final private String acceptText;

    public WeightTypeInPrompt(SafeHtml promptText, SafeHtml unitLabel, String acceptText) {
        this.promptText = promptText;
        this.acceptText = acceptText;
        this.unitLabel = unitLabel;
    }

    @Override
    public FlowPanel getInterface(final Callback1<Double> onComplete) {
        final FlowPanel content = new FlowPanel();

        content.add(WidgetFactory.createPromptPanel(promptText));

        final FlowPanel errorMessageDiv = new FlowPanel();

        content.add(errorMessageDiv);
        errorMessageDiv.addStyleName("intake24-text-box-question-warning");

        final TextBox weightTextBox = new TextBox();
        weightTextBox.addStyleName("intake24-weight-type-in-textbox");

        HTMLPanel unitsLabel = new HTMLPanel("span", unitLabel.asString());

        content.add(weightTextBox);
        content.add(unitsLabel);

        Button accept = WidgetFactory.createGreenButton(acceptText, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                try {
                    double weight = Double.parseDouble(weightTextBox.getText());

                    if (weight <= 0.0 || weight > 2000) {
                        errorMessageDiv.clear();
                        errorMessageDiv.add(new Label(messages.weightTypeIn_rangeError()));
                    } else
                        onComplete.call(weight);
                } catch (NumberFormatException e) {
                    errorMessageDiv.clear();
                    errorMessageDiv.add(new Label(messages.weightTypeIn_formatError()));
                }
            }
        });

        content.add(WidgetFactory.createButtonsPanel(accept));

        return content;
    }
}