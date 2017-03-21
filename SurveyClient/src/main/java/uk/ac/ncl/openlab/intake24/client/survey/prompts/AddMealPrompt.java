/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;


import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.PredefinedMeals;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class AddMealPrompt implements Prompt<Survey, SurveyOperation> {
    PromptMessages messages = GWT.create(PromptMessages.class);

    private final int selectedIndex;

    private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final static PVector<ShepherdTour.Step> tour = TreePVector.<ShepherdTour.Step>empty()
            .plus(new ShepherdTour.Step("predefMealName", "#intake24-predef-meal-name", helpMessages.addMeal_predefNameTitle(),
                    helpMessages.addMeal_predefNameDescription()))
            .plus(new ShepherdTour.Step("customMealName", "#intake24-custom-meal-name", helpMessages.addMeal_customNameTitle(),
                    helpMessages.addMeal_customNameDescription()))
            .plus(new ShepherdTour.Step("acceptButton", "#intake24-accept-button", helpMessages.addMeal_acceptButtonTitle(),
                    helpMessages.addMeal_acceptButtonDescription()))
            .plus(new ShepherdTour.Step("cancelButton", "#intake24-cancel-button", helpMessages.addMeal_cancelButtonTitle(),
                    helpMessages.addMeal_cancelButtonDescription()));

    public AddMealPrompt(int mealNameIndex) {
        this.selectedIndex = mealNameIndex;
    }

    @Override
    public SurveyStageInterface getInterface(final Callback1<SurveyOperation> onComplete,
                                             final Callback1<Function1<Survey, Survey>> onIntermediateStateChange) {
        SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(messages.addMeal_promptText());

        HorizontalPanel mealNamePanel = new HorizontalPanel();
        mealNamePanel.setSpacing(5);
        mealNamePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        HorizontalPanel mealListPanel = new HorizontalPanel();
        mealListPanel.setSpacing(5);
        mealListPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        final TextBox text = new TextBox();

        text.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                text.selectAll();
            }
        });

        text.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    if (!text.getText()
                            .isEmpty())
                        onComplete.call(SurveyOperation.update(new Function1<Survey, Survey>() {
                            @Override
                            public Survey apply(Survey argument) {
                                return argument.plusMeal(Meal.empty(text.getText()))
                                        .withSelection(new Selection.SelectedMeal(argument.meals.size(), SelectionMode.AUTO_SELECTION));
                            }
                        }));
                }
            }
        });

        final ListBox list = new ListBox();

        for (String s : PredefinedMeals.getSuggestedMealNamesForCurrentLocale())
            list.addItem(s);

        list.setSelectedIndex(selectedIndex);

        text.setText(list.getItemText(list.getSelectedIndex()));

        list.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                text.setText(list.getItemText(list.getSelectedIndex()));
            }
        });

        mealListPanel.add(new Label(messages.addMeal_predefLabel()));
        mealListPanel.add(list);
        mealListPanel.getElement()
                .setId("intake24-predef-meal-name");
        ShepherdTour.makeShepherdTarget(mealListPanel);

        mealNamePanel.add(new Label(messages.addMeal_customLabel()));
        mealNamePanel.add(text);
        mealNamePanel.getElement()
                .setId("intake24-custom-meal-name");
        ShepherdTour.makeShepherdTarget(mealNamePanel);

        Button acceptButton = WidgetFactory.createButton(messages.addMeal_addButtonLabel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!text.getText()
                        .isEmpty())
                    onComplete.call(SurveyOperation.update(new Function1<Survey, Survey>() {
                        @Override
                        public Survey apply(Survey argument) {
                            return argument.plusMeal(Meal.empty(text.getText()))
                                    .withSelection(new Selection.SelectedMeal(argument.meals.size(), SelectionMode.AUTO_SELECTION));
                        }
                    }));
            }
        });

        acceptButton.getElement()
                .setId("intake24-accept-button");

        Button cancelButton = WidgetFactory.createButton(messages.addMeal_cancelButtonLabel(), new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(SurveyOperation.noChange);
            }
        });

        cancelButton.getElement()
                .setId("intake24-cancel-button");

        ShepherdTour.makeShepherdTarget(acceptButton, cancelButton);

        FlowPanel contents = new FlowPanel();
        contents.add(WidgetFactory.createPromptPanel(promptText,
                ShepherdTour.createTourButton(tour, AddMealPrompt.class.getSimpleName())));
        contents.add(mealListPanel);
        contents.add(mealNamePanel);
        contents.add(WidgetFactory.createButtonsPanel(acceptButton, cancelButton));

        return new SurveyStageInterface.Aligned(contents, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP,
                SurveyStageInterface.DEFAULT_OPTIONS, AddMealPrompt.class.getSimpleName());
    }

}