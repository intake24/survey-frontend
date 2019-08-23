package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public class AskIfUsualAmount implements PromptRule<Survey, SurveyOperation> {

    public static final String AMOUNT_KEY = "foodAmount";
    public static final String AMOUNT_REASON_KEY = "foodAmountReason";
    public static final String USUAL_VALUE = "usual";
    public static final String LESS_VALUE = "less";
    public static final String MORE_VALUE = "more";


    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("The usual amount", USUAL_VALUE))
                    .plus(new MultipleChoiceQuestionOption("Less than usual (please specify why):", LESS_VALUE, true))
                    .plus(new MultipleChoiceQuestionOption("More than usual (please specify why):", MORE_VALUE, true));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.customData.containsKey(AMOUNT_KEY) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Was the amount of food and drink you had yesterday:</p>");

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskIfUsualAmount.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "usualAmountOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(s -> answer.details.match(
                            reason -> s.withData(AMOUNT_KEY, answer.value).withData(AMOUNT_REASON_KEY, reason),
                            () -> s.withData(AMOUNT_KEY, answer.value)))));

        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskIfUsualAmount(), priority);
    }
}
