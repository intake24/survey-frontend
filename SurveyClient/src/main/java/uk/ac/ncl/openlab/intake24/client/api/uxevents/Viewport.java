package uk.ac.ncl.openlab.intake24.client.api.uxevents;

public class Viewport {
    public int windowInnerHeight;
    public int windowInnerWidth;
    public int maxWindowPageYOffset;
    public int bodyOffsetHeight;

    @Deprecated
    public Viewport() {
    }

    public Viewport(int windowInnerHeight, int windowInnerWidth, int maxWindowPageYOffset, int bodyOffsetHeight) {
        this.windowInnerHeight = windowInnerHeight;
        this.windowInnerWidth = windowInnerWidth;
        this.maxWindowPageYOffset = maxWindowPageYOffset;
        this.bodyOffsetHeight = bodyOffsetHeight;
    }

    public static native int getWindowInnerHeight() /*-{
        return $wnd.innerHeight;
    }-*/;

    public static native int getWindowInnerWidth() /*-{
        return $wnd.innerWidth;
    }-*/;

    public static native int getMaxWindowPageYOffset() /*-{
        return $wnd.pageYOffset;
    }-*/;

    public static native int getBodyOffsetHeight() /*-{
        return $wnd.document.body.offsetHeight;
    }-*/;

    public static Viewport getCurrent() {
        return new Viewport(getWindowInnerHeight(), getWindowInnerWidth(), getMaxWindowPageYOffset(), getBodyOffsetHeight());
    }
}
