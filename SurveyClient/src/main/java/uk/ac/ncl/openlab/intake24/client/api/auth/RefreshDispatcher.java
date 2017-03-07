package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.storage.client.Storage;
import org.fusesource.restygwt.client.Dispatcher;
import org.fusesource.restygwt.client.Method;
import uk.ac.ncl.openlab.intake24.client.ui.LoginUIAdapter;

import java.util.logging.Logger;

public class RefreshDispatcher implements Dispatcher {

    public static final RefreshDispatcher INSTANCE = new RefreshDispatcher();
    private static final Logger logger = Logger.getLogger(RefreshDispatcher.class.getName());

    private Storage localStorage = Storage.getLocalStorageIfSupported();

    private static final LoginUIAdapter loginUIAdapter = new LoginUIAdapter();

    @Override
    public Request send(Method method, RequestBuilder builder) throws RequestException {

        logger.fine("Sending refresh request: " + builder.getHTTPMethod() + " " + builder.getUrl());

        String cachedRefreshToken = localStorage.getItem(AuthCache.REFRESH_TOKEN_KEY);

        if (cachedRefreshToken != null)
            builder.setHeader(AuthCache.AUTH_TOKEN_HEADER, cachedRefreshToken);

        RequestCallback userCallback = builder.getCallback();

        builder.setCallback(new RefreshCallback(method, userCallback, loginUIAdapter));

        return builder.send();
    }
}