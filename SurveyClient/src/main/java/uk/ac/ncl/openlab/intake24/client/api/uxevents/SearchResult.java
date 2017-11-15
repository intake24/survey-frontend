package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import uk.ac.ncl.openlab.intake24.client.api.foods.LookupResult;

import java.util.List;

public class SearchResult {

    protected String query;
    protected String algorithmId;
    protected List<String> existing;

    protected LookupResult result;

    @Deprecated
    public SearchResult() {
    }

    public SearchResult(String query, List<String> existing, String algorithmId, LookupResult result) {
        this.query = query;
        this.existing = existing;
        this.algorithmId = algorithmId;
        this.result = result;
    }
}
