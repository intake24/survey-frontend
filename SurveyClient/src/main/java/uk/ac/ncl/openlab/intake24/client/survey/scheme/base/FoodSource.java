package uk.ac.ncl.openlab.intake24.client.survey.scheme.base;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public abstract class FoodSource implements PromptRule<Meal, MealOperation> {

    private static final String FOOD_SOURCE_KEY = "foodSource";

    private String promptText;
    private PVector<MultipleChoiceQuestionOption> options;

    public FoodSource(String promptText, PVector<MultipleChoiceQuestionOption> options) {
        this.promptText = promptText;
        this.options = options;
    }

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(FOOD_SOURCE_KEY) && !state.isEmpty() && state.portionSizeComplete()) {
            SafeHtml promptSafeText = SafeHtmlUtils.fromSafeConstant(
                    promptText.replace("%s", SafeHtmlUtils.htmlEscape(state.name.toLowerCase()))
            );

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptSafeText, FoodSource.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "foodSourceOption");

            return Option.some(PromptUtil.asMealPrompt(prompt, answer ->
                    MealOperation.setCustomDataField(FOOD_SOURCE_KEY, answer.getValue())));
        } else {
            return Option.none();
        }
    }
}
