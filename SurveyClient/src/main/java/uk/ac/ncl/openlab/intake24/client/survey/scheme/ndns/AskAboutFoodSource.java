package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public class AskAboutFoodSource implements PromptRule<Meal, MealOperation> {

    public static final String FOOD_SOURCE_KEY = "foodSource";

    private static final PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("Large supermarket"))
            .plus(new MultipleChoiceQuestionOption("Convenience shop/corner shop/petrol station"))
            .plus(new MultipleChoiceQuestionOption("Convenience shop/corner shop/petrol station"))
            .plus(new MultipleChoiceQuestionOption("Fast food/take-away"))
            .plus(new MultipleChoiceQuestionOption("Café/coffee shop/sandwich bar/deli"))
            .plus(new MultipleChoiceQuestionOption("Sit-down restaurant or pub with a waiter/waitress"))
            .plus(new MultipleChoiceQuestionOption("Canteen at work or school/university/college"))
            .plus(new MultipleChoiceQuestionOption("Burger, chip or kebab van/’street food’"))
            .plus(new MultipleChoiceQuestionOption("Leisure centre/recreation or entertainment venue"))
            .plus(new MultipleChoiceQuestionOption("Vending machine in any location"))
            .plus(new MultipleChoiceQuestionOption("Other place (please specify):", "Other", true))
            .plus(new MultipleChoiceQuestionOption("Don't know"));

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (state.customData.containsKey(AskIfCookedAtHome.COOKED_AT_HOME_KEY) &&
                state.customData.get(AskIfCookedAtHome.COOKED_AT_HOME_KEY).equals("No") &&
                !state.customData.containsKey(FOOD_SOURCE_KEY) &&
                state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Where was <em>most</em> of the food for your " +
                    SafeHtmlUtils.htmlEscape(state.name.toLowerCase()) + " purchased from?</p>");

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskAboutFoodSource.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "foodSourceOption");

            return Option.some(PromptUtil.asMealPrompt(prompt, answer ->
                    MealOperation.setCustomDataField(FOOD_SOURCE_KEY,
                            answer.details.map(s -> "Other: " + s).getOrElse(answer.value))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutFoodSource(), priority);
    }
}
