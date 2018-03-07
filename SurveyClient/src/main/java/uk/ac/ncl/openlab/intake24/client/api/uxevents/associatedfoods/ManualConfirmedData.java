package uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods;

import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;
import uk.ac.ncl.openlab.intake24.client.survey.AssociatedFood;

import java.util.List;

public class ManualConfirmedData {
    public FoodHeader food;
    public List<FoodHeader> givenFoods;
    public AssociatedFood prompt;
    public FoodHeader selectedFood;

    @Deprecated
    public ManualConfirmedData() {
    }

    public ManualConfirmedData(FoodHeader food, List<FoodHeader> givenFoods,
                               AssociatedFood prompt, FoodHeader selectedFood) {
        this.food = food;
        this.givenFoods = givenFoods;
        this.prompt = prompt;
        this.selectedFood = selectedFood;
    }
}
