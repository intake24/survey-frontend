package uk.ac.ncl.openlab.intake24.client.api.survey;

public class UxEventsSettings {

    public static final UxEventsSettings DEFAULT = new UxEventsSettings(false, false);

    @Deprecated
    public UxEventsSettings() {
    }

    public UxEventsSettings(boolean enableSearchEvents, boolean enableAssociatedFoodsEvents) {
        this.enableSearchEvents = enableSearchEvents;
        this.enableAssociatedFoodsEvents = enableAssociatedFoodsEvents;
    }

    public boolean enableSearchEvents;
    public boolean enableAssociatedFoodsEvents;
}
