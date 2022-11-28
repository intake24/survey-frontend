package uk.ac.ncl.openlab.intake24.client.survey.scheme.debeat;

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

public class MealCompany implements PromptRule<Meal, MealOperation> {

    private static final String MEAL_COMPANY_COMPLETE = "mealCompanyComplete";
    private static final String MEAL_COMPANY_KEY = "mealCompany";

    private static final String promptTemplate = "<p>Who were you with while eating your $meal (tick all that apply)?</p>";

    private static final PVector<MultipleChoiceQuestionOption> options =
            TreePVector.<MultipleChoiceQuestionOption>empty()
                    .plus(new MultipleChoiceQuestionOption("by myself", "0"))
                    .plus(new MultipleChoiceQuestionOption("with parents / caregivers / other older relatives (e.g. grandparents, aunt etc.)", "1"))
                    .plus(new MultipleChoiceQuestionOption("with siblings", "2"))
                    .plus(new MultipleChoiceQuestionOption("with partner / boyfriend / girlfriend", "3"))
                    .plus(new MultipleChoiceQuestionOption("with my child / children", "4"))
                    .plus(new MultipleChoiceQuestionOption("with friends ", "5"))
                    .plus(new MultipleChoiceQuestionOption("with other students / housemates / work colleagues", "6"))
                    .plus(new MultipleChoiceQuestionOption("other (please specify):", "7", true));

    @Override
    public Option<Prompt<Meal, MealOperation>> apply(Meal state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.flags.contains(MEAL_COMPANY_COMPLETE) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(promptTemplate.replace("$meal", SafeHtmlUtils.htmlEscape(state.name.toLowerCase())));

            CheckListPrompt prompt = new CheckListPrompt(promptText, MealCompany.class.getSimpleName(),
                    options, PromptMessages.INSTANCE.mealComplete_continueButtonLabel());

            return Option.some(PromptUtil.asMealPrompt(prompt, answers -> {
                if (!answers.isEmpty()) {
                    String mealCompanyValue = answers.stream()
                            .map(answer -> answer.getValue())
                            .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);

                    return MealOperation.update(meal -> meal.withCustomDataField(MEAL_COMPANY_KEY, mealCompanyValue).withFlag(MEAL_COMPANY_COMPLETE));
                } else
                    return MealOperation.update(meal -> meal.withFlag(MEAL_COMPANY_COMPLETE));
            }));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new MealCompany(), priority);
    }
}
