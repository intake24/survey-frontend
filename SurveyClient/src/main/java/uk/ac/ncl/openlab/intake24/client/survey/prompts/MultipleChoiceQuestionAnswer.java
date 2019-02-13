package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import org.workcraft.gwt.shared.client.Option;

public class MultipleChoiceQuestionAnswer {
    public final String value;
    public final int index;
    public final Option<String> details;

    public MultipleChoiceQuestionAnswer(int index, String value, Option<String> details) {
        this.value = value;
        this.index = index;
        this.details = details;
    }
}
