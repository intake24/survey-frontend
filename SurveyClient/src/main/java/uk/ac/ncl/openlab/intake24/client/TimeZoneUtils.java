package uk.ac.ncl.openlab.intake24.client;

public class TimeZoneUtils {
    public static native String getTimeZone() /*-{
        if (Intl) {
           var timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
           if (timeZone)
             return timeZone;
        };

        return 'Europe/London';  // workaround for old browsers
    }-*/;
}
