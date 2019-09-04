package uk.ac.ncl.openlab.intake24.client.survey.scheme.base;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckListPrompt;

public abstract class Supplements implements PromptRule<Survey, SurveyOperation> {

    private static final String SUPPLEMENTS_COMPLETE = "supplementsComplete";
    private static final String SUPPLEMENTS_KEY = "supplements";

    private SafeHtml promptText;
    private PVector<MultipleChoiceQuestionOption> options;

    public Supplements(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options) {
        this.promptText = promptText;
        this.options = options;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(SUPPLEMENTS_COMPLETE) && state.portionSizeComplete()) {

            CheckListPrompt prompt = new CheckListPrompt(promptText, Supplements.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel());

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String supplementsValue = answers.stream()
                            .map(answer -> answer.getValue())
                            .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);

                    return SurveyOperation.update(survey -> survey.withData(SUPPLEMENTS_KEY, supplementsValue).withFlag(SUPPLEMENTS_COMPLETE));
                } else
                    return SurveyOperation.update(survey -> survey.withFlag(SUPPLEMENTS_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }
}
