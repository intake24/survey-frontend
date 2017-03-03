package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.storage.client.Storage;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function0;

import java.util.logging.Logger;

public class RefreshCallback implements RequestCallback {

    private static final Logger logger = Logger.getLogger(RefreshCallback.class.getName());

    private final Method method;
    private final RequestCallback userCallback;
    private final LoginUI loginUI;


    public RefreshCallback(Method method, RequestCallback userCallback, LoginUI loginUI) {
        this.method = method;
        this.userCallback = userCallback;
        this.loginUI = loginUI;
    }

    @Override
    public void onResponseReceived(final Request request, Response response) {
        int code = response.getStatusCode();

        if (code == Response.SC_OK) {
            userCallback.onResponseReceived(request, response);
        } else if (code == Response.SC_UNAUTHORIZED) {

            loginUI.show(new Callback1<Credentials>() {
                @Override
                public void call(Credentials credentials) {
                    AuthenticationService.INSTANCE.signin(credentials, new MethodCallback<SigninResult>() {
                        @Override
                        public void onFailure(Method signinMethod, Throwable exception) {
                            if (signinMethod.getResponse().getStatusCode() == Response.SC_UNAUTHORIZED) {
                                loginUI.onLoginAttemptFailed();
                            } else
                                loginUI.onLoginServiceError();
                        }

                        @Override
                        public void onSuccess(Method signinMethod, SigninResult response) {
                            loginUI.hide();

                            Storage.getLocalStorageIfSupported().setItem(Constants.REFRESH_TOKEN_KEY, response.refreshToken);
                            method.header(Constants.AUTH_TOKEN_HEADER, response.refreshToken);

                            try {
                                method.send(RefreshCallback.this);
                            } catch (RequestException e) {
                                userCallback.onError(signinMethod.getRequest(), e);
                            }

                        }
                    });
                }
            });
        }
    }

    @Override
    public void onError(Request request, Throwable throwable) {
        userCallback.onError(request, throwable);
    }
}