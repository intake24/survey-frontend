/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.FoodLookupPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;

public class AskToLookupFoodFlexibleRecall implements PromptRule<Pair<FoodEntry, Meal>, MealOperation> {

    /**
     * Experimental. Flexible recall. Compared to original ignores FLAG_FREE_ENTRY_COMPLETE.
     */

    final private RecipeManager recipeManager;
    final private String locale;
    final private String algorithmId;

    public AskToLookupFoodFlexibleRecall(String locale, String algorithmId, RecipeManager recipeManager) {
        this.locale = locale;
        this.recipeManager = recipeManager;
        this.algorithmId = algorithmId;
    }

    @Override
    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> apply(Pair<FoodEntry, Meal> data, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (data.left.isTemplate() || data.left.isCompound() || data.left.isMissing())
            return Option.none();
        else if (!data.left.isEncoded())
            return Option.<Prompt<Pair<FoodEntry, Meal>, MealOperation>>some(new FoodLookupPrompt(locale, algorithmId, data.left, data.right, recipeManager));
        else
            return Option.none();
    }

    @Override
    public String toString() {
        return "Ask to look up food";
    }

    public static WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>> withPriority(int priority, String locale, String algorithmId, RecipeManager recipeManager) {
        return new WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>(new AskToLookupFoodFlexibleRecall(locale, algorithmId, recipeManager), priority);
    }
}