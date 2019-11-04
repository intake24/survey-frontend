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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public class MealLocationRule implements PromptRule<Meal, MealOperation> {

    private static final String MEAL_LOCATION_KEY = "mealLocation";

    private static final String promptTemplate = "<p>Where did you eat your $name? Tick one.</p>";

    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("In school (including outdoor areas within the school site)", "A"))
                    .plus(new MultipleChoiceQuestionOption("At home", "B"))
                    .plus(new MultipleChoiceQuestionOption("On the journey to/from school", "C"))
                    .plus(new MultipleChoiceQuestionOption("Another location: ", "D", true));

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(MEAL_LOCATION_KEY) && !state.isEmpty() && state.portionSizeComplete()) {
            SafeHtml promptSafeText = SafeHtmlUtils.fromSafeConstant(
                    promptTemplate.replace("$name", SafeHtmlUtils.htmlEscape(state.name.toLowerCase()))
            );

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptSafeText, MealLocationRule.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "mealLocationOption");

            return Option.some(PromptUtil.asMealPrompt(prompt, answer ->
                    MealOperation.setCustomDataField(MEAL_LOCATION_KEY, answer.getValue())));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new MealLocationRule(), priority);
    }
}
