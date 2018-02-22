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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.ChoosePortionSizeMethodPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.UnknownPortionSizeMethodPrompt;

public class ChoosePortionSizeMethodFlexibleRecall implements PromptRule<FoodEntry, FoodOperation> {

    /**
     * Experimental. Flexible recall. Compared to original ignores FLAG_FREE_ENTRY_COMPLETE.
     */

    @Override
    public Option<Prompt<FoodEntry, FoodOperation>> apply(final FoodEntry state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.isEncoded())
            return new Option.None<Prompt<FoodEntry, FoodOperation>>();
        else {
            EncodedFood f = (EncodedFood) state;

            if (!f.portionSize.isEmpty())
                return Option.none();
            else if (!f.portionSizeMethodIndex.isEmpty())
                return Option.none();
            else if (f.data.portionSizeMethods.size() == 0)
                return Option.<Prompt<FoodEntry, FoodOperation>>some(new UnknownPortionSizeMethodPrompt(f.description()));
            else if (f.data.portionSizeMethods.size() == 1)
                return Option.none();
            else
                return Option.<Prompt<FoodEntry, FoodOperation>>some(new ChoosePortionSizeMethodPrompt(f));
        }
    }

    @Override
    public String toString() {
        return "Choose portion size method";
    }

    public static WithPriority<PromptRule<FoodEntry, FoodOperation>> withPriority(int priority) {
        return new WithPriority<PromptRule<FoodEntry, FoodOperation>>(new ChoosePortionSizeMethodFlexibleRecall(), priority);
    }
}