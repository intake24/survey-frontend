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

public class AskAboutCookingOil implements PromptRule<Survey, SurveyOperation> {

    public static final String COOKING_OIL_KEY = "cookingOil";

    final private PVector<MultipleChoiceQuestionOption> supplementOptions = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("Sunflower oil"))
            .plus(new MultipleChoiceQuestionOption("Vegetable oil (rapeseed)"))
            .plus(new MultipleChoiceQuestionOption("Corn oil"))
            .plus(new MultipleChoiceQuestionOption("Palm oil"))
            .plus(new MultipleChoiceQuestionOption("Coconut oil"))
            .plus(new MultipleChoiceQuestionOption("Olive oil"))
            .plus(new MultipleChoiceQuestionOption("Butter"))
            .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true))
            .plus(new MultipleChoiceQuestionOption("Did not use"));

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(COOKING_OIL_KEY) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Which type of cooking fat/oil did you used most often when you completed this recall?</p>");

            RadioButtonPrompt prompt = new RadioButtonPrompt(promptText, AskAboutCookingOil.class.getSimpleName(),
                    supplementOptions, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(), "cookingOilOptions");

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer -> SurveyOperation.update(survey -> survey.withData(COOKING_OIL_KEY, answer.getValue()))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutCookingOil(), priority);
    }
}
