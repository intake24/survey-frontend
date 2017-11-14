package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.BrowserConsole;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;
import uk.ac.ncl.openlab.intake24.client.api.foods.LookupResult;
import uk.ac.ncl.openlab.intake24.client.survey.UUID;

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

    public static void postTestEvent(TestEvent event) {
        service.postTestEvent(event, uxEventCallback);
    }

    public static void postSearchResultsReceived(String query, String algorithmId, LookupResult result) {
        service.postSearchResultsReceived(new SearchResultsReceived(query, algorithmId, result), uxEventCallback);
    }

    public static void postSearchButtonClicked(String query) {
        service.postSearchButtonClicked(new SearchButtonClicked(query), uxEventCallback);
    }

    public static void postCantFindButtonClicked(String query) {
        service.postCantFindButtonClicked(new CantFindFoodButtonClicked(query), uxEventCallback);
    }

    public static void postSearchResultSelected(Viewport viewport,
                                                Option<ContainerPosition> foodsContainer,
                                                Option<ContainerPosition> categoriesContainer,
                                                ContainerPosition buttonsContainer,
                                                FoodHeader selectedFood,
                                                int selectedIndex) {
        service.postSearchResultSelected(new SearchResultSelected(viewport, foodsContainer, categoriesContainer,
                buttonsContainer, selectedFood, selectedIndex), uxEventCallback);
    }
}
