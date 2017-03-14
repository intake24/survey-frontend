package uk.ac.ncl.openlab.intake24.client.ui;

import org.workcraft.gwt.shared.client.Callback;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.api.auth.Credentials;
import uk.ac.ncl.openlab.intake24.client.api.auth.GenUserUI;
import uk.ac.ncl.openlab.intake24.client.api.auth.GeneratedCredentials;
import uk.ac.ncl.openlab.intake24.client.api.auth.LoginUI;

public class GenUserUIAdapter implements GenUserUI {

    private OverlayDiv overlayDiv = new OverlayDiv();

    private boolean usingOverlay;
    private GenUserForm form;

    public void show() {
        usingOverlay = Layout.UIRootPanel.getWidgetCount() > 0;

        form = new GenUserForm();

        if (usingOverlay) {
            overlayDiv.setContents(form);
            overlayDiv.setVisible(true);
        } else {
            Layout.UIRootPanel.add(form);
        }
    }

    public void hide() {
        if (usingOverlay) {
            overlayDiv.setVisible(false);
            overlayDiv.setContents(null);
            form = null;
        } else {
            Layout.UIRootPanel.remove(form);
        }
    }

    @Override
    public void onCredentialsReceived(GeneratedCredentials credentials, Callback onCredentialsAcknowledged) {
        form.showCredentials(credentials, onCredentialsAcknowledged);
    }

    @Override
    public void onForbidden() {
        form.showForbidden();

    }

    @Override
    public void onServiceError() {
        form.showServiceError();

    }
}
