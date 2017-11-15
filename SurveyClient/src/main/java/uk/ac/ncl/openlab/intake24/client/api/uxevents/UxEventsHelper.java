package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import uk.ac.ncl.openlab.intake24.client.BrowserConsole;
import uk.ac.ncl.openlab.intake24.client.survey.UUID;

import java.util.Arrays;

public class UxEventsHelper {
    private static final UxEventsService service = GWT.create(UxEventsService.class);

    public static final UUID sessionId = UUID.randomUUID();
    public static final String SEARCH_CATEGORY = "search";

    private static final MethodCallback<Void> uxEventCallback = new MethodCallback<Void>() {
        @Override
        public void onFailure(Method method, Throwable exception) {
            BrowserConsole.error("Failed to log UX event: " + exception.getClass().getName());
        }

        @Override
        public void onSuccess(Method method, Void response) {

        }
    };

    public static void postSearchResultsReceived(SearchResult result) {
        service.postSearchResultsReceived(new UxEvent<SearchResult>("SearchResultsReceived", Arrays.asList(SEARCH_CATEGORY), result), uxEventCallback);
    }

    public static void postSearchButtonClicked(SearchButtonData data) {
        service.postSearchButtonClicked(new UxEvent<SearchButtonData>("SearchButtonClicked", Arrays.asList(SEARCH_CATEGORY), data), uxEventCallback);
    }

    public static void postCantFindButtonClicked() {
        service.postCantFindButtonClicked(new UxEvent<NoData>("CantFindButtonClicked", Arrays.asList(SEARCH_CATEGORY), new NoData()), uxEventCallback);
    }

    public static void postBrowseAllFoodsButtonClicked() {
        service.postBrowseAllFoodsButtonClicked(new UxEvent<NoData>("BrowseAllFoodsButtonClicked", Arrays.asList(SEARCH_CATEGORY), new NoData()), uxEventCallback);
    }

    public static void postSearchResultSelected(SearchResultSelectionData data) {
        service.postSearchResultSelected(new UxEvent<SearchResultSelectionData>("SearchResultSelected", Arrays.asList(SEARCH_CATEGORY), data), uxEventCallback);
    }
}
