/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.scheme;


import com.google.gwt.user.client.ui.Anchor;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.*;
import uk.ac.ncl.openlab.intake24.client.LogRecorder;
import uk.ac.ncl.openlab.intake24.client.PredefinedMeals;
import uk.ac.ncl.openlab.intake24.client.ProcessMilkInHotDrinks;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyParameters;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.DefaultPortionSizeScripts;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.rules.*;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


/**
 * Basic implementation of a survey scheme. Uses the default prompt rules (see
 * <b>defaultRules</b>), and starting meals (see <b>startingSurveyData</b>).
 */
public abstract class BasicScheme implements SurveyScheme {
    final static double MAX_AGE_HOURS = 18.0;

    final protected SurveyInterfaceManager interfaceManager;
    private StateManager stateManager;
    final protected PromptManager defaultPromptManager;
    final protected SelectionManager defaultSelectionManager;
    final protected PortionSizeScriptManager defaultScriptManager;
    final protected CompoundFoodTemplateManager defaultTemplateManager;
    final protected RecipeManager recipeManager;
    final protected LogRecorder log;
    final protected String locale;
    final protected SurveyParameters surveyParameters;

    final protected TreePVector<Function1<Survey, Survey>> basicPostProcess = TreePVector
            .<Function1<Survey, Survey>>empty()
            .plus(new ProcessMilkInHotDrinks());

    private static Logger logger = Logger.getLogger("BasicScheme");

    protected Survey startingSurveyData() {
        return new Survey(PredefinedMeals.getStartingMealsForCurrentLocale(), new Selection.EmptySelection(SelectionMode.AUTO_SELECTION),
                System.currentTimeMillis(), System.currentTimeMillis(), HashTreePSet.<String>empty(), HashTreePMap.<String, String>empty());
    }

    protected Survey postProcess(Survey data, PVector<Function1<Survey, Survey>> functions) {
        Survey postProcessed = data;

        for (Function1<Survey, Survey> f : functions)
            postProcessed = f.apply(postProcessed);

        return postProcessed;
    }

    protected Rules defaultRules(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager,
                                 RecipeManager recipeManager) {
        return new Rules(
                // meal associatedFoods
                TreePVector.<WithPriority<PromptRule<Meal, MealOperation>>>empty()
                        .plus(AskForMealTime.withPriority(4))
                        .plus(ShowEditMeal.withPriority(3))
                        .plus(ShowDrinkReminderPrompt.withPriority(2))
                        .plus(ShowReadyMealsPrompt.withPriority(0)),

                // food associatedFoods
                TreePVector.<WithPriority<PromptRule<FoodEntry, FoodOperation>>>empty()
                        .plus(ShowBrandNamePrompt.withPriority(-1))
                        .plus(ShowNextPortionSizeStep.withPriority(scriptManager, 0))
                        .plus(ChoosePortionSizeMethod.withPriority(1))
                        .plus(AskForMissingFoodDescription.withPriority(2))
                        .plus(ShowSimpleHomeRecipePrompt.withPriority(2))
                        .plus(AskIfHomeRecipe.withPriority(3))
                        .plus(SplitFood.withPriority(4))
                        .plus(InformFoodComplete.withPriority(-100)),

                // extended food propmts
                TreePVector.<WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>>empty()
                        .plus(ShowEditIngredientsPrompt.withPriority(3))
                        .plus(AskToLookupFood.withPriority(3, locale, "paRules", false, recipeManager))
                        .plus(ShowSameAsBeforePrompt.withPriority(3, getSchemeId(), getDataVersion(), scriptManager, templateManager))
                        .plus(ShowHomeRecipeServingsPrompt.withPriority(2))
                        .plus(ShowTemplateRecipeSavePrompt.withPriority(1, recipeManager))
                        .plus(ShowCompoundFoodPrompt.withPriority(0, locale))
                        .plus(ShowAssociatedFoodPrompt.withPriority(0, locale, Option.none()))
                        .plus(ShowBreadLinkedFoodAmountPrompt.withPriority(0))

                ,
                // global associatedFoods

                TreePVector.<WithPriority<PromptRule<Survey, SurveyOperation>>>empty()
                        .plus(ConfirmCompletion.withPriority(0))
                        .plus(ShowEnergyValidationPrompt.withPriority(1, 500.0))
                        .plus(ShowEmptySurveyPrompt.withPriority(1))
                        .plus(ShowTimeGapPrompt.withPriority(2, 180, new Time(9, 0), new Time(21, 0)))

                ,

                // selection rules
                TreePVector.<WithPriority<SelectionRule>>empty()
                        .plus(SelectForPortionSize.withPriority(3))
                        .plus(SelectRawFood.withPriority(2))
                        .plus(SelectFoodForAssociatedPrompts.withPriority(1))
                        .plus(SelectIncompleteFreeEntryMeal.withPriority(1))
                        .plus(SelectMealWithNoDrink.withPriority(1))
                        .plus(SelectUnconfirmedMeal.withPriority(1))
                        .plus(SelectMealForReadyMeals.withPriority(1)));
    }

    public BasicScheme(String locale, SurveyParameters surveyParameters, final SurveyInterfaceManager interfaceManager) {
        this.surveyParameters = surveyParameters;
        this.log = new LogRecorder();
        this.interfaceManager = interfaceManager;
        this.locale = locale;

        interfaceManager.setCallbacks(new Callback1<Survey>() {
            @Override
            public void call(Survey updatedState) {
                if (updatedState.flags.contains(Survey.FLAG_SKIP_HISTORY))
                    stateManager.updateState(updatedState.clearFlag(Survey.FLAG_SKIP_HISTORY), false);
                else
                    stateManager.updateState(updatedState, true);
                showNextPage();
            }
        }, new Callback2<Survey, Boolean>() {
            @Override
            public void call(Survey updatedState, Boolean makeHistoryEntry) {
                stateManager.updateState(updatedState, makeHistoryEntry);
            }
        });

        defaultScriptManager = new PortionSizeScriptManager(DefaultPortionSizeScripts.getCtors());

        defaultTemplateManager = new CompoundFoodTemplateManager(HashTreePMap.<String, TemplateFoodData>empty()
                .plus("sandwich", FoodTemplates.sandwich)
                .plus("salad", FoodTemplates.salad));

        recipeManager = new RecipeManager(getSchemeId(), getDataVersion(), defaultScriptManager, defaultTemplateManager);

        stateManager = new StateManager(getSchemeId(), getDataVersion(),
                surveyParameters.storeUserSessionOnServer, new Callback() {
            @Override
            public void call() {
                showNextPage();
            }
        }, defaultScriptManager);

        final Rules rules = defaultRules(defaultScriptManager, defaultTemplateManager, recipeManager);

        defaultPromptManager = new RuleBasedPromptManager(rules);

        // final SelectionManager selectionManager = new
        // RuleBasedSelectionManager(rules.selectionRules);

        defaultSelectionManager = new PromptAvailabilityBasedSelectionManager(defaultPromptManager);

        if (surveyParameters.storeUserSessionOnServer) {
            StateManagerUtil.getSavedStateFromServerOrStorage(AuthCache.getCurrentUserId(), getSchemeId(),
                    getDataVersion(), defaultScriptManager, defaultTemplateManager,
                    new MethodCallback<Option<Survey>>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(Method method, Option<Survey> surveyOption) {
                            setInitialState(surveyOption);
                        }
                    });
        } else {
            setInitialState(StateManagerUtil.getLatestState(AuthCache.getCurrentUserId(), getSchemeId(),
                    getDataVersion(), defaultScriptManager, defaultTemplateManager));
        }
    }

    private void setInitialState(Option<Survey> surveyOpt) {
        Survey initialState = surveyOpt.accept(new Option.Visitor<Survey, Survey>() {
            @Override
            public Survey visitSome(Survey data) {
                Long surveyDate = getIssueDate(data);
                double age = (System.currentTimeMillis() - surveyDate) / 3600000.0;
                logger.fine("Saved state is " + age + " hours old.");
                logger.fine("Last saved " + data.lastSaved);

                if (age > MAX_AGE_HOURS) {
                    logger.fine("Saved state is older than " + MAX_AGE_HOURS + " hours and has expired.");
                    return startingSurveyData();
                } else {
                    return data.clearCompletionConfirmed()
                            .clearEnergyValueConfirmed();
                }
            }

            @Override
            public Survey visitNone() {
                // log.info("No saved state, starting new survey.");
                return startingSurveyData();
            }
        });

        stateManager.updateState(initialState, false);

        showNextPage();
    }

    protected StateManager getStateManager() {
        return stateManager;
    }

    @Override
    public abstract void showNextPage();

    @Override
    public abstract String getDataVersion();

    @Override
    public abstract String getSchemeId();

    @Override
    public abstract Long getIssueDate(Survey survey);

    @Override
    public List<Anchor> navBarLinks() {
        return Collections.emptyList();
    }
}