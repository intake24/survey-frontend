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

import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyParameters;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.BasicScheme;

public class April2019 extends BasicScheme {
    final private static SurveyMessages surveyMessages = SurveyMessages.Util.getInstance();

    public static final String ID = "ndns419";
    public static final double MAX_AGE_HOURS = 12.0;

    public April2019(String locale, SurveyParameters surveyParameters, final SurveyInterfaceManager interfaceManager) {
        super(locale, surveyParameters, interfaceManager);
    }

    @Override
    protected Rules getRules(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager, RecipeManager recipeManager) {
        Rules baseRules = defaultRules(scriptManager, templateManager, recipeManager);

        return new Rules(
                baseRules.mealPromptRules
                        .plus(AskIfCookedAtHome.withPriority(9))
                        .plus(AskAboutFoodSource.withPriority(9)),
                baseRules.foodPromptRules,
                baseRules.extendedFoodPromptRules,
                baseRules.surveyPromptRules
                        .plus(AskAboutSupplements.withPriority(30))
                        .plus(AskIfUsualAmount.withPriority(29)),
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
        Double age = (System.currentTimeMillis() - survey.startTime) / 3600000.0;
        logger.fine("Saved state is " + age + " hours old.");
        return age > MAX_AGE_HOURS;
    }

}
