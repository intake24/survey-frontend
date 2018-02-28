package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.foods.CategoryHeader;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;

public class SearchResultSelectionData {

    public Viewport viewport;
    public Option<ContainerPosition> foodsContainer;
    public Option<ContainerPosition> categoriesContainer;
    public ContainerPosition buttonsContainer;
    public Option<FoodHeader> selectedFood;
    public Option<CategoryHeader> selectedCategory;
    public int selectedIndex;

    @Deprecated
    public SearchResultSelectionData() {
    }

    public SearchResultSelectionData(Viewport viewport,
                                     Option<ContainerPosition> foodsContainer,
                                     Option<ContainerPosition> categoriesContainer,
                                     ContainerPosition buttonsContainer,
                                     Option<FoodHeader> selectedFood,
                                     Option<CategoryHeader> selectedCategory,
                                     int selectedIndex) {
        this.viewport = viewport;
        this.foodsContainer = foodsContainer;
        this.categoriesContainer = categoriesContainer;
        this.buttonsContainer = buttonsContainer;
        this.selectedFood = selectedFood;
        this.selectedCategory = selectedCategory;
        this.selectedIndex = selectedIndex;
    }
}
