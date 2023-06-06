package uk.ac.ncl.openlab.intake24.client.survey.scheme.bristol;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.TextAreaPrompt;

import java.util.ArrayList;

public class AskAboutFoodSourceFollowUp implements PromptRule<Meal, MealOperation> {

    private static final String FOOD_SOURCE_FOLLOW_UP_KEY = "foodSourceFollowUp";

    private static final String promptTemplate = "<p>Please provide the name of the outlet.</p>";

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        ArrayList<String> answers = new ArrayList<>();
        answers.add("Traditional market / delicatessen / butchers / fishmongers / farm shop");
        answers.add("Vending machine");
        answers.add("Canteen");
        answers.add("Grown at home / allotment");
        answers.add("Don't know");

        boolean askFollowUp = state.customData.containsKey(AskAboutFoodSource.FOOD_SOURCE_KEY) && !answers.contains(state.customData.get(AskAboutFoodSource.FOOD_SOURCE_KEY));

        if (!state.customData.containsKey(FOOD_SOURCE_FOLLOW_UP_KEY) && askFollowUp && state.portionSizeComplete()) {
            TextAreaPrompt prompt = new TextAreaPrompt(SafeHtmlUtils.fromSafeConstant(promptTemplate));

            return Option.some(PromptUtil.asMealPrompt(prompt, answer -> MealOperation.update(meal -> meal.withCustomDataField(FOOD_SOURCE_FOLLOW_UP_KEY, answer))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutFoodSourceFollowUp(), priority);
    }
}
