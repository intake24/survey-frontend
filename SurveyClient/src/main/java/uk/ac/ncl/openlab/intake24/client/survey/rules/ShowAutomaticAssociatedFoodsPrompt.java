/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.AssociatedFoodPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.AutomaticAssociatedFoodsPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;

import java.util.logging.Logger;

import static org.workcraft.gwt.shared.client.CollectionUtils.exists;
import static org.workcraft.gwt.shared.client.CollectionUtils.indexOf;

public class ShowAutomaticAssociatedFoodsPrompt implements PromptRule<Meal, MealOperation> {

    private final Logger log = Logger.getLogger("ShowAutomaticAssociatedFoodPrompt");

    private final String locale;

    public ShowAutomaticAssociatedFoodsPrompt(final String locale) {
        this.locale = locale;
    }

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(final Meal meal, SelectionMode selectionType, PSet<String> surveyFlag) {

        if (!meal.encodingComplete() || meal.associatedFoodsComplete())
            return Option.none();
        else

            return Option.<Prompt<Meal, MealOperation>>some(new AutomaticAssociatedFoodsPrompt(locale, meal));
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority, String locale) {
        return new WithPriority<PromptRule<Meal, MealOperation>>(new ShowAutomaticAssociatedFoodsPrompt(locale), priority);
    }
}
