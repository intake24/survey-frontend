package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.storage.client.Storage;
import org.workcraft.gwt.shared.client.Option;

public class AuthCache {
    public final static String ACCESS_TOKEN_KEY = "accessToken";
    public final static String REFRESH_TOKEN_KEY = "refreshToken";
    public final static String AUTH_TOKEN_HEADER = "X-Auth-Token";

    private final static Storage localStorage = Storage.getLocalStorageIfSupported();

    private static String currentUserId;

    static {
        String cachedAccessToken = getCachedAccessToken();
        String cachedRefreshToken = getCachedRefreshToken();

        if (cachedAccessToken != null)
            currentUserId = getUserIdFromJWT(cachedAccessToken);
        else if (cachedRefreshToken != null)
            currentUserId = getUserIdFromJWT(cachedRefreshToken);
        else
            currentUserId = null;
    }

    private static String getUserIdFromJWT(String token) {
        String payloadBase64Url = token.split("\\.")[1];

        String payloadJson = new String(Base64Utils.fromBase64Url(payloadBase64Url));
        JSONObject payloadValue = JSONParser.parseStrict(payloadJson).isObject();

        return payloadValue.get("userId").toString();
    }

    public static void clear() {
        localStorage.removeItem(ACCESS_TOKEN_KEY);
        localStorage.removeItem(REFRESH_TOKEN_KEY);
    }

    public static void updateRefreshToken(String refreshToken) {
        localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
        currentUserId = getUserIdFromJWT(refreshToken);
    }

    public static void updateAccessToken(String accessToken) {
        localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
        currentUserId = getUserIdFromJWT(accessToken);
    }

    public static String getCachedRefreshToken() {
        return localStorage.getItem(REFRESH_TOKEN_KEY);
    }

    public static String getCachedAccessToken() {
        return localStorage.getItem(ACCESS_TOKEN_KEY);
    }

    public static Option<String> getCurrentUserIdOption() {
        return Option.fromNullable(currentUserId);
    }

    public static String getCurrentUserId() {
        return getCurrentUserIdOption().getOrDie("Current user id required, but is not known");
    }
}
