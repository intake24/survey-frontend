package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.GenericReminder;

public class RemindFrequentlyForgottenFoods extends GenericReminder {

    public RemindFrequentlyForgottenFoods(SafeHtml promptText, String key) {
        super(promptText, key);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new RemindFrequentlyForgottenFoods(SafeHtmlUtils.fromSafeConstant(
                "<p>Thinking about all the food and drink you had yesterday, " +
                        "we want to check that you didn’t forget anything. Below is a list of some commonly forgotten foods. " +
                        "If you think you’ve forgotten anything please go back and enter these now.</p>" +
                        "<p>You can either select a meal from the menu on the left to add foods or drink or click " +
                        "the \"Add meal\" button to add another meal.</p>" +
                        "<p><ul>" +
                        "<li>Coffee, tea</li>" +
                        "<li>Soft drinks</li>" +
                        "<li>Water (including tap, bottled and drinking fountain)</li>" +
                        "<li>Alcoholic drinks</li>" +
                        "<li>Milk</li>" +
                        "<li>Biscuits, cakes, sweets, chocolate, other confectionery</li>" +
                        "<li>Crisps, nuts or other savoury snacks</li>" +
                        "<li>Sauces, dressings</li>" +
                        "<li>Bread</li>" +
                        "<li>Cheese</li>" +
                        "</ul></p>"), "foodsReminderShown"), priority);
    }
}
