package uk.ac.ncl.openlab.intake24.client.survey.scheme.nz;

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

    static final String AMOUNT_KEY = "foodAmount";
    static final String AMOUNT_COMPLETE = "foodAmountComplete";
    static final String USUAL_VALUE = "Usual";
    static final String LESS_VALUE = "Less";
    static final String MORE_VALUE = "More";


    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("Usual", USUAL_VALUE))
                    .plus(new MultipleChoiceQuestionOption("Less than usual", LESS_VALUE))
                    .plus(new MultipleChoiceQuestionOption("More than usual", MORE_VALUE));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.customData.containsKey(AMOUNT_KEY) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>What was the amount of food and drink you had yesterday?</p>");

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskIfUsualAmount.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "usualAmountOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(s ->
                            answer.value.equals(USUAL_VALUE) ? s.withData(AMOUNT_KEY, answer.value).withFlag(AMOUNT_COMPLETE) : s.withData(AMOUNT_KEY, answer.value)
                    )));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskIfUsualAmount(), priority);
    }
}
