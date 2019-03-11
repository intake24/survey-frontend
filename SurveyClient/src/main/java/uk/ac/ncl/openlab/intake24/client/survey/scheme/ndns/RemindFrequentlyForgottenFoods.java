package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.ReminderPrompt;

public class RemindFrequentlyForgottenFoods implements PromptRule<Survey, SurveyOperation> {

    public static final String REMINDER_KEY = "foodsReminderShown";

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(REMINDER_KEY) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Thinking about all the food and drink you had yesterday, " +
                    "we want to check that you didn’t forget anything. Below is a list of some commonly forgotten foods. " +
                    "If you think you’ve forgotten anything please go back and enter these now.</p>" +
                    "<p>You can either select a meal from the menu on the left to add foods or drink or click the \"Add meal\" button to " +
                    "add another meal.</p>" +
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
                    "</ul></p>");

            return Option.some(PromptUtil.asSurveyPrompt(new ReminderPrompt(promptText),
                    answers -> SurveyOperation.update(survey -> survey.withFlag(REMINDER_KEY))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new RemindFrequentlyForgottenFoods(), priority);
    }
}
