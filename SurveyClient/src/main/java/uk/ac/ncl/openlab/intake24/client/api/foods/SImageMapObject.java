package uk.ac.ncl.openlab.intake24.client.api.foods;

import org.workcraft.gwt.imagemap.shared.ImageMapObject;
import org.workcraft.gwt.imagemap.shared.Polygon;

public class SImageMapObject {
    public int id;
    public String description;
    public String overlayUrl;
    public double[] outline;

    public ImageMapObject toImageMapObject() {
        return new ImageMapObject(id, overlayUrl, new Polygon(outline));
    }
}
