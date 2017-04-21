package uk.ac.ncl.openlab.intake24.client.api.auth;


import com.google.gwt.http.client.*;
import com.google.gwt.storage.client.Storage;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import uk.ac.ncl.openlab.intake24.client.api.errors.ErrorReportingService;

import java.util.logging.Logger;

public class AccessCallback implements RequestCallback {

    private static final Logger logger = Logger.getLogger(AccessCallback.class.getName());

    private final Method serviceMethod;
    private final RequestCallback serviceCallback;

    private boolean refreshAttempted = false;


    public AccessCallback(Method serviceMethod, RequestCallback serviceCallback) {
        this.serviceMethod = serviceMethod;
        this.serviceCallback = serviceCallback;
    }

    @Override
    public void onResponseReceived(final Request request, Response response) {
        int code = response.getStatusCode();

        if (code == Response.SC_UNAUTHORIZED) {
            if (refreshAttempted) {
                Throwable exception = new RuntimeException("Refreshed access token not recognized");
                ErrorReportingService.reportError(exception);
                serviceCallback.onError(request, exception);
            } else {
                AuthenticationService.INSTANCE.refresh(new MethodCallback<RefreshResult>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        ErrorReportingService.reportError(new RuntimeException("Access token refresh call failed", exception));
                        serviceCallback.onError(request, exception);
                    }

                    @Override
                    public void onSuccess(Method method, RefreshResult response) {
                        refreshAttempted = true;

                        AuthCache.updateAccessToken(response.accessToken);
                        method.header("X-Auth-Token", response.accessToken);

                        try {
                            serviceMethod.send(serviceCallback);
                        } catch (RequestException e) {
                            ErrorReportingService.reportError(new RuntimeException("API call failed", e));
                            serviceCallback.onError(request, e);
                        }
                    }
                });
            }
        } else {
            if (response.getStatusCode() != 200 && response.getStatusCode() != 429) {
                ErrorReportingService.reportError(new RuntimeException("Unexpected API response: " + response.getStatusCode()));
            }

            serviceCallback.onResponseReceived(request, response);
        }
    }

    @Override
    public void onError(Request request, Throwable throwable) {
        ErrorReportingService.reportError(new RuntimeException("API call failed", throwable));
        serviceCallback.onError(request, throwable);
    }
}