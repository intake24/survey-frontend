package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.storage.client.Storage;

public class DynamicRedirect {
    final static String prefix = "redirect-";

    public static void set(Long userId, String url) {
        Storage localStorage = Storage.getLocalStorageIfSupported();
        localStorage.setItem(prefix + userId, url);
    }

    public static String get(Long userId) {
        Storage localStorage = Storage.getLocalStorageIfSupported();
        return localStorage.getItem(prefix + userId);
    }

    public static void clear(Long userId) {
        Storage.getLocalStorageIfSupported().removeItem(prefix + userId);
    }
}
