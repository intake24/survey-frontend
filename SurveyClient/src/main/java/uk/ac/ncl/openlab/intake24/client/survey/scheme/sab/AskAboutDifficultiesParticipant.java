package uk.ac.ncl.openlab.intake24.client.survey.scheme.sab;

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

public class AskAboutDifficultiesParticipant implements PromptRule<Survey, SurveyOperation> {

    private static final String DIFFICULTIES_PARTICIPANT_KEY = "diffParticipant";
    private static final String YES_VALUE = "Yes";
    private static final String NO_VALUE = "No";

    final private PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("No", NO_VALUE))
            .plus(new MultipleChoiceQuestionOption("Yes (please specify):", YES_VALUE, true));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.customData.containsKey(DIFFICULTIES_PARTICIPANT_KEY) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(
                    "<p>Did you have any difficulties in telling me about the foods or drinks " +
                            "that you consumed yesterday?</p>"
            );

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskAboutDifficultiesParticipant.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), "diffParticipantOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(s -> s.withData(DIFFICULTIES_PARTICIPANT_KEY, answer.getValue()))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutDifficultiesParticipant(), priority);
    }
}

