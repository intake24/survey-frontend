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
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.ReadyMealsPrompt;

import static org.workcraft.gwt.shared.client.CollectionUtils.exists;

public class ShowReadyMealsPrompt implements PromptRule<Meal, MealOperation> {

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(final Meal meal, SelectionMode selectionType, PSet<String> surveyFlags) {
        boolean hasReadyMeal = exists(meal.foods, new Function1<FoodEntry, Boolean>() {
            @Override
            public Boolean apply(FoodEntry argument) {
                return argument.accept(new FoodEntry.Visitor<Boolean>() {
                    @Override
                    public Boolean visitRaw(RawFood food) {
                        return false;
                    }

                    @Override
                    public Boolean visitEncoded(EncodedFood food) {
                        return !food.isDrink() && !food.link.isLinked() && food.data.readyMealOption;
                    }

                    @Override
                    public Boolean visitTemplate(TemplateFood food) {
                        return false;
                    }

                    @Override
                    public Boolean visitMissing(MissingFood food) {
                        return false;
                    }

                    @Override
                    public Boolean visitCompound(CompoundFood food) {
                        return false;
                    }
                });
            }
        });

        if (meal.isEmpty() || !meal.encodingComplete() || !meal.portionSizeComplete() || !hasReadyMeal || meal.readyMealsComplete())
            return Option.none();
        else {
            return Option.<Prompt<Meal, MealOperation>>some(new ReadyMealsPrompt(meal));
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<PromptRule<Meal, MealOperation>>(new ShowReadyMealsPrompt(), priority);
    }
}