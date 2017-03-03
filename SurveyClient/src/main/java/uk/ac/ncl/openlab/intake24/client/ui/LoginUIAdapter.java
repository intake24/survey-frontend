package uk.ac.ncl.openlab.intake24.client.ui;

import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.api.auth.Credentials;
import uk.ac.ncl.openlab.intake24.client.api.auth.LoginUI;

public class LoginUIAdapter implements LoginUI {

    private OverlayDiv overlayDiv = new OverlayDiv();
    private LoginForm form;

    public void show(Callback1<Credentials> attemptLogin) {
        form = new LoginForm(attemptLogin, "test");
        overlayDiv.setContents(form);
        overlayDiv.setVisible(true);
    }

    public void hide() {
        overlayDiv.setVisible(false);
        overlayDiv.setContents(null);
        form = null;
    }

    public void onLoginAttemptFailed() {
        form.onLoginAttemptFailed();

    }

    public void onLoginServiceError() {
        form.onLoginServiceError();
    }
}
