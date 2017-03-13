/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.api.foods;


public class DrinkwareSet {
    public String guideId;
    public DrinkScale[] scales;

    @Deprecated
    public DrinkwareSet() {
    }

    public DrinkwareSet(String guideId, DrinkScale[] scaleDefs) {
        this.guideId = guideId;
        this.scales = scaleDefs;
    }
}
