package uk.ac.ncl.openlab.intake24.client.survey.scheme.bristol;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public class ReadyToEat implements PromptRule<Meal, MealOperation> {

    private static final String READY_TO_EAT_KEY = "readyToEat";

    private static final String promptTemplate = "<p>Was most of $meal ‘ready-to-eat’ when you got it?</p>" +
            "<p><em>Ready-to-eat means <strong>items from outside the home that came already prepared</strong>, " +
            "with no further cooking, heating or preparation required before being eaten or drank.</em></p>";

    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("Yes"))
                    .plus(new MultipleChoiceQuestionOption("No"))
                    .plus(new MultipleChoiceQuestionOption("Don’t know"));

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(READY_TO_EAT_KEY) && !state.isEmpty() && state.portionSizeComplete()) {
            SafeHtml promptSafeText = SafeHtmlUtils.fromSafeConstant(promptTemplate.replace("$meal", SafeHtmlUtils.htmlEscape(state.name.toLowerCase())));

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptSafeText, ReadyToEat.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "readyToEat");

            return Option.some(PromptUtil.asMealPrompt(prompt, answer ->
                    MealOperation.setCustomDataField(READY_TO_EAT_KEY, answer.getValue())));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new ReadyToEat(), priority);
    }
}
