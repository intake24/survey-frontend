package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.survey.Meal;
import uk.ac.ncl.openlab.intake24.client.survey.Prompt;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyStageInterface;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;


public class AdditionalFoodReminderPrompt implements Prompt<Meal, MealOperation> {
    private final PromptMessages messages = GWT.create(PromptMessages.class);

    private final SafeHtml promptText;
    private final String addFoodKey;

    public AdditionalFoodReminderPrompt(SafeHtml promptText, String addFoodKey) {
        this.addFoodKey = addFoodKey;
        this.promptText = promptText;
    }

    @Override
    public SurveyStageInterface getInterface(final Callback1<MealOperation> onComplete,
                                             final Callback1<Function1<Meal, Meal>> onIntermediateStateChange) {
        final FlowPanel content = new FlowPanel();

        content.add(WidgetFactory.createPromptPanel(promptText));

        Button goBackBtn = WidgetFactory.createGreenButton(messages.additionalFood_goBack(),
                "goBackButton", clickEvent -> onComplete.call(MealOperation.editFoodsRequest(false)));
        Button continueBtn = WidgetFactory.createGreenButton(messages.additionalFood_continue(),
                "continueButton", clickEvent -> onComplete.call(MealOperation.update(state -> state.withFlag(addFoodKey))));

        content.add(WidgetFactory.createButtonsPanel(goBackBtn, continueBtn));

        return new SurveyStageInterface.Aligned(content, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP, SurveyStageInterface.DEFAULT_OPTIONS, DrinkReminderPrompt.class.getSimpleName());
    }

    @Override
    public String toString() {
        return "Additional food reminder prompt";
    }
}
