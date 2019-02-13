package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import org.workcraft.gwt.shared.client.Option;

public class MultipleChoiceQuestionOption {
    public final String label;
    public final Option<String> value;
    public final boolean hasTextBox;

    public MultipleChoiceQuestionOption(String label, Option<String> value, boolean hasTextBox) {
        this.label = label;
        this.hasTextBox = hasTextBox;
        this.value = value;
    }

    public MultipleChoiceQuestionOption(String label) {
        this.label = label;
        this.value = Option.none();
        this.hasTextBox = false;
    }

    public MultipleChoiceQuestionOption(String label, String value) {
        this.label = label;
        this.value = Option.some(value);
        this.hasTextBox = false;
    }

    public MultipleChoiceQuestionOption(String label, String value, boolean hasTextBox) {
        this.label = label;
        this.value = Option.some(value);
        this.hasTextBox = hasTextBox;
    }
}
