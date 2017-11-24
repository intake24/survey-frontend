package uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods;

import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;
import uk.ac.ncl.openlab.intake24.client.survey.AssociatedFood;

public class ManualAlreadyReportedData {
    public FoodHeader food;
    public AssociatedFood prompt;

    @Deprecated
    public ManualAlreadyReportedData() {
    }

    public ManualAlreadyReportedData(FoodHeader food, AssociatedFood prompt) {
        this.food = food;
        this.prompt = prompt;
    }
}
