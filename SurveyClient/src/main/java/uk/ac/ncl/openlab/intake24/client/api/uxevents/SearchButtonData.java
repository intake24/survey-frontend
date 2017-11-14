package uk.ac.ncl.openlab.intake24.client.api.uxevents;

public class SearchButtonData {
    public String query;

    @Deprecated
    public SearchButtonData() {
    }

    public SearchButtonData(String query) {
        this.query = query;
    }
}
