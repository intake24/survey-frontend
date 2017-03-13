package uk.ac.ncl.openlab.intake24.client.api.foods;

import org.workcraft.gwt.imagemap.shared.ImageMap;
import org.workcraft.gwt.imagemap.shared.ImageMapObject;

public class SImageMap {

    public String baseImageUrl;
    public SImageMapObject[] objects;

    public ImageMap toImageMap() {

        ImageMapObject[] imo = new ImageMapObject[objects.length];

        for (int i = 0; i < objects.length; i++) {
            imo[i] = objects[i].toImageMapObject();
        }

        return new ImageMap(baseImageUrl, imo);
    }
}
