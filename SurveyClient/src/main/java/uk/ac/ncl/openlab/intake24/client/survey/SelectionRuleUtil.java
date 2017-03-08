/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

public class SelectionRuleUtil {
    public static int selectedMealIndex(Survey survey) {
        return survey.selectedElement.accept(new Selection.Visitor<Integer>() {
            @Override
            public Integer visitMeal(Selection.SelectedMeal meal) {
                return meal.mealIndex;
            }

            @Override
            public Integer visitFood(Selection.SelectedFood food) {
                return food.mealIndex;
            }

            @Override
            public Integer visitNothing(Selection.EmptySelection selection) {
                return -1;
            }
        });
    }
}
