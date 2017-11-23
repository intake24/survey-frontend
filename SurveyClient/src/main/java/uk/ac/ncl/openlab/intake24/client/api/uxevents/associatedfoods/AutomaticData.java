package uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods;

import uk.ac.ncl.openlab.intake24.client.api.foods.CategoryHeader;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.Viewport;

import java.util.List;

public class AutomaticData {

    public Viewport viewport;
    public List<FoodHeader> foods;
    public List<CategoryHeader> suggestedCategories;
    public List<String> selectedCategories;

    @Deprecated
    public AutomaticData() {
    }

    public AutomaticData(Viewport viewport, List<FoodHeader> foods, List<CategoryHeader> suggestedCategories, List<String> selectedCategories) {
        this.viewport = viewport;
        this.foods = foods;
        this.suggestedCategories = suggestedCategories;
        this.selectedCategories = selectedCategories;
    }
}
