/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;


import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;

import static org.workcraft.gwt.shared.client.CollectionUtils.indexOf;

public class SelectUnconfirmedMeal implements SelectionRule {
    @Override
    public Option<Selection> apply(Survey state) {
        int firstUnconfirmedIndex = indexOf(state.meals, new Function1<Meal, Boolean>() {
            @Override
            public Boolean apply(Meal argument) {
                return argument.time.isEmpty();
            }
        });

        if (firstUnconfirmedIndex == -1)
            return new Option.None<Selection>();
        else
            return new Option.Some<Selection>(new Selection.SelectedMeal(firstUnconfirmedIndex, SelectionMode.AUTO_SELECTION));
    }

    @Override
    public String toString() {
        return "Select unconfirmed meal";
    }

    public static WithPriority<SelectionRule> withPriority(int priority) {
        return new WithPriority<SelectionRule>(new SelectUnconfirmedMeal(), priority);
    }
}
