package uk.ac.ncl.openlab.intake24.client.api.auth;


import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.storage.client.Storage;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.logging.Logger;

public class AccessCallback implements RequestCallback {

    private static final Logger logger = Logger.getLogger(AccessCallback.class.getName());


    private final Method method;
    private final RequestCallback userCallback;

    private boolean refreshAttempted = false;


    public AccessCallback(Method method, RequestCallback userCallback) {
        this.method = method;
        this.userCallback = userCallback;
    }

    @Override
    public void onResponseReceived(final Request request, Response response) {
        int code = response.getStatusCode();

        if (code == Response.SC_UNAUTHORIZED) {
            if (refreshAttempted) {
                logger.fine("Access token rejected after refresh");
                userCallback.onError(request, new RuntimeException("Refreshed access token not recognized"));
            } else {
                logger.fine("Access token rejected, attempting refresh");
                AuthenticationService.INSTANCE.refresh(new MethodCallback<RefreshResult>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        userCallback.onError(request, exception);
                    }

                    @Override
                    public void onSuccess(Method method, RefreshResult response) {
                        refreshAttempted = true;

                        Storage.getLocalStorageIfSupported().setItem(Constants.ACCESS_TOKEN_KEY, response.accessToken);
                        method.header("X-Auth-Token", response.accessToken);

                        try {
                            method.send(AccessCallback.this);
                        } catch (RequestException e) {
                            userCallback.onError(request, e);
                        }
                    }
                });
            }
        } else
            userCallback.onResponseReceived(request, response);
    }

    @Override
    public void onError(Request request, Throwable throwable) {
        userCallback.onError(request, throwable);
    }
}