package uk.ac.ncl.openlab.intake24.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.api.auth.Credentials;
import uk.ac.ncl.openlab.intake24.client.api.auth.LoginUI;

public class LoginUIAdapter implements LoginUI {

    private OverlayDiv overlayDiv = new OverlayDiv();

    private boolean usingOverlay;
    private LoginForm form;


    public void show(Callback1<Credentials> attemptLogin) {
        usingOverlay = Layout.UIRootPanel.getWidgetCount() > 0;

        form = new LoginForm(attemptLogin, usingOverlay);

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

    public void onLoginAttemptFailed() {
        form.onLoginAttemptFailed();
    }

    public void onLoginServiceError() {
        form.onLoginServiceError();
    }
}
