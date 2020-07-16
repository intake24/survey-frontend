package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckListPrompt;

public class AskAboutSelfIsolation implements PromptRule<Survey, SurveyOperation> {

    private static final String SELFISOLATION_KEY = "selfIsolation";
    private static final String SELFISOLATION_COMPLETE = "selfIsolationComplete";

    final private PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("Self isolating at home due to you or a member of your household having COVID-19 symptoms ", "COVID-19 symptoms"))
            .plus(new MultipleChoiceQuestionOption("Self isolating at home due to you or a member of your household having a positive or pending coronavirus test result", "positive/pending COVID-19 test"))
            .plus(new MultipleChoiceQuestionOption("Shielding or isolating due to vulnerability such as age or health conditions ", "COVID-19 vulnerability"));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(SELFISOLATION_COMPLETE) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Do any of the following currently apply to you (tick all that apply):<p>");

            CheckListPrompt prompt = new CheckListPrompt(promptText, AskAboutSelfIsolation.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), false);

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String isolationValue = answers.stream().map(answer -> answer.getValue())
                            .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);
                    return SurveyOperation.update(survey -> survey.withData(SELFISOLATION_KEY, isolationValue).withFlag(SELFISOLATION_COMPLETE));
                } else
                    return SurveyOperation.update(survey -> survey.withFlag(SELFISOLATION_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutSelfIsolation(), priority);
    }
}

