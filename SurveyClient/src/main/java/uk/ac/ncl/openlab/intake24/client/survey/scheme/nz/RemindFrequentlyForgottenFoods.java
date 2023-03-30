package uk.ac.ncl.openlab.intake24.client.survey.scheme.nz;

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
                "<p>Thinking about all the food and drink you had yesterday, we want to check that you didn't " +
                        "forget anything. Below is a list of some commonly forgotten foods. If you think you've " +
                        "forgotten anything, please go back and enter these now:</p>" +
                        "<p><ul>" +
                        "<li>Coffee, tea</li>" +
                        "<li>Soft drinks, sport drinks, juice</li>" +
                        "<li>Water (including tap, bottled and drinking fountain)</li>" +
                        "<li>Alcoholic drinks</li>" +
                        "<li>Milk</li>" +
                        "<li>Fruit</li>" +
                        "<li>Biscuits, cakes, lollies, sweets, chocolate, other sweet snacks</li>" +
                        "<li>Crisps, nuts or other savoury snacks</li>" +
                        "<li>Sauces, dressings, chutneys</li>" +
                        "<li>Protein powder or shake</li>" +
                        "<li>Meal replacement products</li>" +
                        "<li>Bread, cheese</li>" +
                        "</ul></p>" +
                        "<p>If you did forget, please go back and enter these foods.</p>"), "foodsReminderShown"), priority);
    }
}
