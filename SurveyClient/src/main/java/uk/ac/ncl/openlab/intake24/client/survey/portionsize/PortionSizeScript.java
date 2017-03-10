/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.portionsize;

import org.pcollections.PMap;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodData;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;

/**
 * Generates associatedFoods for portion size estimation.
 */
public interface PortionSizeScript {
    public Option<SimplePrompt<UpdateFunc>> nextPrompt(PMap<String, String> data, FoodData foodData);
}
