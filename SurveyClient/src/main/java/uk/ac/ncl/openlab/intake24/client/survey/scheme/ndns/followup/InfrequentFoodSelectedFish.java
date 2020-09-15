package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.*;

public class InfrequentFoodSelectedFish extends InfrequentFood {

    private static final String INFREQUENT_FOOD_KEY = "infrequentFood_selectedFish";

    public InfrequentFoodSelectedFish(String promptText, String key, UserData userData) {
        super(promptText, key, userData);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, UserData userData) {
        return new WithPriority<>(new InfrequentFoodSelectedFish(
                "<p>In the last month, on how many days did you eat " +
                        "<strong>fresh, tinned or frozen oily fish</strong> " +
                        "such as salmon, sardines, mackerel, kippers, anchovies, pilchards, trout?</p>" +
                        "<p>Include oily fish eaten in stews, pies and other dishes. Do NOT include tuna.</p>" +
                        "<p>If you did not eat in last month please enter 0.</p>",
                INFREQUENT_FOOD_KEY, userData), priority);
    }
}
