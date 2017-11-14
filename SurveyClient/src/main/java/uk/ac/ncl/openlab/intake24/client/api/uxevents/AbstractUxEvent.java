package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import uk.ac.ncl.openlab.intake24.client.survey.UUID;

import java.util.Date;
import java.util.List;

public abstract class AbstractUxEvent<T> {

    protected String eventType;
    protected List<String> eventCategories;

    protected String sessionId;
    protected long localTime;

    protected T data;

    @Deprecated
    public AbstractUxEvent() {
    }

    public AbstractUxEvent(List<String> categories, T data) {
        this.sessionId = UxEventsHelper.sessionId.toString();
        this.eventType = this.getClass().getSimpleName();
        this.eventCategories = categories;
        this.data = data;
        this.localTime = new Date().getTime();
    }
}
