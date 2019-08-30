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

public class AskAboutProxy implements PromptRule<Survey, SurveyOperation> {

    private static final String PROXY_KEY = "proxy";
    static final String PROXY_COMPLETE = "proxyComplete";
    static final String PROXY_YES_VALUE = "yes";
    static final String PROXY_NO_VALUE = "no";

    final private PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("No", PROXY_NO_VALUE))
            .plus(new MultipleChoiceQuestionOption("Yes", PROXY_YES_VALUE));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.customData.containsKey(PROXY_KEY) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(
                    "<p>Did you complete this recall on behalf of someone else?</p>" +
                            "<p><small>Please note that recalls should be completed independently by the individual " +
                            "named on the first screen and only completed by someone else when necessary " +
                            "i.e. for young children, if there are language difficulties or the person " +
                            "is not confident in using a computer to fill in the recall</small></p>"
            );

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskAboutProxy.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), "proxyOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(s ->
                            answer.value.equals(PROXY_YES_VALUE) ? s.withData(PROXY_KEY, answer.value) :
                                    s.withData(PROXY_KEY, answer.value).withFlag(PROXY_COMPLETE))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutProxy(), priority);
    }
}

