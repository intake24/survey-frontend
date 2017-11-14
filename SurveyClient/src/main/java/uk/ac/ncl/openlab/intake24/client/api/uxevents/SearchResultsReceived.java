package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import uk.ac.ncl.openlab.intake24.client.api.foods.LookupResult;

import java.util.Arrays;

public class SearchResultsReceived extends AbstractUxEvent<SearchResult> {

    @Deprecated
    SearchResultsReceived() {
    }

    SearchResultsReceived(String query, String algorithmId, LookupResult result) {
        super(Arrays.asList(UxEventsHelper.SEARCH_CATEGORY), new SearchResult(query, algorithmId, result));
    }

}
