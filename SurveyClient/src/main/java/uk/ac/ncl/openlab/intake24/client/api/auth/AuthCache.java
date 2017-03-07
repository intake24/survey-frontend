package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.storage.client.Storage;

public class AuthCache {
    public final static String ACCESS_TOKEN_KEY = "accessToken";
    public final static String REFRESH_TOKEN_KEY = "refreshToken";
    public final static String AUTH_TOKEN_HEADER = "X-Auth-Token";

    public static void clear() {
        Storage.getLocalStorageIfSupported().removeItem(ACCESS_TOKEN_KEY);
        Storage.getLocalStorageIfSupported().removeItem(REFRESH_TOKEN_KEY);
    }
}
