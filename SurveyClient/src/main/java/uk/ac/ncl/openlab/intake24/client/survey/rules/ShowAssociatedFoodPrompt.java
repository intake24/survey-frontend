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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;

import java.util.logging.Logger;

import static org.workcraft.gwt.shared.client.CollectionUtils.exists;
import static org.workcraft.gwt.shared.client.CollectionUtils.indexOf;

public class ShowAssociatedFoodPrompt implements PromptRule<Pair<FoodEntry, Meal>, MealOperation> {

    private final Logger log = Logger.getLogger("ShowAssociatedFoodPrompt");

    private final String locale;

    public ShowAssociatedFoodPrompt(final String locale) {
        this.locale = locale;
    }

    public static int applicablePromptIndex(final PVector<FoodEntry> foods, final EncodedFood food) {
        return indexOf(food.enabledPrompts, new Function1<AssociatedFood, Boolean>() {
            @Override
            public Boolean apply(final AssociatedFood prompt) {
                return !exists(Meal.linkedFoods(foods, food), new Function1<FoodEntry, Boolean>() {
                    @Override
                    public Boolean apply(FoodEntry argument) {
                        return argument.accept(new FoodEntry.Visitor<Boolean>() {
                            @Override
                            public Boolean visitRaw(RawFood food) {
                                return false;
                            }

                            @Override
                            public Boolean visitEncoded(EncodedFood food) {
                                return food.isInCategory(prompt.code) || food.data.code.equals(prompt.code);
                            }

                            @Override
                            public Boolean visitTemplate(TemplateFood food) {
                                return false;
                            }

                            @Override
                            public Boolean visitMissing(MissingFood food) {
                                return prompt.code.equals(food.customData.get(MissingFood.KEY_ASSOC_FOOD_CATEGORY));
                            }

                            @Override
                            public Boolean visitCompound(CompoundFood food) {
                                return false;
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> apply(final Pair<FoodEntry, Meal> pair, SelectionMode selectionType, PSet<String> surveyFlag) {

        if (!pair.left.isEncoded() || !pair.left.isPortionSizeComplete() || pair.left.link.isLinked() || !pair.right.encodingComplete())
            return Option.none();
        else {
            EncodedFood food = (EncodedFood) pair.left;
            Meal meal = pair.right;

            int index = applicablePromptIndex(meal.foods, food);

            if (index == -1)
                return Option.none();
            else
                return Option.<Prompt<Pair<FoodEntry, Meal>, MealOperation>>some(new AssociatedFoodPrompt(locale, pair, meal.foodIndex(food), index));
        }
    }

    public static WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>> withPriority(int priority, String locale) {
        return new WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>(new ShowAssociatedFoodPrompt(locale), priority);
    }
}