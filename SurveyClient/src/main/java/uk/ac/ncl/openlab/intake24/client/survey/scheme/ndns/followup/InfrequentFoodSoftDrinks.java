package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;

public class InfrequentFoodSoftDrinks extends InfrequentFood {

    private static final String INFREQUENT_FOOD_KEY = "infrequentFood_softDrinks";

    public InfrequentFoodSoftDrinks(String promptText, String key, UserData userData) {
        super(promptText, key, userData);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, UserData userData) {
        return new WithPriority<>(new InfrequentFoodSoftDrinks(
                "<p>In the last month on how many days did you drink <strong>sugar sweetened soft drinks</strong>" +
                        " such as squashes, cordials, energy drinks, cola, lemonade?</p>" +
                        "<p>Do NOT include low calorie/sugar free/no added sugar varieties.</p>" +
                        "<p>If you did not eat in last month please enter 0.</p>",
                INFREQUENT_FOOD_KEY, userData), priority);
    }
}
