package uk.ac.ncl.openlab.intake24.client.survey;

import org.pcollections.PVector;

public class SameAsBefore {
    public final EncodedFood mainFood;
    public final PVector<FoodEntry> linkedFoods;

    public SameAsBefore(EncodedFood mainFood, PVector<FoodEntry> linkedFoods) {
        this.mainFood = mainFood;
        this.linkedFoods = linkedFoods;
    }
}
