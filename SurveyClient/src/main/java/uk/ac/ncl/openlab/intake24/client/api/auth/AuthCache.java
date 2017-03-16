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

    private static String currentUserKey;
    private static String currentUserName;

    static {
        String cachedAccessToken = getCachedAccessToken();
        String cachedRefreshToken = getCachedRefreshToken();

        if (cachedAccessToken != null) {
            currentUserKey = getUserKeyFromJWT(cachedAccessToken);
            currentUserName = extractUserName(currentUserKey);

        } else if (cachedRefreshToken != null) {
            currentUserKey = getUserKeyFromJWT(cachedRefreshToken);
            currentUserName = extractUserName(currentUserKey);
        } else {
            currentUserKey = null;
            currentUserName = null;
        }

    }

    private static String getUserKeyFromJWT(String token) {
        String payloadBase64Url = token.split("\\.")[1];

        String payloadJson = new String(Base64Utils.fromBase64Url(payloadBase64Url));
        JSONObject payloadValue = JSONParser.parseStrict(payloadJson).isObject();
        String subjectJson = new String(Base64Utils.fromBase64(payloadValue.get("sub").isString().stringValue()));
        JSONObject subjectValue = JSONParser.parseStrict(subjectJson).isObject();

        String intake24UserKey = subjectValue.get("providerKey").isString().stringValue();

        return intake24UserKey;
    }

    private static String extractUserName(String key) {
        int index = key.indexOf('#');
        if (index == -1)
            return key;
        else
            return key.substring(index + 1);
    }

    public static void clear() {
        localStorage.removeItem(ACCESS_TOKEN_KEY);
        localStorage.removeItem(REFRESH_TOKEN_KEY);
    }

    public static void updateRefreshToken(String refreshToken) {
        localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
        currentUserKey = getUserKeyFromJWT(refreshToken);
        currentUserName = extractUserName(currentUserKey);
    }

    public static void updateAccessToken(String accessToken) {
        localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
        currentUserKey = getUserKeyFromJWT(accessToken);
        currentUserName = extractUserName(currentUserKey);
    }

    public static String getCachedRefreshToken() {
        return localStorage.getItem(REFRESH_TOKEN_KEY);
    }

    public static String getCachedAccessToken() {
        return localStorage.getItem(ACCESS_TOKEN_KEY);
    }

    public static Option<String> getCurrentUserKeyOption() {
        return Option.fromNullable(currentUserKey);
    }

    public static String getCurrentUserKey() {
        return getCurrentUserKeyOption().getOrDie("Current user key required, but has not been set");
    }

    public static Option<String> getCurrentUserNameOption() {
        return Option.fromNullable(currentUserName);
    }

    public static String getCurrentUserName() {
        return getCurrentUserNameOption().getOrDie("Current user name required, but has not been set");
    }

}
