/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Function2;
import org.workcraft.gwt.shared.client.Option;

import static org.workcraft.gwt.shared.client.CollectionUtils.*;

public class HandleDeleted implements Function2<Survey, Survey, Survey> {
    private Meal cleanUpLinkedFoods(final Meal meal) {
        return meal.withFoods(filter(meal.foods, new Function1<FoodEntry, Boolean>() {
            @Override
            public Boolean apply(FoodEntry food) {
                return food.link.linkedTo.accept(new Option.Visitor<UUID, Boolean>() {
                    @Override
                    public Boolean visitSome(UUID id) {
                        return meal.foodIndex(id) != -1;
                    }

                    @Override
                    public Boolean visitNone() {
                        return true;
                    }
                });
            }
        }));
    }

    private Meal updateCompoundFoods(final Meal meal) {
        return meal.withFoods(map(meal.foods, new Function1<FoodEntry, FoodEntry>() {
            @Override
            public FoodEntry apply(FoodEntry argument) {
                return argument.accept(new FoodEntry.Visitor<FoodEntry>() {
                    @Override
                    public FoodEntry visitRaw(RawFood food) {
                        return food;
                    }

                    @Override
                    public FoodEntry visitEncoded(EncodedFood food) {
                        return food;
                    }

                    @Override
                    public FoodEntry visitTemplate(TemplateFood food) {
                        TemplateFood res = food;

                        for (int i = 0; i < food.data.template.size(); i++) {
                            for (UUID id : food.components.get(i)) {
                                if (meal.foodIndex(id) == -1)
                                    res = res.removeComponent(id);
                            }
                        }

                        return res;
                    }

                    @Override
                    public FoodEntry visitMissing(MissingFood food) {
                        return food;
                    }

                    @Override
                    public FoodEntry visitCompound(CompoundFood food) {
                        return food;
                    }
                });
            }
        }));
    }

    @Override
    public Survey apply(Survey s0, Survey s1) {
        return s1.withMeals(map(s1.meals, new Function1<Meal, Meal>() {
            @Override
            public Meal apply(Meal argument) {
                return updateCompoundFoods(cleanUpLinkedFoods(argument));
            }
        }));
    }
}