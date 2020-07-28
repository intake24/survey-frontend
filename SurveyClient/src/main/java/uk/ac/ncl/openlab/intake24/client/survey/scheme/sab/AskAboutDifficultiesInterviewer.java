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

public class AskAboutDifficultiesInterviewer implements PromptRule<Survey, SurveyOperation> {

    private static final String DIFFICULTIES_INTERVIEWER_KEY = "diffInterviewer";
    private static final String YES_VALUE = "Yes";
    private static final String NO_VALUE = "No";

    final private PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("No", NO_VALUE))
            .plus(new MultipleChoiceQuestionOption("Yes (please specify):", YES_VALUE, true));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey survey, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!survey.customData.containsKey(DIFFICULTIES_INTERVIEWER_KEY) && survey.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(
                    "<p><strong>For interviewer</strong></p>" +
                            "<p>Did you have any difficulties in recording the foods or drinks consumed " +
                            "by this participant?</p>" +
                            "<p>For example, participant did not cook or prepare food themselves; " +
                            "participant was away from home; participant had difficulties remembering; foods " +
                            "not found on food list, etc. Please provide as much information as possible.</p>"
            );

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskAboutDifficultiesInterviewer.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), "diffInterviewerOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(s -> s.withData(DIFFICULTIES_INTERVIEWER_KEY, answer.getValue()))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutDifficultiesInterviewer(), priority);
    }
}

