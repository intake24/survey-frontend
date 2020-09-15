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

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.StandardUnitDef;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.SameAsBeforePrompt;

public class ShowSameAsBeforePrompt implements PromptRule<Pair<FoodEntry, Meal>, MealOperation> {
    private final PortionSizeScriptManager scriptManager;
    private final CompoundFoodTemplateManager templateManager;
    private final PVector<StandardUnitDef> milkPercentageOptions;

    private final String schemeId;
    private final String versionId;

    public ShowSameAsBeforePrompt(String schemeId, String versionId, PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager,
                                  PVector<StandardUnitDef> milkPercentageOptions) {

        this.schemeId = schemeId;
        this.versionId = versionId;
        this.scriptManager = scriptManager;
        this.templateManager = templateManager;
        this.milkPercentageOptions = milkPercentageOptions;
    }

    @Override
    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> apply(final Pair<FoodEntry, Meal> pair, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (pair.left.isEncoded()) {
            EncodedFood f = pair.left.asEncoded();
            if (!f.data.sameAsBeforeOption || f.notSameAsBefore() || f.isInCategory(SpecialData.FOOD_CODE_MILK_IN_HOT_DRINK) || f.isPortionSizeComplete() || f.link.isLinked())
                return Option.none();
            else {
                Option<SameAsBefore> sameAsBefore = StateManagerUtil.getSameAsBefore(f.data.code, schemeId, versionId, scriptManager, templateManager);
                return sameAsBefore.accept(new Option.Visitor<SameAsBefore, Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>>>() {
                    @Override
                    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitSome(SameAsBefore item) {
                        return Option.<Prompt<Pair<FoodEntry, Meal>, MealOperation>>some(new SameAsBeforePrompt(pair, pair.right.foodIndex(pair.left), item, milkPercentageOptions));
                    }

                    @Override
                    public Option<Prompt<Pair<FoodEntry, Meal>, MealOperation>> visitNone() {
                        return Option.none();
                    }
                });
            }
        } else
            return Option.none();
    }

    public static WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>> withPriority(int priority, String schemeId, String versionId, PortionSizeScriptManager scriptManager,
                                                                                              CompoundFoodTemplateManager templateManager, PVector<StandardUnitDef> milkPercentageOptions) {
        return new WithPriority<PromptRule<Pair<FoodEntry, Meal>, MealOperation>>(new ShowSameAsBeforePrompt(schemeId, versionId, scriptManager, templateManager, milkPercentageOptions), priority);
    }
}
