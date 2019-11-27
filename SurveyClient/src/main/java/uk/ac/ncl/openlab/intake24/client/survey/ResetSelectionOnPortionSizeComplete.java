/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.workcraft.gwt.shared.client.Function2;

public class ResetSelectionOnPortionSizeComplete implements Function2<Survey, Survey, Survey> {

    @Override
    public Survey apply(Survey s0, Survey s1) {
        // When portion size estimation in a meal is complete, force selection to go back to the first food
        // so that the question order makes sense.

        return s0.selectedElement.accept(new Selection.Visitor<Survey>() {
            @Override
            public Survey visitMeal(Selection.SelectedMeal meal) {
                return s1;
            }

            @Override
            public Survey visitFood(Selection.SelectedFood selection) {
                if (s0.meals.size() != s1.meals.size()) {
                    // Meal structure changed, nothing we can do
                    return s1;
                } else {
                    Meal m0 = s0.meals.get(selection.mealIndex);
                    Meal m1 = s1.meals.get(selection.mealIndex);

                    if (!m0.portionSizeComplete() && m1.portionSizeComplete()) {
                        return s1.withSelection(new Selection.SelectedFood(selection.mealIndex, 0, SelectionMode.AUTO_SELECTION));
                    } else
                        return s1;
                }
            }

            @Override
            public Survey visitNothing(Selection.EmptySelection selection) {
                return s1;
            }
        });
    }
}
