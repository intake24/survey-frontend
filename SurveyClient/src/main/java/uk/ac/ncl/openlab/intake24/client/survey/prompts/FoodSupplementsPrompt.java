/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.ListFoodSupplements;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;
import uk.ac.ncl.openlab.intake24.client.ui.foodlist.EditableFoodList;

import java.util.ArrayList;

public class FoodSupplementsPrompt implements Prompt<Survey, SurveyOperation> {
    private EditableFoodList supplementsList;

    private final static PromptMessages messages = PromptMessages.Util.getInstance();

    private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final static PVector<ShepherdTour.Step> tour = TreePVector.<ShepherdTour.Step>empty()
            .plus(new ShepherdTour.Step("foodList", "#intake24-supplement-list", helpMessages.foodSupplements_listTitle(), helpMessages.foodSupplements_listDescription()))
            .plus(new ShepherdTour.Step("continueButton", "#intake24-done-button", helpMessages.foodSupplements_continueButtonTitle(), helpMessages.foodSupplements_continueButtonDescription(), "top left", "bottom left"));

    public FoodSupplementsPrompt() {
    }

    @Override
    public SurveyStageInterface getInterface(final Callback1<SurveyOperation> onComplete, final Callback1<Function1<Survey, Survey>> onIntermediateStateChange) {
        final SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(messages.foodSupplements_promptText());

        final Button done = WidgetFactory.createGreenButton(messages.editRecipeIngredientsPrompt_continueButtonLabel(), "editRecipeContinueButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(SurveyOperation.update(survey -> {

                    ArrayList<FoodEntry> supplements = new ArrayList<>();

                    for (FoodEntry e : supplementsList.getEnteredItems())
                        supplements.add(e.withFlag(RawFood.FLAG_FOOD_SUPPLEMENT));

                    Meal supplementsMeal = new Meal(messages.foodSupplements_mealName(), TreePVector.from(supplements), Option.some(new Time(23, 59)), HashTreePSet.empty(), HashTreePMap.empty());

                    return survey.plusMeal(supplementsMeal.withFlag(ListFoodSupplements.supplementsMealFlag).withFlag(Meal.FLAG_FREE_ENTRY_COMPLETE));
                }));
            }
        });

        done.getElement().setId("intake24-done-button");

        supplementsList = new EditableFoodList(messages.editMeal_addFoodButtonLabel(), false, s -> {
        });


        final HTMLPanel foodHeader = new HTMLPanel("h2", SafeHtmlUtils.htmlEscape(messages.foodSupplements_listTitle()));
        final FlowPanel foodListContainer = new FlowPanel();
        foodListContainer.getElement().setId("intake24-supplement-list");
        foodListContainer.add(foodHeader);
        foodListContainer.add(supplementsList);
        ShepherdTour.makeShepherdTarget(foodListContainer);

        FlowPanel contents = new FlowPanel();
        contents.addStyleName("intake24-edit-meal-prompt");

        Panel promptPanel = WidgetFactory.createPromptPanel(promptText, ShepherdTour.createTourButton(tour, FoodSupplementsPrompt.class.getSimpleName()));
        contents.add(promptPanel);
        ShepherdTour.makeShepherdTarget(promptPanel);

        contents.add(foodListContainer);
        contents.add(WidgetFactory.createButtonsPanel(done));

        ShepherdTour.makeShepherdTarget(done);

        return new SurveyStageInterface.Aligned(contents, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP, SurveyStageInterface.DEFAULT_OPTIONS, FoodSupplementsPrompt.class.getSimpleName());
    }

    @Override
    public String toString() {
        return "List food supplements prompt";
    }
}
