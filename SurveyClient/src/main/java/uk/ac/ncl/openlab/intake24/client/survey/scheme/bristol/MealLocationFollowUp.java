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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckListPrompt;

public class MealLocationFollowUp implements PromptRule<Meal, MealOperation> {

    private static final String MEAL_LOCATION_FOLLOW_UP_COMPLETE = "mealLocationFollowUpComplete";
    private static final String MEAL_LOCATION_FOLLOW_UP_KEY = "mealLocationFollowUp";

    private static final String promptTemplate = "<p>Where were you when you had your $meal?</p>";

    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("At home"))
                    .plus(new MultipleChoiceQuestionOption("At work"))
                    .plus(new MultipleChoiceQuestionOption("At a friend / family’s home"))
                    .plus(new MultipleChoiceQuestionOption("At a restaurant / coffee shop / café / fast food venue"))
                    .plus(new MultipleChoiceQuestionOption("Outside / while travelling"))
                    .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true));

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(MEAL_LOCATION_FOLLOW_UP_COMPLETE) &&
                state.customData.containsKey(MealLocation.MEAL_LOCATION_KEY) &&
                !state.customData.get(MealLocation.MEAL_LOCATION_KEY).equals("Yes") &&
                !state.isEmpty() && state.portionSizeComplete()) {
            SafeHtml promptSafeText = SafeHtmlUtils.fromSafeConstant(promptTemplate.replace("$meal", SafeHtmlUtils.htmlEscape(state.name.toLowerCase())));

            CheckListPrompt prompt = new CheckListPrompt(promptSafeText, MealLocationFollowUp.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel());

            return Option.some(PromptUtil.asMealPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String mealLocationFollowUpValue = answers.stream()
                            .map(answer -> answer.getValue())
                            .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);

                    return MealOperation.update(meal -> meal.withCustomDataField(MEAL_LOCATION_FOLLOW_UP_KEY, mealLocationFollowUpValue).withFlag(MEAL_LOCATION_FOLLOW_UP_COMPLETE));
                } else
                    return MealOperation.update(meal -> meal.withFlag(MEAL_LOCATION_FOLLOW_UP_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new MealLocationFollowUp(), priority);
    }
}
