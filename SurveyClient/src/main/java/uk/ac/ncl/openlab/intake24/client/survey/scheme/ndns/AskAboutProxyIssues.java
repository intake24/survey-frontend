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

public class AskAboutProxyIssues implements PromptRule<Survey, SurveyOperation> {

    private static final String PROXY_KEY_ISSUES = "proxyIssues";

    final private PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("No", AskAboutProxy.PROXY_NO_VALUE))
            .plus(new MultipleChoiceQuestionOption("Yes (please specify):", AskAboutProxy.PROXY_YES_VALUE, true));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.flags.contains(AskAboutProxy.PROXY_COMPLETE) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(
                    "<p>Did you have any difficulties in recording foods " +
                            "or amounts eaten because you were completing this for someone else?</p>" +
                            "<p><small>For example, occasions when your child was not in your care and " +
                            "you are unsure of the details of a particular food or an entire meal.</small></p>"
            );

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskAboutProxyIssues.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), "proxyOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(s ->
                            s.withData(PROXY_KEY_ISSUES, answer.getValue()).withFlag(AskAboutProxy.PROXY_COMPLETE))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutProxyIssues(), priority);
    }
}

