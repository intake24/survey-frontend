package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.GenericReminder;

public class RemindSupplements extends GenericReminder {

    public RemindSupplements(SafeHtml promptText, String key) {
        super(promptText, key);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new RemindFrequentlyForgottenFoods(SafeHtmlUtils.fromSafeConstant(
                "<p>Did you take any dietary supplements (e.g. vitamins)?</p>" +
                        "<p>If yes, please go back by selecting  <strong>'+ Add another meal'</strong>, " +
                        "search and enter your supplement.</p>" +
                        "<p>If no, please press continue.</p>"),
                "supplementsReminderShown"), priority);
    }
}
