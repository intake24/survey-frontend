package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.ReminderPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.GenericReminder;

public class InfrequentFoodIntro extends GenericReminder {

    private final UserData userData;

    public InfrequentFoodIntro(SafeHtml promptText, String key, UserData userData) {
        super(promptText, key);
        this.userData = userData;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (userData.recallNumber == 1 && !state.flags.contains(reminderKey) && state.portionSizeComplete()) {
            return Option.some(PromptUtil.asSurveyPrompt(new ReminderPrompt(promptText),
                    answers -> SurveyOperation.update(survey -> survey.withFlag(reminderKey))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, UserData userData) {
        return new WithPriority<>(new InfrequentFoodIntro(SafeHtmlUtils.fromSafeConstant(
                "<p>The next five questions ask about your eating habits in the LAST MONTH.</p>"),
                "InfrequentFoodIntro", userData), priority);
    }
}
