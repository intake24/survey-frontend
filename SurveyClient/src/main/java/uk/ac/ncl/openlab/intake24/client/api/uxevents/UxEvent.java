package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import java.util.Date;
import java.util.List;

public class UxEvent<T> {

    protected String eventType;
    protected List<String> eventCategories;

    protected String sessionId;
    protected long localTime;

    protected T data;

    @Deprecated
    public UxEvent() {
    }

    public UxEvent(String eventType, List<String> categories, T data) {
        this.sessionId = UxEventsHelper.sessionId.toString();
        this.eventType = eventType;
        this.eventCategories = categories;
        this.data = data;
        this.localTime = new Date().getTime();
    }
}
