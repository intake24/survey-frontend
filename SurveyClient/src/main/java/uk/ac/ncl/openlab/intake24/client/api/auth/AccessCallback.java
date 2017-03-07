package uk.ac.ncl.openlab.intake24.client.api.auth;


import com.google.gwt.http.client.*;
import com.google.gwt.storage.client.Storage;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

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
                logger.fine("Access token rejected after refresh");
                serviceCallback.onError(request, new RuntimeException("Refreshed access token not recognized"));
            } else {
                logger.fine("Access token rejected, attempting refresh");
                AuthenticationService.INSTANCE.refresh(new MethodCallback<RefreshResult>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        serviceCallback.onError(request, exception);
                    }

                    @Override
                    public void onSuccess(Method method, RefreshResult response) {
                        refreshAttempted = true;

                        logger.fine("Refresh successful, retrying service request");

                        Storage.getLocalStorageIfSupported().setItem(AuthCache.ACCESS_TOKEN_KEY, response.accessToken);
                        method.header("X-Auth-Token", response.accessToken);

                        try {
                            serviceMethod.send(serviceCallback);
                        } catch (RequestException e) {
                            serviceCallback.onError(request, e);
                        }
                    }
                });
            }
        } else
            serviceCallback.onResponseReceived(request, response);
    }

    @Override
    public void onError(Request request, Throwable throwable) {
        serviceCallback.onError(request, throwable);
    }
}