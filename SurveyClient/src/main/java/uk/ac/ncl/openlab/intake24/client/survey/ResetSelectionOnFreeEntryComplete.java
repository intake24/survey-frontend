/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.workcraft.gwt.shared.client.Function2;

public class ResetSelectionOnFreeEntryComplete implements Function2<Survey, Survey, Survey> {

    @Override
    public Survey apply(Survey s0, Survey s1) {
        // When free entry is complete, force selection to go back to the first meal so that
        // the question order makes sense.

        // Otherwise, the last entered food will remain selected, then the foods in the last entered
        // meal and only then the first meal.

        if (!s0.freeEntryComplete() && s1.freeEntryComplete() && s1.meals.size() > 0) {
            return s1.withSelection(new Selection.SelectedMeal(0, SelectionMode.AUTO_SELECTION));
        }
        else
            return s1;
    }
}
