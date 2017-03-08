package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.http.client.*;
import com.google.gwt.storage.client.Storage;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;

import java.util.logging.Logger;

public class AccessDispatcher implements Dispatcher {

    public static final AccessDispatcher INSTANCE = new AccessDispatcher();
    private static final Logger logger = Logger.getLogger(AccessDispatcher.class.getName());

    @Override
    public Request send(Method method, RequestBuilder builder) throws RequestException {

        String content = builder.getRequestData();

        if (content != null && content.length() > 0) {
            logger.fine(content);
        }

        String cachedAccessToken = AuthCache.getCachedAccessToken();

        if (cachedAccessToken != null)
            builder.setHeader(AuthCache.AUTH_TOKEN_HEADER, cachedAccessToken);

        RequestCallback userCallback = builder.getCallback();

        builder.setCallback(new AccessCallback(method, userCallback));

        return builder.send();
    }
}