package uk.ac.ncl.openlab.intake24.client.survey.scheme.birmingham;

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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckListPrompt;

public class MealFoodSourceRule implements PromptRule<Meal, MealOperation> {

    private static final String FOOD_SOURCES_COMPLETE = "foodSourcesComplete";
    private static final String FOOD_SOURCES_KEY = "foodSources";

    private static final String promptTemplate = "<p>Where did you buy your $name from? Tick all that apply.</p>";

    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("A school canteen/café/shop", "A"))
                    .plus(new MultipleChoiceQuestionOption("School vending machine", "B"))
                    .plus(new MultipleChoiceQuestionOption("A shop/café/fast food place/vending machine outside of school", "C"))
                    .plus(new MultipleChoiceQuestionOption("Brought from home", "D"));

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(FOOD_SOURCES_COMPLETE) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(promptTemplate.replace("$name", SafeHtmlUtils.htmlEscape(state.name)));

            CheckListPrompt prompt = new CheckListPrompt(promptText, MealFoodSourceRule.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel());

            return Option.some(PromptUtil.asMealPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String supplementsValue = answers.stream()
                            .map(answer -> answer.getValue())
                            .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);

                    return MealOperation.update(meal -> meal.withCustomDataField(FOOD_SOURCES_KEY, supplementsValue).withFlag(FOOD_SOURCES_COMPLETE));
                } else
                    return MealOperation.update(meal -> meal.withFlag(FOOD_SOURCES_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new MealFoodSourceRule(), priority);
    }
}
