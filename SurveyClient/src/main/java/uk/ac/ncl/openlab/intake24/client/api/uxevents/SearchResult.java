package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import uk.ac.ncl.openlab.intake24.client.api.foods.LookupResult;

public class SearchResult {

    protected String query;
    protected String algorithmId;

    protected LookupResult result;

    @Deprecated
    public SearchResult() { }

    public SearchResult(String query, String algorithmId, LookupResult result) {
        this.query = query;
        this.algorithmId = algorithmId;
        this.result = result;
    }
}
