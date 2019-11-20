package uk.ac.ncl.openlab.intake24.client.survey.scheme.sab;

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
                "<p>Thinking about the food and drink you had yesterday, I would like to check that you didn’t " +
                        "forget anything. In addition to what you have already told me about, did you have any:</p>" +
                        "<p><ul>" +
                        "<li>Tea, coffee, herbal tea</li>" +
                        "<li>Soft drinks, milkshake, or juice</li>" +
                        "<li>Water, including tap, filtered, fountain, bottled</li>" +
                        "<li>Alcoholic drinks</li>" +
                        "<li>Milk, yoghurt, cheese, curd</li>" +
                        "<li>Savoury snacks, poppadum, crisps, sev, fritters, samosa, nuts</li>" +
                        "<li>Sweet snacks, sweet biscuits, sweets, chocolate, cakes, desserts, pastries, halwa, " +
                        "sweet flour or bean snacks</li>" +
                        "<li>Bread, flatbread, paratha, roti, chapatti, roll</li>" +
                        "<li>Sauces, dressings, chutneys, pickles, raita</li>" +
                        "<li>Paan, sauf, or other mouth fresheners</li>" +
                        "</ul></p>" +
                        "<p>Interviewer – you can either select a meal from the menu on the left to add foods " +
                        "or drinks or click the “Add meal” button to add another meal.</p>"), "foodsReminderShown"), priority);
    }
}
