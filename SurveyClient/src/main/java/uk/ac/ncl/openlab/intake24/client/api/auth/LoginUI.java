package uk.ac.ncl.openlab.intake24.client.api.auth;

import org.workcraft.gwt.shared.client.Callback1;

public interface LoginUI {

    void show(Callback1<Credentials> attemptLogin);
    void hide();

    void onLoginAttemptFailed();
    void onLoginServiceError();
}
