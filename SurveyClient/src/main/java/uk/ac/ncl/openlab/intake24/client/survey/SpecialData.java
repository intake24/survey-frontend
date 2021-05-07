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

package uk.ac.ncl.openlab.intake24.client.survey;

import uk.ac.ncl.openlab.intake24.client.api.foods.PortionSizeMethod;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.MilkInHotDrinkPortionSizeScript;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionDescriptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpecialData {
    public static final String FOOD_CODE_SALAD = "$SLD";
    public static final String FOOD_CODE_SANDWICH = "$SND";
    public static final String FOOD_CODE_MISSING = "$MIS";
    public static final String FOOD_CODE_MILK_IN_HOT_DRINK = "MHDK";
    public static final String FOOD_CODE_MILK_ON_CEREAL = "MCRL";
    public static final String FOOD_CODE_COFFEE = "CFFB";
    public static final String CATEGORY_CODE_RECIPE_INGREDIENTS = "RECP";
    public static final String CATEGORY_BREAD_TOP_LEVEL = "BRED";
    public static final String CATEGORY_TEA_CODE = "TEAS";
    public static final String CATEGORY_BREAKFAST_CEREALS = "CRLS";
    public static final String CATEGORY_CEREAL_BARS = "CRBR";
    public static final String CATEGORY_HOT_CEREALS = "HTCL";
    public static final String CATEGORY_WEETABIX_CEREALS = "WTBI";
    public static final List<String> CATEGORIES_CEREAL_NO_MILK = Arrays.asList(CATEGORY_CEREAL_BARS, CATEGORY_HOT_CEREALS, CATEGORY_WEETABIX_CEREALS);

    // This will only ever be used as the only available portion size method and the method selection
    // screen won't be shown, so it doesn't matter what image to use.
    // The placeholder base64 encoded image is a 1x1 white pixel .png
    public static final PortionSizeMethod milkInAHotDrinkPortionSizeMethod = new PortionSizeMethod(
            MilkInHotDrinkPortionSizeScript.name,
            PortionDescriptions.Util.getInstance().use_a_standard_measure(),
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+ip1sAAAAASUVORK5CYII=",
            false,
            1.0,
            new HashMap<>()
    );
}
