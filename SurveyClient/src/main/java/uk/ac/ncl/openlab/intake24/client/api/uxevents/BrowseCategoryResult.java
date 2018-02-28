package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import uk.ac.ncl.openlab.intake24.client.api.foods.LookupResult;

import java.util.List;

public class BrowseCategoryResult {

    protected String categoryCode;
    protected String algorithmId;
    protected List<String> existing;

    protected LookupResult result;

    @Deprecated
    public BrowseCategoryResult() {
    }

    public BrowseCategoryResult(String categoryCode, List<String> existing, String algorithmId, LookupResult result) {
        this.categoryCode = categoryCode;
        this.existing = existing;
        this.algorithmId = algorithmId;
        this.result = result;
    }
}
