/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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

public class MealCompletePrompt implements Prompt<Meal, MealOperation> {
    private final Meal meal;
    private final PromptMessages messages = GWT.create(PromptMessages.class);

    public MealCompletePrompt(final Meal meal) {
        this.meal = meal;
    }

    @Override
    public SurveyStageInterface getInterface(final Callback1<MealOperation> onComplete, final Callback1<Function1<Meal, Meal>> onIntermediateStateChange) {

        final SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(messages.mealComplete_promptText(SafeHtmlUtils.htmlEscape(meal.name.toLowerCase())));

        Button contButton = WidgetFactory.createButton(messages.mealComplete_continueButtonLabel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(MealOperation.noChange);
            }
        });

        Button addButton = WidgetFactory.createButton(messages.mealComplete_editMealButtonLabel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(MealOperation.editFoodsRequest(false));
            }
        });

        Button timeButton = WidgetFactory.createButton(messages.mealComplete_editTimeButtonLabel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(MealOperation.editTimeRequest);
            }
        });

        Button deleteButton = WidgetFactory.createRedButton(messages.mealComplete_deleteButtonLabel(), "mealCompleteDeleteButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(MealOperation.deleteRequest(true));
            }
        });

        FlowPanel contents = new FlowPanel();

        contents.add(WidgetFactory.createPromptPanel(promptText));
        contents.add(WidgetFactory.createButtonsPanel(contButton, addButton, timeButton, deleteButton));

        return new SurveyStageInterface.Aligned(contents, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP, SurveyStageInterface.DEFAULT_OPTIONS, MealCompletePrompt.class.getSimpleName());
    }

    @Override
    public String toString() {
        return "Meal complete prompt";
    }
}
