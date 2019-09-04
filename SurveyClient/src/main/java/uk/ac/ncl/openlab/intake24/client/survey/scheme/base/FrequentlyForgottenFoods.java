package uk.ac.ncl.openlab.intake24.client.survey.scheme.base;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.ReminderPrompt;

public abstract class FrequentlyForgottenFoods implements PromptRule<Survey, SurveyOperation> {

    private static final String REMINDER_KEY = "foodsReminderShown";

    private SafeHtml promptText;

    public FrequentlyForgottenFoods(SafeHtml promptText) {
        this.promptText = promptText;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(REMINDER_KEY) && state.portionSizeComplete()) {
            return Option.some(PromptUtil.asSurveyPrompt(new ReminderPrompt(promptText),
                    answers -> SurveyOperation.update(survey -> survey.withFlag(REMINDER_KEY))));
        } else {
            return Option.none();
        }
    }
}
