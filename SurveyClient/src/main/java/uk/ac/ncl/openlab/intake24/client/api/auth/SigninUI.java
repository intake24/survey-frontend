package uk.ac.ncl.openlab.intake24.client.api.auth;

import org.workcraft.gwt.shared.client.Callback1;

public interface SigninUI {

    void show(Callback1<Credentials> attemptSignin);
    void hide();

    void onSigninAttemptFailed();
    void onAuthenticationServiceError();
}
