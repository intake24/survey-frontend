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

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pcollections.*;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.survey.CompoundFoodTemplateManager;
import uk.ac.ncl.openlab.intake24.client.survey.Meal;
import uk.ac.ncl.openlab.intake24.client.survey.Selection;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

public class SSurvey {

    @JsonProperty
    public long startTime;
    @JsonProperty
    public final PVector<SMeal> meals;
    @JsonProperty
    public final SSelection selectedElement;
    @JsonProperty
    public final PSet<String> flags;
    @JsonProperty
    public final PMap<String, String> customData;
    @JsonProperty
    public final String schemeId;
    @JsonProperty
    public final String versionId;


    @JsonCreator
    public SSurvey(@JsonProperty("meals") List<SMeal> meals, @JsonProperty("selectedElement") SSelection selectedElement,
                   @JsonProperty("startTime") long startTime, @JsonProperty("flags") Set<String> flags,
                   @JsonProperty("customData") Map<String, String> customData, @JsonProperty("schemeId") String schemeId,
                   @JsonProperty("versionId") String versionId) {
        this.startTime = startTime;
        this.meals = TreePVector.from(meals);
        this.selectedElement = selectedElement;
        this.flags = HashTreePSet.from(flags);
        this.customData = HashTreePMap.from(customData);
        this.schemeId = schemeId;
        this.versionId = versionId;
    }

    public SSurvey(Survey survey, String scheme_id, String version_id) {
        this.startTime = survey.startTime;
        this.schemeId = scheme_id;
        this.versionId = version_id;
        this.meals = map(survey.meals, new Function1<Meal, SMeal>() {
            @Override
            public SMeal apply(Meal argument) {
                return new SMeal(argument);
            }
        });

        this.selectedElement = survey.selectedElement.accept(new Selection.Visitor<SSelection>() {
            @Override
            public SSelection visitMeal(Selection.SelectedMeal meal) {
                return new SSelection.SSelectedMeal(meal);
            }

            @Override
            public SSelection visitFood(Selection.SelectedFood food) {
                return new SSelection.SSelectedFood(food);
            }

            @Override
            public SSelection visitNothing(Selection.EmptySelection selection) {
                return new SSelection.SEmptySelection(selection);
            }
        });

        this.flags = survey.flags;
        this.customData = survey.customData;
    }

    public Survey toSurvey(final PortionSizeScriptManager scriptManager, final CompoundFoodTemplateManager templateManager) {

        PVector<Meal> surveyMeals = map(meals, new Function1<SMeal, Meal>() {
            @Override
            public Meal apply(SMeal argument) {
                return argument.toMeal(scriptManager, templateManager);
            }
        });

        Selection surveySelectedElement = selectedElement.accept(new SSelection.Visitor<Selection>() {
            @Override
            public Selection visitMeal(SSelection.SSelectedMeal meal) {
                return meal.toSelectedMeal();
            }

            @Override
            public Selection visitFood(SSelection.SSelectedFood food) {
                return food.toSelectedFood();
            }

            @Override
            public Selection visitNothing(SSelection.SEmptySelection selection) {
                return selection.toEmptySelection();
            }
        });

        return new Survey(surveyMeals, surveySelectedElement, startTime, flags, customData);
    }
}
