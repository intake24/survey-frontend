package uk.ac.ncl.openlab.intake24.client.api.uxevents;


import java.util.Arrays;

public class SearchButtonClicked extends AbstractUxEvent<SearchButtonData> {

    @Deprecated
    SearchButtonClicked() {
    }

    SearchButtonClicked(String query) {
        super(Arrays.asList(UxEventsHelper.SEARCH_CATEGORY), new SearchButtonData(query));
    }

}
