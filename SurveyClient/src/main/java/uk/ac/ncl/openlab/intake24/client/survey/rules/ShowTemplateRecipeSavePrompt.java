/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import static org.workcraft.gwt.shared.client.CollectionUtils.forall;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.SaveHomeRecipePrompt;

public class ShowTemplateRecipeSavePrompt implements PromptRule<Pair<FoodEntry, Meal>, MealOperation> {
	
	private final RecipeManager recipeManager;

	public ShowTemplateRecipeSavePrompt(RecipeManager recipeManager) {
		this.recipeManager = recipeManager;
	}

	@Override
	public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> apply(final Pair<FoodEntry, Meal> data, SelectionMode selectionType, final PSet<String> surveyFlags) {
		return data.left.accept(new FoodEntry.Visitor<Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>>>() {
			@Override
			public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitRaw(RawFood food) {
				return Option.none();
			}

			@Override
			public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitEncoded(EncodedFood food) {
				return Option.none();
			}

			@Override
			public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitTemplate(final TemplateFood food) {
				if ( food.isTemplateComplete()
						&& forall(Meal.linkedFoods(data.right.foods, data.left), FoodEntry.isPortionSizeComplete)						
						&& food.customData.containsKey(Recipe.SERVINGS_NUMBER_KEY)
						&& !food.flags.contains(Recipe.IS_SAVED_FLAG)) {
					
					return Option.<Prompt<Pair<FoodEntry, Meal>, MealOperation>>some(new SaveHomeRecipePrompt(recipeManager, data));

				} else
					return Option.none();
			}

			@Override
			public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitMissing(MissingFood food) {
				return Option.none();
			}

			@Override
			public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitCompound(
					CompoundFood food) {
				return Option.none();
			}

		});
	}

	@Override
	public String toString() {
		return "Suggest to save template recipe for future use";
	}

	public static WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>> withPriority(int priority, RecipeManager recipeManager) {
		return new WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>(new ShowTemplateRecipeSavePrompt(recipeManager), priority);
	}
}