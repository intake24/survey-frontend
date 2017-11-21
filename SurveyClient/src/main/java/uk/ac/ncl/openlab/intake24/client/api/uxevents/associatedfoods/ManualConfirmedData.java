package uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods;

import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;
import uk.ac.ncl.openlab.intake24.client.survey.AssociatedFood;

public class ManualConfirmedData {
    public FoodHeader food;
    public AssociatedFood prompt;
    public FoodHeader selectedFood;

    @Deprecated
    public ManualConfirmedData() {
    }

    public ManualConfirmedData(FoodHeader food, AssociatedFood prompt, FoodHeader selectedFood) {
        this.food = food;
        this.prompt = prompt;
        this.selectedFood = selectedFood;
    }
}
