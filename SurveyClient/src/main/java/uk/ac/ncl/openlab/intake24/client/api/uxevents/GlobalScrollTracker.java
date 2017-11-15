package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import com.google.gwt.user.client.Window;

public class GlobalScrollTracker {

    public static final GlobalScrollTracker INSTANCE = new GlobalScrollTracker();

    private int maxYOffset = 0;

    private GlobalScrollTracker() {
        Window.addWindowScrollHandler(new Window.ScrollHandler() {
            @Override
            public void onWindowScroll(Window.ScrollEvent scrollEvent) {
                maxYOffset = Math.max(scrollEvent.getScrollTop(), maxYOffset);
            }
        });
    }

    public void resetMaxYOffset() {
        maxYOffset = 0;
    }

    public int getCurrentMaxYOffset() {
        return maxYOffset;
    }
}
