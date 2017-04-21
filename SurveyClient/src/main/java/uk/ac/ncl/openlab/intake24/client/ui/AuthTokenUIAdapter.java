package uk.ac.ncl.openlab.intake24.client.ui;

import uk.ac.ncl.openlab.intake24.client.api.auth.AuthTokenUI;

public class AuthTokenUIAdapter implements AuthTokenUI {
    private AutoOverlay autoOverlay = new AutoOverlay();
    private AuthTokenForm form;


    @Override
    public void onSigninAttemptFailed() {
        form = new AuthTokenForm();
        form.showInvalidToken();
        autoOverlay.show(form);
    }

    @Override
    public void onAuthenticationServiceError() {
        form = new AuthTokenForm();
        form.showServiceError();
        autoOverlay.show(form);
    }
}
