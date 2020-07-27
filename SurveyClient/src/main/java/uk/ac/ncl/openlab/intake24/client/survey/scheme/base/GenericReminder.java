package uk.ac.ncl.openlab.intake24.client.survey.scheme.base;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.ReminderPrompt;

public abstract class GenericReminder implements PromptRule<Survey, SurveyOperation> {

    protected String reminderKey;
    protected SafeHtml promptText;

    public GenericReminder(SafeHtml promptText, String reminderKey) {
        this.promptText = promptText;
        this.reminderKey = reminderKey;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(reminderKey) && state.portionSizeComplete()) {
            return Option.some(PromptUtil.asSurveyPrompt(new ReminderPrompt(promptText),
                    answers -> SurveyOperation.update(survey -> survey.withFlag(reminderKey))));
        } else {
            return Option.none();
        }
    }
}
