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

public class AskAboutSupplements implements PromptRule<Survey, SurveyOperation> {

    public static final String SUPPLEMENTS_KEY = "supplements";

    final private PVector<MultipleChoiceQuestionOption> supplementOptions = TreePVector.<MultipleChoiceQuestionOption>empty()
            .plus(new MultipleChoiceQuestionOption("Multivitamin"))
            .plus(new MultipleChoiceQuestionOption("Multivitamin and mineral"))
            .plus(new MultipleChoiceQuestionOption("Vitamin A"))
            .plus(new MultipleChoiceQuestionOption("Vitamin B complex"))
            .plus(new MultipleChoiceQuestionOption("Vitamin C"))
            .plus(new MultipleChoiceQuestionOption("Vitamin D"))
            .plus(new MultipleChoiceQuestionOption("Vitamin E"))
            .plus(new MultipleChoiceQuestionOption("Calcium"))
            .plus(new MultipleChoiceQuestionOption("Iron"))
            .plus(new MultipleChoiceQuestionOption("Magnesium"))
            .plus(new MultipleChoiceQuestionOption("Selenium"))
            .plus(new MultipleChoiceQuestionOption("Zinc"))
            .plus(new MultipleChoiceQuestionOption("Cod liver/ Fish oil"))
            .plus(new MultipleChoiceQuestionOption("Evening Primrose oil"))
            .plus(new MultipleChoiceQuestionOption("Chondroitin"))
            .plus(new MultipleChoiceQuestionOption("Glucosamine"))
            .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true));


    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(SUPPLEMENTS_KEY) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Did you take any dietary supplements?</p>");

            CheckListPrompt prompt = new CheckListPrompt(promptText, AskAboutSupplements.class.getSimpleName(),
                    supplementOptions, PromptMessages.INSTANCE.mealComplete_continueButtonLabel());


            return Option.some(PromptUtil.asSurveyPrompt(prompt, answers -> {

                String supplementsValue = answers.stream()
                        .map(answer -> answer.details.map(s -> "Other: " + s).getOrElse(answer.value))
                        .reduce("", (s1, s2) -> s1 + (s1.isEmpty() ? "" : ", ") + s2);

                return SurveyOperation.update(survey -> survey.withData(SUPPLEMENTS_KEY, supplementsValue));
            }));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutSupplements(), priority);
    }
}
