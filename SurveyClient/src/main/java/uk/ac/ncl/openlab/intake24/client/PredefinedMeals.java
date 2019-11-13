/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.Meal;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;

public class PredefinedMeals {
    private static PromptMessages messages = GWT.create(PromptMessages.class);

    private static final PVector<Meal> defaultStartingMeals = TreePVector.<Meal>empty()
            .plus(Meal.empty(messages.predefMeal_Breakfast()))
            .plus(Meal.empty(messages.predefMeal_MorningSnack()))
            .plus(Meal.empty(messages.predefMeal_Lunch()))
            .plus(Meal.empty(messages.predefMeal_MidDaySnack()))
            .plus(Meal.empty(messages.predefMeal_EveningMeal()))
            .plus(Meal.empty(messages.predefMeal_LateSnack()));

    private static final PVector<Meal> portugueseStartingMeals = TreePVector.<Meal>empty()
            .plus(Meal.empty(messages.predefMeal_Breakfast()))
            .plus(Meal.empty(messages.predefMeal_MorningSnack()))
            .plus(Meal.empty(messages.predefMeal_Lunch()))
            .plus(Meal.empty(messages.predefMeal_MidDaySnack()))
            .plus(Meal.empty(messages.predefMeal_Dinner()))
            .plus(Meal.empty(messages.predefMeal_EveningMeal()));

    private static final PVector<Meal> SABStartingMeals = TreePVector.<Meal>empty()
            .plus(Meal.empty(messages.predefMeal_EarlySnack()))
            .plus(Meal.empty(messages.predefMeal_Breakfast()))
            .plus(Meal.empty(messages.predefMeal_MorningSnack()))
            .plus(Meal.empty(messages.predefMeal_Lunch()))
            .plus(Meal.empty(messages.predefMeal_MidDaySnack()))
            .plus(Meal.empty(messages.predefMeal_EveningMeal()))
            .plus(Meal.empty(messages.predefMeal_LateSnack()));

    private static final String[] defaultSuggestedMealNames = {
            messages.predefMeal_EarlySnack(),
            messages.predefMeal_Breakfast(),
            messages.predefMeal_MorningSnack(),
            messages.predefMeal_Lunch(),
            messages.predefMeal_MidDaySnack(),
            messages.predefMeal_Dinner(),
            messages.predefMeal_EveningMeal(),
            messages.predefMeal_LateSnack()
    };

    private static final String[] portugueseSuggestedMealNames = {
            messages.predefMeal_EarlySnack(),
            messages.predefMeal_Breakfast(),
            messages.predefMeal_MorningSnack(),
            messages.predefMeal_Lunch(),
            messages.predefMeal_MidDaySnack(),
            messages.predefMeal_Dinner(),
            messages.predefMeal_EveningMeal(),
            messages.predefMeal_LateSnack()
    };

    private static final String[] SABSuggestedMealNames = {
            messages.predefMeal_EarlySnack(),
            messages.predefMeal_Breakfast(),
            messages.predefMeal_MorningSnack(),
            messages.predefMeal_Lunch(),
            messages.predefMeal_MidDaySnack(),
            messages.predefMeal_Dinner(),
            messages.predefMeal_EveningMeal(),
            messages.predefMeal_LateSnack()
    };

    public static PVector<Meal> getStartingMealsForCurrentLocale() {
        switch (EmbeddedData.localeId) {
            case "pt_PT":
                return portugueseStartingMeals;
            case "SABv1":
                return SABStartingMeals;
            default:
                return defaultStartingMeals;
        }
    }

    public static String[] getSuggestedMealNamesForCurrentLocale() {
        switch (EmbeddedData.localeId) {
            case "pt_PT":
                return portugueseSuggestedMealNames;
            case "SABv1":
                return SABSuggestedMealNames;
            default:
                return defaultSuggestedMealNames;
        }
    }
}
