/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.DrinkReminderPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;

public class ShowDrinkReminderPrompt implements PromptRule<Meal, MealOperation> {

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(final Meal meal, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!meal.encodingComplete() || meal.hasDrinks() || meal.confirmedNoDrinks())
            return Option.none();
        else {
            return Option.some(new DrinkReminderPrompt(meal));
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new ShowDrinkReminderPrompt(), priority);
    }
}