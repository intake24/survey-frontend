package uk.ac.ncl.openlab.intake24.client.ui;

import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.api.auth.Credentials;
import uk.ac.ncl.openlab.intake24.client.api.auth.SigninUI;

public class LoginUIAdapter implements SigninUI {

    private LoginForm form;

    private AutoOverlay autoOverlay = new AutoOverlay();


    public void show(Callback1<Credentials> attemptSignin) {
        form = new LoginForm(attemptSignin, autoOverlay.willUseOverlay());
        autoOverlay.show(form);

    }

    public void hide() {
        autoOverlay.hide();
        form = null;
    }

    public void onSigninAttemptFailed() {
        form.onLoginAttemptFailed();
    }

    public void onAuthenticationServiceError() {
        form.onLoginServiceError();
    }
}
