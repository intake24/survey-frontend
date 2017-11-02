package uk.ac.ncl.openlab.intake24.client.api.foods;

import java.util.List;

public class AutomaticAssociatedFoods {
    public List<CategoryHeader> categories;

    @Deprecated
    public AutomaticAssociatedFoods() {
    }

    public AutomaticAssociatedFoods(List<CategoryHeader> categories) {
        this.categories = categories;
    }
}
