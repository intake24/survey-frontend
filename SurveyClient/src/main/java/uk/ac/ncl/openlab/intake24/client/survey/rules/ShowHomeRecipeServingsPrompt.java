/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.*;
import uk.ac.ncl.openlab.intake24.client.BrowserConsole;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.FractionalQuantityPrompt;

import static org.workcraft.gwt.shared.client.CollectionUtils.foldl;
import static org.workcraft.gwt.shared.client.CollectionUtils.forall;

public class ShowHomeRecipeServingsPrompt implements PromptRule<Pair<FoodEntry, Meal>, MealOperation> {
    private final PromptMessages messages = GWT.create(PromptMessages.class);

    @Override
    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> apply(final Pair<FoodEntry, Meal> data,
                                                                      SelectionMode selectionType, final PSet<String> surveyFlags) {
        return data.left.accept(new FoodEntry.Visitor<Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>>>() {

            private Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> getPromptIfApplicable(final FoodEntry food) {
                if (forall(Meal.linkedFoods(data.right.foods, data.left), FoodEntry.isPortionSizeComplete)
                        && (!food.customData.containsKey(Recipe.SERVINGS_NUMBER_KEY))) {

                    FractionalQuantityPrompt quantityPrompt = new FractionalQuantityPrompt(SafeHtmlUtils
                            .fromSafeConstant(messages.homeRecipe_servingsPromptText(SafeHtmlUtils.htmlEscape(food.description()))),
                            messages.homeRecipe_servingsButtonLabel());

                    return Option.some(PromptUtil.asExtendedFoodPrompt(quantityPrompt, new Function1<Double, MealOperation>() {
                        @Override
                        public MealOperation apply(final Double servings) {

                            return MealOperation.update(new Function1<Meal, Meal>() {
                                @Override
                                public Meal apply(Meal meal) {

                                    int compoundIndex = meal.foodIndex(data.left);

                                    Meal withUpdatedCompoundFood = meal.updateFood(compoundIndex, data.left.withCustomDataField(Recipe.SERVINGS_NUMBER_KEY, Double.toString(servings)));

                                    return foldl(Meal.linkedFoods(meal.foods, data.left), withUpdatedCompoundFood, new Function2<Meal, FoodEntry, Meal>() {
                                        @Override
                                        public Meal apply(Meal meal, FoodEntry food) {

                                           if (food.isEncoded()) {
                                               EncodedFood encodedFood = food.asEncoded();
                                               CompletedPortionSize updatedPs = encodedFood.completedPortionSize().multiply(1.0 / servings);
                                               return meal.updateFood(meal.foodIndex(food), encodedFood.withPortionSize(new Either.Right(updatedPs)));
                                           } else {
                                               return meal;
                                           }
                                        }
                                    });
                                }
                            });
                        }
                    }));
                } else {
                    return Option.none();
                }
            }

            @Override
            public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitRaw(RawFood food) {
                return Option.none();
            }

            @Override
            public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitEncoded(EncodedFood food) {
                return Option.none();
            }

            @Override
            public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitTemplate(final TemplateFood food) {
                if (food.isTemplateComplete())
                    return getPromptIfApplicable(food);
                else
                    return Option.none();
            }

            @Override
            public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitMissing(MissingFood food) {
                return Option.none();
            }

            @Override
            public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitCompound(CompoundFood food) {
                return getPromptIfApplicable(food);
            }

        });
    }

    @Override
    public String toString() {
        return "Ask how many people did the homemade dish serve";
    }

    public static WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>> withPriority(int priority) {
        return new WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>(new ShowHomeRecipeServingsPrompt(),
                priority);
    }
}