package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.CheckBoxPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.MultipleChoiceCheckboxQuestion;

import java.util.List;

public class AskAboutSupplements implements PromptRule<Survey, SurveyOperation> {

    public static final String SUPPLEMENTS_KEY = "supplements";

    final private PVector<String> supplementOptions = TreePVector.<String>empty()
            .plus("Multivitamin")
            .plus("Multivitamin and mineral")
            .plus("Vitamin A")
            .plus("Vitamin B complex")
            .plus("Vitamin C")
            .plus("Vitamin D")
            .plus("Vitamin E")
            .plus("Calcium")
            .plus("Iron")
            .plus("Magnesium")
            .plus("Selenium")
            .plus("Zinc")
            .plus("Cod liver/ Fish oil")
            .plus("Evening Primrose oil")
            .plus("Chondroitin")
            .plus("Glucosamine");

    private String concat(List<String> options) {
        StringBuffer sb = new StringBuffer();

        for (String s : options) {
            if (sb.length() > 0)
                sb.append(", ");
            sb.append(s);
        }

        return sb.toString();
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(SUPPLEMENTS_KEY) && state.portionSizeComplete()) {

            SafeHtml promptText = SafeHtmlUtils.fromSafeConstant("<p>Did you take any dietary supplements?</p>");

            new MultipleChoiceCheckboxQuestion(state, SafeHtmlUtils
                    .fromSafeConstant("<p>Do you take any dietary supplements e.g. Multivitamins?</p>"), "Continue", supplementOptions,
                    "supplements", Option.some("Other"));

            CheckBoxPrompt prompt = new CheckBoxPrompt(promptText, AskAboutSupplements.class.getSimpleName(),
                    supplementOptions, PromptMessages.INSTANCE.mealComplete_continueButtonLabel(),
                    "cookedAtHomeOption", Option.some("Other (please specify)"));

            return Option.some(PromptUtil.asSurveyPrompt(prompt, supplements -> SurveyOperation.update(survey -> survey.withData(SUPPLEMENTS_KEY, concat(supplements)))));
        } else {
            return Option.none();
        }
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutSupplements(), priority);
    }
}
