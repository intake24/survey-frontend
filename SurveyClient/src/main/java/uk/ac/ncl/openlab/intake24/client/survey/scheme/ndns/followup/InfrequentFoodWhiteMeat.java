package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;

public class InfrequentFoodWhiteMeat extends InfrequentFood {

    private static final String INFREQUENT_FOOD_KEY = "infrequentFood_whiteMeat";

    public InfrequentFoodWhiteMeat(String promptText, String key, UserData userData) {
        super(promptText, key, userData);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, UserData userData) {
        return new WithPriority<>(new InfrequentFoodWhiteMeat(
                "<p>In the last month, on how many days did you eat <strong>white meat</strong> such as " +
                        "chicken and turkey? Include stews, pies and other dishes containing white meat.</p>" +
                        "<p>If you did not eat in last month please enter 0.</p>",
                INFREQUENT_FOOD_KEY, userData), priority);
    }
}
