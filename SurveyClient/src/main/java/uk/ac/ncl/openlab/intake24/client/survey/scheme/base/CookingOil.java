package uk.ac.ncl.openlab.intake24.client.survey.scheme.base;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public abstract class CookingOil implements PromptRule<Survey, SurveyOperation> {

    private static final String COOKING_OIL_KEY = "cookingOil";

    private SafeHtml promptText;
    private PVector<MultipleChoiceQuestionOption> options;

    public CookingOil(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options) {
        this.promptText = promptText;
        this.options = options;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(COOKING_OIL_KEY) && state.portionSizeComplete()) {
            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, CookingOil.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), "cookingOilOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer -> SurveyOperation.update(s -> s.withData(COOKING_OIL_KEY, answer.getValue()))));
        } else {
            return Option.none();
        }
    }
}
