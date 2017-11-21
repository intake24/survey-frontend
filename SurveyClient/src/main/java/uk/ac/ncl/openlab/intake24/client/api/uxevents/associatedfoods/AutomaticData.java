package uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods;

import uk.ac.ncl.openlab.intake24.client.api.foods.CategoryHeader;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;

import java.util.List;

public class AutomaticData {

    public List<FoodHeader> foods;
    public List<CategoryHeader> suggestedCategories;
    public List<String> selectedCategories;

    @Deprecated
    public AutomaticData() {
    }

    public AutomaticData(List<FoodHeader> foods, List<CategoryHeader> suggestedCategories, List<String> selectedCategories) {
        this.foods = foods;
        this.suggestedCategories = suggestedCategories;
        this.selectedCategories = selectedCategories;
    }
}
