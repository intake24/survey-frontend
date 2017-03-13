/*
This file is part of Intake24

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
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodData;
import uk.ac.ncl.openlab.intake24.client.api.foods.PortionSizeMethod;
import uk.ac.ncl.openlab.intake24.client.survey.AssociatedFood;

import java.util.List;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

public class SFoodData {
    @JsonProperty
    public final String code;
    @JsonProperty
    public final String localDescription;
    @JsonProperty
    public final boolean readyMealOption;
    @JsonProperty
    public final boolean sameAsBeforeOption;
    @JsonProperty
    public final double caloriesPer100g;
    @JsonProperty
    public final PVector<SPortionSizeMethod> portionSizeMethods;
    @JsonProperty
    public final PVector<AssociatedFood> associatedFoods;
    @JsonProperty
    public final PVector<String> brands;
    @JsonProperty
    public final PVector<String> categories;

    @JsonCreator
    public SFoodData(@JsonProperty("code") String code,
                     @JsonProperty("readyMealOption") boolean readyMealOption,
                     @JsonProperty("sameAsBeforeOption") boolean sameAsBeforeOption,
                     @JsonProperty("caloriesPer100g") double caloriesPer100g,
                     @JsonProperty("localDescription") String localDescription,
                     @JsonProperty("portionSizeMethods") List<SPortionSizeMethod> portionSizeMethods,
                     @JsonProperty("associatedFoods") List<AssociatedFood> associatedFoods,
                     @JsonProperty("brands") List<String> brands,
                     @JsonProperty("categories") List<String> categories) {
        this.readyMealOption = readyMealOption;
        this.sameAsBeforeOption = sameAsBeforeOption;
        this.caloriesPer100g = caloriesPer100g;
        this.localDescription = localDescription;
        this.code = code;
        this.portionSizeMethods = TreePVector.from(portionSizeMethods);
        this.associatedFoods = TreePVector.from(associatedFoods);
        this.brands = TreePVector.from(brands);
        this.categories = TreePVector.from(categories);
    }

    public SFoodData(FoodData data) {
        this(data.code, data.readyMealOption, data.sameAsBeforeOption, data.caloriesPer100g, data.localDescription,
                map(TreePVector.from(data.portionSizeMethods), new Function1<PortionSizeMethod, SPortionSizeMethod>() {
                    @Override
                    public SPortionSizeMethod apply(PortionSizeMethod argument) {
                        return new SPortionSizeMethod(argument);
                    }

                }),
                data.associatedFoods,
                TreePVector.from(data.brands), TreePVector.from(data.categories));
    }

    public FoodData toFoodData() {
        return new FoodData(code, readyMealOption, sameAsBeforeOption, caloriesPer100g, localDescription,
                map(portionSizeMethods, new Function1<SPortionSizeMethod, PortionSizeMethod>() {
                    @Override
                    public PortionSizeMethod apply(SPortionSizeMethod argument) {
                        return argument.toPortionSizeMethod();
                    }

                }),
                associatedFoods,
                brands, categories);
    }

}