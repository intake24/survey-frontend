/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pcollections.*;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SMeal {

    @JsonProperty
    public final String name;
    @JsonProperty
    public final PVector<SFoodEntry> foods;
    @JsonProperty
    public final Option<Time> time;
    @JsonProperty
    public final PSet<String> flags;
    @JsonProperty
    public final PMap<String, String> customData;

    @JsonCreator
    public SMeal(@JsonProperty("name") String name, @JsonProperty("foods") List<SFoodEntry> foods,
                 @JsonProperty("time") Option<Time> time, @JsonProperty("flags") Set<String> flags,
                 @JsonProperty("customData") Map<String, String> customData) {
        this.name = name;
        this.foods = TreePVector.from(foods);
        this.time = time;
        this.flags = HashTreePSet.from(flags);
        this.customData = HashTreePMap.from(customData);
    }

    public SMeal(Meal meal) {
        this.name = meal.name;
        this.foods = SFoodEntry.toSerialisable(meal.foods);
        this.time = meal.time;
        this.flags = meal.flags;
        this.customData = meal.customData;
    }

    public Meal toMeal(final PortionSizeScriptManager scriptManager, final CompoundFoodTemplateManager templateManager) {

        PVector<FoodEntry> mealFoods = SFoodEntry.toRuntime(foods, scriptManager, templateManager);

        return new Meal(name, mealFoods, time, flags, customData);
    }
}
