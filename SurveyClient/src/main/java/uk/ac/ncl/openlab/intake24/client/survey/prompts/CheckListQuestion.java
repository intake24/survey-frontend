/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.CheckBox;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;

import java.util.ArrayList;
import java.util.List;

public class CheckListQuestion extends MultipleChoiceQuestion<List<MultipleChoiceQuestionAnswer>> {

    private boolean required;

    public CheckListQuestion(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options, Boolean required) {
        super(promptText, options);
        this.required = required;
    }

    public Option<List<MultipleChoiceQuestionAnswer>> getAnswer() {
        ArrayList<MultipleChoiceQuestionAnswer> result = new ArrayList<>();

        for (OptionElements elements : optionElements) {
            if (elements.checkBox.getValue()) {
                String details = elements.textBox.map(tb -> tb.getText()).getOrElse("");
                if (!elements.textBox.isEmpty() && details.length() == 0) {
                    showWarning();
                    return Option.none();
                } else
                    result.add(new MultipleChoiceQuestionAnswer(elements.index, elements.checkBox.getFormValue(), elements.textBox.map(tb -> tb.getText())));
            }
        }

        if (required && result.isEmpty()) {
            showWarning();
            return Option.none();
        } else
            return Option.some(result);
    }

    @Override
    protected CheckBox createCheckBox(SafeHtml label, String value) {
        CheckBox input = new CheckBox(label);
        input.addClickHandler(event -> clearWarning());
        return input;
    }
}
