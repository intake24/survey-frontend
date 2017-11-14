package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;

public class SearchResultSelectionData {

    public Viewport viewport;
    public Option<ContainerPosition> foodsContainer;
    public Option<ContainerPosition> categoriesContainer;
    public ContainerPosition buttonsContainer;
    public FoodHeader selectedFood;
    public int selectedIndex;

    @Deprecated
    public SearchResultSelectionData() {
    }

    public SearchResultSelectionData(Viewport viewport,
                                     Option<ContainerPosition> foodsContainer,
                                     Option<ContainerPosition> categoriesContainer,
                                     ContainerPosition buttonsContainer,
                                     FoodHeader selectedFood,
                                     int selectedIndex) {
        this.viewport = viewport;
        this.foodsContainer = foodsContainer;
        this.categoriesContainer = categoriesContainer;
        this.buttonsContainer = buttonsContainer;
        this.selectedFood = selectedFood;
        this.selectedIndex = selectedIndex;
    }
}
