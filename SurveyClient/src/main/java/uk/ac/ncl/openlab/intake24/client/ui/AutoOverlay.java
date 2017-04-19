package uk.ac.ncl.openlab.intake24.client.ui;

import com.google.gwt.user.client.ui.Widget;

public class AutoOverlay {
    private OverlayDiv overlayDiv = new OverlayDiv();

    private boolean usingOverlay;
    private Widget widget;

    public void show(Widget widget) {
        usingOverlay = willUseOverlay();

        this.widget = widget;

        if (usingOverlay) {
            overlayDiv.setContents(widget);
            overlayDiv.setVisible(true);
        } else {
            Layout.UIRootPanel.add(widget);
        }
    }

    public void hide() {
        if (usingOverlay) {
            overlayDiv.setVisible(false);
            overlayDiv.setContents(null);
        } else {
            Layout.UIRootPanel.remove(widget);
        }
        widget = null;
    }

    public boolean willUseOverlay() {
        return Layout.UIRootPanel.getWidgetCount() > 0;
    }
}
