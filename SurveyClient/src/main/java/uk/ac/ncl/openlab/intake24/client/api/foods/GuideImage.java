/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.api.foods;

import org.workcraft.gwt.imagemap.shared.ImageMap;

import java.util.Map;

public class GuideImage {
    public String description;
    public SImageMap imageMap;
    public Map<Integer, Double> weights;

    @Deprecated
    public GuideImage() {
    }

    public GuideImage(String description, SImageMap imageMap, Map<Integer, Double> weights) {
        this.description = description;
        this.imageMap = imageMap;
        this.weights = weights;
    }

    @Override
    public String toString() {
        return "GuideDef [description=" + description + ", imageMap=" + imageMap + ", weights=" + weights + "]";
    }

}
