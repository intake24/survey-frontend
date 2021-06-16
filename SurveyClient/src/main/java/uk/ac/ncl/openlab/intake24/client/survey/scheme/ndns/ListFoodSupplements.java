package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.FoodSupplementsPrompt;

import static org.workcraft.gwt.shared.client.CollectionUtils.exists;

public class ListFoodSupplements implements PromptRule<Survey, SurveyOperation> {

    public static final String SUPPLEMENTS_MEAL_FLAG = "food-supplements";

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        String confirmation = state.customData.get(ConfirmFoodSupplements.supplementsConfirmationKey);
        boolean confirmed = confirmation != null && confirmation.equals(Boolean.toString(true));
        boolean mealExists = exists(state.meals, meal -> meal.flags.contains(SUPPLEMENTS_MEAL_FLAG));

        if (confirmed && !mealExists && state.portionSizeComplete()) {
            return Option.some(new FoodSupplementsPrompt());
        } else {
            return Option.none();
        }
    }


    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new ListFoodSupplements(), priority);
    }
}
