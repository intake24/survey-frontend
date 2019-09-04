package uk.ac.ncl.openlab.intake24.client.survey.scheme.base;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckListPrompt;

public abstract class Diet implements PromptRule<Survey, SurveyOperation> {

    private static final String DIET_COMPLETE = "dietComplete";
    private static final String DIET_KEY = "diet";

    private SafeHtml promptText;
    private PVector<MultipleChoiceQuestionOption> options;

    public Diet(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options) {
        this.promptText = promptText;
        this.options = options;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(DIET_COMPLETE) && state.portionSizeComplete()) {
            CheckListPrompt prompt = new CheckListPrompt(promptText, Diet.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel());

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String dietValue = answers.stream().map(answer -> answer.getValue())
                            .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);
                    return SurveyOperation.update(survey -> survey.withData(DIET_KEY, dietValue).withFlag(DIET_COMPLETE));
                } else
                    return SurveyOperation.update(survey -> survey.withFlag(DIET_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }
}
