/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package org.workcraft.gwt.imagechooser.shared;

public class ImageDef {
    public final String url;
    public final String thumbnailUrl;
    public final String label;

    public ImageDef(String url, String thumbnailUrl, String label) {
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.label = label;
    }
}
