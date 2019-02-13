package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckBoxPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.MultipleChoiceCheckboxQuestion;

public class AskIfUsualAmount implements PromptRule<Survey, SurveyOperation> {

    public static final String AMOUNT_KEY = "amount";
    public static final String AMOUNT_REASON_KEY = "amountReason";
    public static final String USUAL = "usual";
    public static final String LESS = "less";
    public static final String MORE = "more";

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.customData.containsKey(AMOUNT_KEY) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Was the amount of food and drink you had today:</p>");

            new MultipleChoiceCheckboxQuestion(survey, SafeHtmlUtils
                    .fromSafeConstant("<p>Do you take any dietary supplements e.g. Multivitamins?</p>"), "Continue", supplementOptions,
                    "supplements", Option.some("Other"));

            CheckBoxPrompt prompt = new CheckBoxPrompt(promptText, AskIfUsualAmount.class.getSimpleName(),
                    supplementOptions, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "cookedAtHomeOption", Option.some("Other (please specify)"));

            return Option.some(PromptUtil.asSurveyPrompt(prompt, supplements -> SurveyOperation.update(survey -> survey.withData(SUPPLEMENTS_KEY, concat(supplements)))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskIfUsualAmount(), priority);
    }
}
