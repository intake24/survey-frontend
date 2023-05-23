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
import uk.ac.ncl.openlab.intake24.client.survey.scheme.BasicScheme;

import java.util.Date;

public class NDNSDefaultSimplified extends BasicScheme {

    public static final String ID = "ndns_default_simple";
    private static final double MAX_AGE_HOURS = 12.0;

    public NDNSDefaultSimplified(String locale, SurveyParameters surveyParameters, final SurveyInterfaceManager interfaceManager, UserData userData) {
        super(locale, surveyParameters, interfaceManager, userData);
    }

    @Override
    public void showNextPage() {
        final Survey state = getStateManager().getCurrentState();

        if (!state.flags.contains(NameCheckPage.NAME_CHECK_DONE) && !userData.name.isEmpty()) {
            interfaceManager.show(new NameCheckPage(state, userData));
        } else
            super.showNextPage();
    }

    @Override
    protected Rules getRules(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager, RecipeManager recipeManager) {
        Rules baseRules = defaultRules(scriptManager, templateManager, recipeManager);

        return new Rules(
                baseRules.mealPromptRules
                        .plus(AskAboutFoodSourceSimple.withPriority(9)),
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
                        .plus(ConfirmFoodSupplements.withPriority(26))
                        .plus(ListFoodSupplements.withPriority(25))
                        //.plus(RemindSupplements.withPriority(26))
                        .plus(AskAboutProxy.withPriority(24))
                        .plus(AskAboutProxyIssues.withPriority(23)),
                baseRules.selectionRules
        );
    }

    @Override
    public String getDataVersion() {
        return "1";
    }

    @Override
    public String getSchemeId() {
        return ID;
    }

    @Override
    public Boolean getSurveyExpired(Survey survey) {
        long currentTime = System.currentTimeMillis();
        Date startDate = new Date(survey.startTime);

        long msToMidnight = ((23 - startDate.getHours()) * 3600 + (59 - startDate.getMinutes()) * 60 + (59 - startDate.getSeconds() + 1)) * 1000;
        long midnight = survey.startTime + msToMidnight;

        return (((currentTime - survey.startTime) / 3600000.0) > MAX_AGE_HOURS || currentTime > midnight);
    }
}
