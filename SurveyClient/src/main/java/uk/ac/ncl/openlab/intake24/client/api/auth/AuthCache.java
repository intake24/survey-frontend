package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import uk.ac.ncl.openlab.intake24.client.BrowserConsole;

public class AuthCache {
    public final static String ACCESS_TOKEN_KEY = "accessToken";
    public final static String REFRESH_TOKEN_KEY = "refreshToken";
    public final static String AUTH_TOKEN_HEADER = "X-Auth-Token";

    private final static Storage localStorage = Storage.getLocalStorageIfSupported();

    private static String currentUserName;

    static {
        String cachedAccessToken = getCachedAccessToken();
        String cachedRefreshToken = getCachedRefreshToken();

        if (cachedAccessToken != null)
            currentUserName = getUserNameFromJWT(cachedAccessToken);
        else if (cachedRefreshToken != null)
            currentUserName = getUserNameFromJWT(cachedRefreshToken);
        else
            currentUserName = null;
    }

    private static String getUserNameFromJWT(String token) {

        String payloadBase64Url = token.split("\\.")[1];

        String payloadJson = new String(Base64Utils.fromBase64Url(payloadBase64Url));
        JSONObject payloadValue = JSONParser.parseStrict(payloadJson).isObject();
        String subjectJson = new String(Base64Utils.fromBase64(payloadValue.get("sub").isString().stringValue()));
        JSONObject subjectValue = JSONParser.parseStrict(subjectJson).isObject();

        String intake24UserKey = subjectValue.get("providerKey").isString().stringValue();

        BrowserConsole.warn(intake24UserKey);

        return intake24UserKey;
    }

    public static void clear() {
        localStorage.removeItem(ACCESS_TOKEN_KEY);
        localStorage.removeItem(REFRESH_TOKEN_KEY);
    }

    public static void updateRefreshToken(String refreshToken) {
        localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
        currentUserName = getUserNameFromJWT(refreshToken);
    }

    public static void updateAccessToken(String accessToken) {
        localStorage.setItem(ACCESS_TOKEN_KEY, accessToken);
        currentUserName = getUserNameFromJWT(accessToken);
    }

    public static String getCachedRefreshToken() {
        return localStorage.getItem(REFRESH_TOKEN_KEY);
    }

    public static String getCachedAccessToken() {
        return localStorage.getItem(ACCESS_TOKEN_KEY);
    }

    public static boolean currentUserNameKnown() {
        return currentUserName != null;
    }

    public static String getCurrentUserName() {
        if (currentUserName == null)
            throw new RuntimeException("Current user name required, but has not been set");
        return currentUserName;
    }
}
