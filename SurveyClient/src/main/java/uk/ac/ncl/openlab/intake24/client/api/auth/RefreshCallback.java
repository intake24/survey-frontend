package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Callback;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;
import uk.ac.ncl.openlab.intake24.client.api.errors.ErrorReportingService;

import java.util.logging.Logger;

public class RefreshCallback implements RequestCallback {

    private static final Logger logger = Logger.getLogger(RefreshCallback.class.getName());

    private final Method refreshMethod;
    private final RequestCallback refreshCallback;
    private final LoginUI loginUI;
    private final GenUserUI genUserUI;


    public RefreshCallback(Method method, RequestCallback userCallback, LoginUI loginUI, GenUserUI genUserUI) {
        this.refreshMethod = method;
        this.refreshCallback = userCallback;
        this.loginUI = loginUI;
        this.genUserUI = genUserUI;
    }

    @Override
    public void onResponseReceived(final Request request, Response response) {
        int code = response.getStatusCode();

        if (code == Response.SC_UNAUTHORIZED) {

            if (Window.Location.getParameter("genUser") != null) {
                genUserUI.show();

                GeneratedUsersService.INSTANCE.generateUser(EmbeddedData.getSurveyId(), new MethodCallback<GeneratedCredentials>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        if (method.getResponse().getStatusCode() == 403)
                            genUserUI.onForbidden();
                        else
                            genUserUI.onServiceError();
                    }

                    @Override
                    public void onSuccess(Method method, GeneratedCredentials response) {

                        genUserUI.onCredentialsReceived(response, new Callback() {
                            @Override
                            public void call() {
                                AuthenticationService.INSTANCE.signin(new Credentials(Option.some(EmbeddedData.getSurveyId()), response.userName, response.password), new MethodCallback<SigninResult>() {
                                    @Override
                                    public void onFailure(Method signinMethod, Throwable exception) {
                                        ErrorReportingService.reportError(new RuntimeException("Failed to generate user", exception));
                                        genUserUI.onServiceError();
                                    }

                                    @Override
                                    public void onSuccess(Method signinMethod, SigninResult response) {
                                        AuthCache.updateRefreshToken(response.refreshToken);
                                        //TODO: User HTML5 history API to prevent reload
                                        Window.Location.replace(Window.Location.createUrlBuilder().removeParameter("genUser").buildString());
                                    }
                                });
                            }
                        });
                    }
                });
            } else {
                loginUI.show(new Callback1<Credentials>() {
                    @Override
                    public void call(Credentials credentials) {
                        AuthenticationService.INSTANCE.signin(credentials, new MethodCallback<SigninResult>() {
                            @Override
                            public void onFailure(Method signinMethod, Throwable exception) {
                                if (signinMethod.getResponse().getStatusCode() == Response.SC_UNAUTHORIZED) {
                                    loginUI.onLoginAttemptFailed();
                                } else {
                                    ErrorReportingService.reportError(new RuntimeException("Login service error", exception));
                                    loginUI.onLoginServiceError();
                                }
                            }

                            @Override
                            public void onSuccess(Method signinMethod, SigninResult response) {
                                loginUI.hide();

                                logger.fine("Login with provided credentials successful");

                                AuthCache.updateRefreshToken(response.refreshToken);

                                refreshMethod.header(AuthCache.AUTH_TOKEN_HEADER, response.refreshToken);

                                try {
                                    refreshMethod.send(RefreshCallback.this);
                                } catch (RequestException e) {
                                    refreshCallback.onError(signinMethod.getRequest(), e);
                                }

                            }
                        });
                    }
                });
            }
        } else {
            refreshCallback.onResponseReceived(request, response);
        }
    }

    @Override
    public void onError(Request request, Throwable throwable) {
        refreshCallback.onError(request, throwable);
    }
}