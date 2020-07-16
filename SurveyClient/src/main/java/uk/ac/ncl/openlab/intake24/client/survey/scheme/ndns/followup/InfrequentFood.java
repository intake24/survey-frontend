package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.SelectDayPrompt;

public abstract class InfrequentFood implements PromptRule<Survey, SurveyOperation> {

    private final String promptText;
    private final String key;
    private final UserData userData;

    InfrequentFood(String promptText, String key, UserData userData) {
        this.promptText = promptText;
        this.key = key;
        this.userData = userData;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (userData.recallNumber == 1 && !state.customData.containsKey(key) && state.portionSizeComplete()) {
            SelectDayPrompt prompt = new SelectDayPrompt(SafeHtmlUtils.fromSafeConstant(promptText));

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(survey -> survey.withData(key, answer))));
        } else {
            return Option.none();
        }
    }
}

