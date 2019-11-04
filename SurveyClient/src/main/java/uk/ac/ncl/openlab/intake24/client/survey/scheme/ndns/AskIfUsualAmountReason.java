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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckListPrompt;

import java.util.ArrayList;

public class AskIfUsualAmountReason implements PromptRule<Survey, SurveyOperation> {

    private final String AMOUNT_REASON_KEY = "foodAmountReason";

    final private PVector<MultipleChoiceQuestionOption> moreOptions = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("Travelling / out of house / visiting friends"))
            .plus(new MultipleChoiceQuestionOption("Just got some money"))
            .plus(new MultipleChoiceQuestionOption("At a special occasion/socialising"))
            .plus(new MultipleChoiceQuestionOption("On a special day"))
            .plus(new MultipleChoiceQuestionOption("Weekend day / Friday"))
            .plus(new MultipleChoiceQuestionOption("Very hungry / thirsty"))
            .plus(new MultipleChoiceQuestionOption("Bored / stressed"))
            .plus(new MultipleChoiceQuestionOption("Working shifts"))
            .plus(new MultipleChoiceQuestionOption("Change of routine"))
            .plus(new MultipleChoiceQuestionOption("Busy/active"))
            .plus(new MultipleChoiceQuestionOption("Don’t know"))
            .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true));

    final private PVector<MultipleChoiceQuestionOption> lessOptions = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("Sickness/tiredness"))
            .plus(new MultipleChoiceQuestionOption("Short of money"))
            .plus(new MultipleChoiceQuestionOption("Little food / drink in the house"))
            .plus(new MultipleChoiceQuestionOption("Travelling/out of house / visiting friends"))
            .plus(new MultipleChoiceQuestionOption("At a special occasion/socialising"))
            .plus(new MultipleChoiceQuestionOption("On a special day"))
            .plus(new MultipleChoiceQuestionOption("Weekend day / Friday"))
            .plus(new MultipleChoiceQuestionOption("Too busy / lack of time"))
            .plus(new MultipleChoiceQuestionOption("Dieting"))
            .plus(new MultipleChoiceQuestionOption("Fasting"))
            .plus(new MultipleChoiceQuestionOption("Bored / stressed"))
            .plus(new MultipleChoiceQuestionOption("Change of routine"))
            .plus(new MultipleChoiceQuestionOption("Don’t know"))
            .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true));

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        ArrayList<String> keys = new ArrayList<>();
        keys.add(AskIfUsualAmount.MORE_VALUE);
        keys.add(AskIfUsualAmount.LESS_VALUE);
        String amount = state.customData.get(AskIfUsualAmount.AMOUNT_KEY);

        if (!state.flags.contains(AskIfUsualAmount.AMOUNT_COMPLETE) && keys.contains(amount) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(
                    amount.equals(AskIfUsualAmount.MORE_VALUE) ?
                            "<p>What is the reason you had more than usual? Please tick all that apply.</p>" :
                            "<p>What is the reason you had less than usual? Please tick all that apply.</p>");

            CheckListPrompt prompt = new CheckListPrompt(promptText, AskIfUsualAmountReason.class.getSimpleName(),
                    amount.equals(AskIfUsualAmount.MORE_VALUE) ? moreOptions : lessOptions,
                    PromptMessages.INSTANCE.mealComplete_continueButtonLabel());

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String reasonValue = answers.stream().map(answer ->
                            answer.getValue()).reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);
                    return SurveyOperation.update(survey ->
                            survey.withData(AMOUNT_REASON_KEY, reasonValue).withFlag(AskIfUsualAmount.AMOUNT_COMPLETE));
                } else
                    return SurveyOperation.update(survey -> survey.withFlag(AskIfUsualAmount.AMOUNT_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskIfUsualAmountReason(), priority);
    }
}


