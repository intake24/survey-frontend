/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import java.util.List;
import java.util.Map;

public class CompletedMeal {
    public String name;
    public List<CompletedFood> foods;
    public List<CompletedMissingFood> missingFoods;
    public Time time;
    public Map<String, String> customData;

    @Deprecated
    public CompletedMeal() {
    }

    public CompletedMeal(String name, List<CompletedFood> foods, List<CompletedMissingFood> missingFoods, Time time, Map<String, String> customData) {
        this.name = name;
        this.foods = foods;
        this.missingFoods = missingFoods;
        this.time = time;
        this.customData = customData;
    }
}