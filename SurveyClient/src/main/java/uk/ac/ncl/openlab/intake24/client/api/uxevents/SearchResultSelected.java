package uk.ac.ncl.openlab.intake24.client.api.uxevents;


import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;

import java.util.Arrays;

public class SearchResultSelected extends AbstractUxEvent<SearchResultSelectionData> {

    @Deprecated
    SearchResultSelected() {
    }

    SearchResultSelected(Viewport viewport,
                         Option<ContainerPosition> foodsContainer,
                         Option<ContainerPosition> categoriesContainer,
                         ContainerPosition buttonsContainer,
                         FoodHeader selectedFood,
                         int selectedIndex) {
        super(Arrays.asList(UxEventsHelper.SEARCH_CATEGORY), new SearchResultSelectionData(viewport, foodsContainer,
                categoriesContainer, buttonsContainer, selectedFood, selectedIndex));
    }
}
