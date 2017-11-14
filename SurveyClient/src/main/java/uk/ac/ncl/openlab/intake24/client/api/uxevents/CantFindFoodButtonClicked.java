package uk.ac.ncl.openlab.intake24.client.api.uxevents;


import java.util.Arrays;

public class CantFindFoodButtonClicked extends AbstractUxEvent<SearchButtonData> {

    @Deprecated
    CantFindFoodButtonClicked() {
    }

    CantFindFoodButtonClicked(String query) {
        super(Arrays.asList(UxEventsHelper.SEARCH_CATEGORY), new SearchButtonData(query));
    }
}