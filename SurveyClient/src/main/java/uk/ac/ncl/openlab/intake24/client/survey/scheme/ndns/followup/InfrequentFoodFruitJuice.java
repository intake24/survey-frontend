package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;

public class InfrequentFoodFruitJuice extends InfrequentFood {

    private static final String INFREQUENT_FOOD_KEY = "infrequentFood_fruitJuice";

    public InfrequentFoodFruitJuice(String promptText, String key, UserData userData) {
        super(promptText, key, userData);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, UserData userData) {
        return new WithPriority<>(new InfrequentFoodFruitJuice(
                "<p>In the last month, on how many days did you drink <strong>100% fruit juice</strong> " +
                        "such as apple or orange juice?</p>" +
                        "<p>Do NOT include juice drinks with other ingredients such as water, sugar or sweetener " +
                        "e.g. Fruit Shoot, Capri Sun, J20.</p>" +
                        "<p>If you did not eat in last month please enter 0.</p>",
                INFREQUENT_FOOD_KEY, userData), priority);
    }
}
