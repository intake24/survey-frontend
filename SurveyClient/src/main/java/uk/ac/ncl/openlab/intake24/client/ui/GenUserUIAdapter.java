package uk.ac.ncl.openlab.intake24.client.ui;

import org.workcraft.gwt.shared.client.Callback;
import uk.ac.ncl.openlab.intake24.client.api.auth.GenUserUI;
import uk.ac.ncl.openlab.intake24.client.api.auth.GeneratedCredentials;

public class GenUserUIAdapter implements GenUserUI {

    private AutoOverlay autoOverlay = new AutoOverlay();
    private GenUserForm form;

    public void show() {
        form = new GenUserForm();
        autoOverlay.show(form);
    }

    public void hide() {
        autoOverlay.hide();
        form = null;
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
