package org.workcraft.gwt.imagemap.shared;

public class ImageMapObject {
    public final int id;
    public final String overlayUrl;
    public final Polygon outline;

    public ImageMapObject(int id, String overlayUrl, Polygon outline) {
        this.id = id;
        this.overlayUrl = overlayUrl;
        this.outline = outline;
    }
}
