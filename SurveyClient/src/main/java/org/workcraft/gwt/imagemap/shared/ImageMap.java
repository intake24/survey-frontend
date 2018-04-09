/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package org.workcraft.gwt.imagemap.shared;

public class ImageMap {
    public final String baseImageUrl;
    public final ImageMapObject[] objects;

    public ImageMap(String baseImageUrl, ImageMapObject[] objects) {
        this.baseImageUrl = baseImageUrl;
        this.objects = objects;
    }
}