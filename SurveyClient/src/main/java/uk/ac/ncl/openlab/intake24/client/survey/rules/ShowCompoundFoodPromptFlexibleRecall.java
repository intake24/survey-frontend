/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.CompoundFoodPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;

public class ShowCompoundFoodPromptFlexibleRecall implements PromptRule<Pair<FoodEntry, Meal>, MealOperation> {

    /**
     * Experimental. Flexible recall. Compared to original ignores FLAG_FREE_ENTRY_COMPLETE.
     */

    private final String locale;

    public ShowCompoundFoodPromptFlexibleRecall(String locale) {
        this.locale = locale;
    }

    @Override
    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> apply(final Pair<FoodEntry, Meal> pair, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!pair.left.isTemplate())
            return Option.none();
        else {
            final TemplateFood food = (TemplateFood) pair.left;
            final Meal meal = pair.right;

            return food.nextComponentIndex().map(new Function1<Integer, Prompt<Pair<FoodEntry, Meal>, MealOperation>>() {
                @Override
                public Prompt<Pair<FoodEntry, Meal>, MealOperation> apply(Integer componentIndex) {
                    TemplateFoodData.ComponentDef def = food.data.template.get(componentIndex);

                    boolean isFirst = food.components.get(componentIndex).isEmpty();
                    boolean allowSkip = (def.type == TemplateFoodData.ComponentType.Optional) || (def.occurence == TemplateFoodData.ComponentOccurence.Multiple && !isFirst);

                    return new CompoundFoodPrompt(locale, meal, meal.foodIndex(food), componentIndex, isFirst, allowSkip);
                }
            });
        }
    }

    public static WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>> withPriority(int priority, String locale) {
        return new WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>(new ShowCompoundFoodPromptFlexibleRecall(locale), priority);
    }
}