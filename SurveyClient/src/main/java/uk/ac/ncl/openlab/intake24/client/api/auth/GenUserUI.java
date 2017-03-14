package uk.ac.ncl.openlab.intake24.client.api.auth;

import org.workcraft.gwt.shared.client.Callback;

public interface GenUserUI {
    void show();
    void hide();

    void onCredentialsReceived(GeneratedCredentials credentials, Callback onCredentialsAcknowledged);
    void onForbidden();
    void onServiceError();
}
