package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;

public class InfrequentFoodAnyFish extends InfrequentFood {

    private static final String INFREQUENT_FOOD_KEY = "infrequentFood_anyFish";

    public InfrequentFoodAnyFish(String promptText, String key, UserData userData) {
        super(promptText, key, userData);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, UserData userData) {
        return new WithPriority<>(new InfrequentFoodAnyFish(
                "<p>In the last month, on how many days did you eat <strong>any type of fish</strong>?</p>" +
                        "<p>This includes white fish such as cod, haddock, plaice, fresh or canned tuna and any " +
                        "shellfish such as prawns or mussels. Include any oily fish that you might have mentioned " +
                        "in the question before. Include stews, pies and other dishes containing fish.</p>" +
                        "<p>If you did not eat in last month please enter 0.</p>",
                INFREQUENT_FOOD_KEY, userData), priority);
    }
}
