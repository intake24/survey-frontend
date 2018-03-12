/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import static org.workcraft.gwt.shared.client.CollectionUtils.filter;

import java.util.ArrayList;
import java.util.List;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Panel;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;
import uk.ac.ncl.openlab.intake24.client.ui.foodlist.EditableFoodList;

public class EditRecipeIngredientsPrompt implements Prompt<Pair<FoodEntry, Meal>, MealOperation> {
	private final Meal meal;
	private final int foodIndex;
	
	private EditableFoodList ingredientList;
	
	private final static PromptMessages messages = PromptMessages.Util.getInstance();
	
	private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

	private final static PVector<ShepherdTour.Step> tour = TreePVector.<ShepherdTour.Step>empty()
			.plus(new ShepherdTour.Step("foodList", "#intake24-ingredient-list", helpMessages.editIngredients_foodListTitle(), helpMessages.editIngredients_foodListDescription()))
			.plus(new ShepherdTour.Step("continueButton", "#intake24-done-button", helpMessages.editIngredients_continueButtonTitle(), helpMessages.editIngredients_continueButtonDescription(), "top left", "bottom left"));
		
	public EditRecipeIngredientsPrompt(Meal meal, int foodIndex) {
		this.meal = meal;
		this.foodIndex  = foodIndex;		
	}

	@Override
	public SurveyStageInterface getInterface(final Callback1<MealOperation> onComplete, final Callback1<Function1<Pair<FoodEntry, Meal>, Pair<FoodEntry, Meal>>> onIntermediateStateChange) {
		final SafeHtml promptText = SafeHtmlUtils.fromSafeConstant(messages.editRecipeIngredientsPrompt_promptText(SafeHtmlUtils.htmlEscape(meal.foods.get(foodIndex).description())));
		final UUID compoundFoodId = meal.foods.get(foodIndex).link.id;
		
		final Function1<FoodEntry, Boolean> noLinkedFoodsFilter = new Function1<FoodEntry, Boolean>() {
			@Override
			public Boolean apply(FoodEntry argument) {
				return argument.link.linkedTo.accept(new Option.Visitor<UUID, Boolean>() {
					@Override
					public Boolean visitSome(UUID item) {
						return !item.equals(compoundFoodId);
					}

					@Override
					public Boolean visitNone() {
						return true;
					}									
				});
			}
		};						
		
		final Button done = WidgetFactory.createGreenButton(messages.editRecipeIngredientsPrompt_continueButtonLabel(), "editRecipeContinueButton", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onComplete.call(MealOperation.update(new Function1<Meal, Meal>() {
					@Override
					public Meal apply(Meal argument) {
						ArrayList<FoodEntry> linkedFoods = new ArrayList<FoodEntry>();
						for (FoodEntry e: ingredientList.getEnteredItems())
							linkedFoods.add(e.relink(FoodLink.newLinked(compoundFoodId)).withFlag(RawFood.FLAG_RECIPE_INGREDIENT));

						return argument								
								.withFoods(filter(argument.foods, noLinkedFoodsFilter))
								.plusAllFoods(linkedFoods)
								.updateFood(foodIndex, argument.foods.get(foodIndex).withFlag(CompoundFood.FLAG_INGREDIENTS_COMPLETE));
					}
				}));
			}
		});
		
		done.getElement().setId("intake24-done-button");
		
		ingredientList = new EditableFoodList( messages.editMeal_addFoodButtonLabel(), false, new Callback1<List<FoodEntry>>() {			
			@Override
			public void call(List<FoodEntry> arg1) {
				onIntermediateStateChange.call(new Function1<Pair<FoodEntry, Meal>, Pair<FoodEntry, Meal>>() {
					@Override
					public Pair<FoodEntry, Meal> apply(Pair<FoodEntry, Meal> argument) {
						
						ArrayList<FoodEntry> linkedFoods = new ArrayList<FoodEntry>();
						for (FoodEntry e: ingredientList.getEnteredItems())
							linkedFoods.add(e.relink(FoodLink.newLinked(compoundFoodId)).withFlag(RawFood.FLAG_RECIPE_INGREDIENT));
						
						return Pair.create(null, argument.right.withFoods(filter(argument.right.foods, noLinkedFoodsFilter)).plusAllFoods(linkedFoods));				
					}
				});
			}
		});
		
		ingredientList.disableLinkedFoodsIndentation();
		
		for (FoodEntry f: Meal.linkedFoods(meal.foods, meal.foods.get(foodIndex))) 
			ingredientList.addItem(Option.some(f));

		
		final HTMLPanel foodHeader = new HTMLPanel("h2", SafeHtmlUtils.htmlEscape(messages.editRecipeIngredientsPrompt_ingredientsHeader()));		
		final FlowPanel foodListContainer = new FlowPanel();
		foodListContainer.getElement().setId("intake24-ingredient-list");
		foodListContainer.add(foodHeader);
		foodListContainer.add(ingredientList);
		ShepherdTour.makeShepherdTarget(foodListContainer);
				
		FlowPanel contents = new FlowPanel();
		contents.addStyleName("intake24-edit-meal-prompt");

		Panel promptPanel = WidgetFactory.createPromptPanel(promptText, ShepherdTour.createTourButton(tour, EditRecipeIngredientsPrompt.class.getSimpleName()));
		contents.add(promptPanel);
		ShepherdTour.makeShepherdTarget(promptPanel);				
			
		contents.add(foodListContainer);
		contents.add(WidgetFactory.createButtonsPanel(done));
		
		ShepherdTour.makeShepherdTarget(done);
		
		return new SurveyStageInterface.Aligned(contents, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP, SurveyStageInterface.DEFAULT_OPTIONS, EditRecipeIngredientsPrompt.class.getSimpleName());
	}

	@Override
	public String toString() {
		return "Edit recipe ingredients prompt";
	}
}