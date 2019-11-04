/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.RadioButton;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;

public class RadioButtonQuestion extends MultipleChoiceQuestion<MultipleChoiceQuestionAnswer> {
    private final String radioGroupId;

    public RadioButtonQuestion(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options, String radioGroupId) {
        super(promptText, options);
        this.radioGroupId = radioGroupId;
    }

    public Option<MultipleChoiceQuestionAnswer> getAnswer() {
        for (OptionElements elements : optionElements) {
            String details = elements.textBox.map(tb -> tb.getText()).getOrElse("");

            if (elements.checkBox.getValue() && (elements.textBox.isEmpty() || details.length() != 0))
                return Option.some(new MultipleChoiceQuestionAnswer(elements.index, elements.checkBox.getFormValue(), elements.textBox.map(tb -> tb.getText())));
        }

        showWarning();
        return Option.none();
    }

    @Override
    protected CheckBox createCheckBox(SafeHtml label, String value) {
        RadioButton button = new RadioButton(radioGroupId, label);
        button.addClickHandler(event -> clearWarning());
        return button;
    }
}