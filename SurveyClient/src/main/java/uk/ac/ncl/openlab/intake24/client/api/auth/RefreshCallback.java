package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Callback;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;
import uk.ac.ncl.openlab.intake24.client.UncaughtExceptionHandler;

import java.util.logging.Logger;

public class RefreshCallback implements RequestCallback {

  private static final Logger logger = Logger.getLogger(RefreshCallback.class.getName());

  private final Method refreshMethod;
  private final RequestCallback refreshCallback;
  private final SigninUI loginUI;
  private final GenUserUI genUserUI;
  private final AuthTokenUI authTokenUI;


  public RefreshCallback(Method method, RequestCallback userCallback, SigninUI loginUI, GenUserUI genUserUI, AuthTokenUI authTokenUI) {
    this.refreshMethod = method;
    this.refreshCallback = userCallback;
    this.loginUI = loginUI;
    this.genUserUI = genUserUI;
    this.authTokenUI = authTokenUI;
  }

  @Override
  public void onResponseReceived(final Request request, Response response) {
    int code = response.getStatusCode();

    if (code == Response.SC_UNAUTHORIZED) {

      if (Window.Location.getParameter(UrlParameterConstants.generateUserKey) != null) {
        genUserUI.show();

        GeneratedUsersService.INSTANCE.generateUser(EmbeddedData.surveyId, new MethodCallback<GeneratedCredentials>() {
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
                AuthenticationService.INSTANCE.signinWithAlias(new Credentials(EmbeddedData.surveyId, response.userName, response.password), new MethodCallback<SigninResult>() {
                  @Override
                  public void onFailure(Method signinMethod, Throwable exception) {
                    UncaughtExceptionHandler.reportError(new RuntimeException("Failed to generate user", exception));
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
      } else if (Window.Location.getParameter(UrlParameterConstants.authTokenKey) != null) {
        String authToken = Window.Location.getParameter(UrlParameterConstants.authTokenKey);

        AuthenticationService.INSTANCE.signinWithToken(authToken, new MethodCallback<SigninResult>() {
          @Override
          public void onFailure(Method signinMethod, Throwable exception) {
            if (signinMethod.getResponse().getStatusCode() == Response.SC_UNAUTHORIZED) {

              if (!EmbeddedData.originatingUrl.isEmpty())
                Window.Location.replace(EmbeddedData.originatingUrl.getOrDie());

              // authTokenUI.onSigninAttemptFailed();
            } else {
              UncaughtExceptionHandler.reportError(new RuntimeException("Authentication service error while trying to sign in with URL auth token", exception));
              authTokenUI.onAuthenticationServiceError();
            }
          }

          @Override
          public void onSuccess(Method signinMethod, SigninResult response) {
            AuthCache.updateRefreshToken(response.refreshToken);

            String urlWithoutToken = Window.Location.createUrlBuilder().removeParameter(UrlParameterConstants.authTokenKey).buildString();

            Window.Location.replace(urlWithoutToken);

                        /*
                        refreshMethod.header(AuthCache.AUTH_TOKEN_HEADER, response.refreshToken);

                        try {
                            refreshMethod.send(RefreshCallback.this);
                        } catch (RequestException e) {
                            refreshCallback.onError(signinMethod.getRequest(), e);
                        }*/
          }
        });
      } else if (!EmbeddedData.originatingUrl.isEmpty()) {
        Window.Location.replace(EmbeddedData.originatingUrl.getOrDie());
      } else {
        loginUI.show(new Callback1<Credentials>() {
          @Override
          public void call(Credentials credentials) {
            AuthenticationService.INSTANCE.signinWithAlias(credentials, new MethodCallback<SigninResult>() {
              @Override
              public void onFailure(Method signinMethod, Throwable exception) {
                if (signinMethod.getResponse().getStatusCode() == Response.SC_UNAUTHORIZED) {
                  loginUI.onSigninAttemptFailed();
                } else {
                  UncaughtExceptionHandler.reportError(new RuntimeException("Authentication service error while trying to sign in with credentials", exception));
                  loginUI.onAuthenticationServiceError();
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