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

package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;


import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyParameters;
import uk.ac.ncl.openlab.intake24.client.api.survey.UserData;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;
import uk.ac.ncl.openlab.intake24.client.survey.rules.*;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup.*;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup.AskAboutFoodSource;


public class FollowUp extends NDNSDefault {
    public static final String ID = "ndns_follow_up";

    public FollowUp(String locale, SurveyParameters surveyParameters, final SurveyInterfaceManager interfaceManager, UserData userData) {
        super(locale, surveyParameters, interfaceManager, userData);
    }

    @Override
    protected Rules getRules(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager, RecipeManager recipeManager) {
        Rules baseRules = defaultRules(scriptManager, templateManager, recipeManager);

        return new Rules(
                baseRules.mealPromptRules
                        .plus(AskAboutFoodSource.withPriority(9)),
                TreePVector.<WithPriority<PromptRule<FoodEntry, FoodOperation>>>empty()
                        .plus(ShowNextPortionSizeStep.withPriority(scriptManager, 0))
                        .plus(ChoosePortionSizeMethod.withPriority(1))
                        .plus(AskForMissingFoodDescription.withPriority(2))
                        .plus(SplitFood.withPriority(4))
                        .plus(InformFoodComplete.withPriority(-100)),
                baseRules.extendedFoodPromptRules,
                baseRules.surveyPromptRules
                        .plus(RemindFrequentlyForgottenFoods.withPriority(31))
                        .plus(AskIfUsualAmount.withPriority(30))
                        .plus(AskIfUsualAmountReason.withPriority(29))
                        .plus(AskAboutDiet.withPriority(28))
                        .plus(AskAboutCookingOil.withPriority(27))
                        .plus(RemindSupplements.withPriority(26))
                        .plus(AskAboutSelfIsolation.withPriority(25))
                        .plus(InfrequentFoodIntro.withPriority(24, userData))
                        .plus(InfrequentFoodSelectedFish.withPriority(23, userData))
                        .plus(InfrequentFoodAnyFish.withPriority(22, userData))
                        .plus(InfrequentFoodWhiteMeat.withPriority(21, userData))
                        .plus(InfrequentFoodFruitJuice.withPriority(20, userData))
                        .plus(InfrequentFoodSoftDrinks.withPriority(19, userData))
                        .plus(AskAboutProxy.withPriority(18))
                        .plus(AskAboutProxyIssues.withPriority(17)),
                baseRules.selectionRules
        );
    }
}
