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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.SimpleHomeRecipePrompt;

public class ShowSimpleHomeRecipePromptFlexibleRecall implements PromptRule<FoodEntry, FoodOperation> {

    /**
     * Experimental. Flexible recall. Compared to original ignores FLAG_FREE_ENTRY_COMPLETE.
     */

    @Override
    public Option<Prompt<FoodEntry, FoodOperation>> apply(FoodEntry data, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (data.isMissing() && data.flags.contains(MissingFood.HOME_RECIPE_FLAG) && !((MissingFood) data).isDescriptionComplete())
            return new Option.Some<Prompt<FoodEntry, FoodOperation>>(new SimpleHomeRecipePrompt((MissingFood) data));
        else
            return new Option.None<Prompt<FoodEntry, FoodOperation>>();
    }

    @Override
    public String toString() {
        return "Ask for missing food description";
    }

    public static WithPriority<PromptRule<FoodEntry, FoodOperation>> withPriority(int priority) {
        return new WithPriority<PromptRule<FoodEntry, FoodOperation>>(new ShowSimpleHomeRecipePromptFlexibleRecall(), priority);
    }
}