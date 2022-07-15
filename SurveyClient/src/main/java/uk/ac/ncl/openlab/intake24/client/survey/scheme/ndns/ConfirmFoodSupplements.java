package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.YesNoPrompt;

public class ConfirmFoodSupplements implements PromptRule<Survey, SurveyOperation> {

    public static final String supplementsConfirmationKey = "food-supplements";

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.customData.containsKey(supplementsConfirmationKey) && state.portionSizeComplete()) {
            YesNoPrompt prompt = new YesNoPrompt(SafeHtmlUtils.fromSafeConstant(PromptMessages.INSTANCE.foodSupplements_confirmationPromptText()),
                    PromptMessages.INSTANCE.yesNoQuestion_defaultYesLabel(),
                    PromptMessages.INSTANCE.foodSupplements_noLabel());

            return Option.some(PromptUtil.asSurveyPrompt(prompt, answer ->
                    SurveyOperation.update(survey -> survey.withData(supplementsConfirmationKey, Boolean.toString(answer)))));
        } else {
            return Option.none();
        }
    }


    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new ConfirmFoodSupplements(), priority);
    }
}
