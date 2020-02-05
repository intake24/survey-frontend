package uk.ac.ncl.openlab.intake24.client.survey.scheme.sab;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.AdditionalFoodReminderPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;

import java.util.ArrayList;

public class AskAboutAdditionalFood implements PromptRule<Meal, MealOperation> {
    private static PromptMessages messages = GWT.create(PromptMessages.class);

    private static final String MEAL_ADDITIONAL_FOOD_KEY = "noAdditionalFood";

    private static final String promptTemplate = "<p>In addition to what you have already told me about, " +
            "did you have any of the following with your $meal?</p>" +
            "<ul>" +
            "<li>Rice</li>" +
            "<li>Chapatti / roti / paratha / other bread</li>" +
            "<li>Pickle or chutney</li>" +
            "<li>Raita, curd or yoghurt</li>" +
            "</ul>" +
            "<p>If yes, please go back and record these items.</p>" +
            "<p>If no, please press continue.</p>";

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(final Meal meal, SelectionMode selectionType, PSet<String> surveyFlags) {
        ArrayList<String> mealList = new ArrayList<>();
        mealList.add(messages.predefMeal_Breakfast().toLowerCase());
        mealList.add(messages.predefMeal_Lunch().toLowerCase());
        mealList.add(messages.predefMeal_Dinner().toLowerCase());
        mealList.add(messages.predefMeal_EveningMeal().toLowerCase());

        if (mealList.contains(meal.name.toLowerCase()) && !meal.flags.contains(MEAL_ADDITIONAL_FOOD_KEY) && !meal.isEmpty() && meal.portionSizeComplete()) {
            SafeHtml promptSafeText = SafeHtmlUtils.fromSafeConstant(
                    promptTemplate.replace("$meal", SafeHtmlUtils.htmlEscape(meal.name.toLowerCase()))
            );
            return Option.some(new AdditionalFoodReminderPrompt(promptSafeText, MEAL_ADDITIONAL_FOOD_KEY));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutAdditionalFood(), priority);
    }
}
