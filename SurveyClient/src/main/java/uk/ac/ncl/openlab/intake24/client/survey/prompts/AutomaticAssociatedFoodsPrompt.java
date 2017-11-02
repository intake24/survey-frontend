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

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.*;
import uk.ac.ncl.openlab.intake24.client.GoogleAnalytics;
import uk.ac.ncl.openlab.intake24.client.LoadingPanel;
import uk.ac.ncl.openlab.intake24.client.api.foods.*;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSize;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.*;

public class AutomaticAssociatedFoodsPrompt implements Prompt<Meal, MealOperation> {
    private final static PromptMessages messages = PromptMessages.Util.getInstance();
    private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final Meal meal;

    private PVector<ShepherdTour.Step> tour;

    private FoodBrowser foodBrowser;
    private boolean isInBrowserMode = false;
    private final String locale;

    public AutomaticAssociatedFoodsPrompt(final String locale, final Meal meal) {
        this.locale = locale;
        this.meal = meal;
    }

    @Override
    public SurveyStageInterface getInterface(final Callback1<MealOperation> onComplete,
                                             Callback1<Function1<Meal, Meal>> updateIntermediateState) {
        final FlowPanel content = new FlowPanel();
        PromptUtil.addBackLink(content);

        final FlowPanel promptPanel = WidgetFactory.createPromptPanel(SafeHtmlUtils.fromSafeConstant(messages.assocFoods_automaticPrompt(SafeHtmlUtils.htmlEscape(meal.name.toLowerCase()))));

        content.add(promptPanel);

        final LoadingPanel loading = new LoadingPanel(messages.foodBrowser_loadingMessage());

        content.add(loading);

        ArrayList<String> foodCodes = new ArrayList<>();

        for (FoodEntry e : meal.foods) {
            if (e.isEncoded())
                foodCodes.add(e.asEncoded().data.code);
        }

        FoodDataService.INSTANCE.getAutomaticAssociatedFoods(locale, foodCodes, new MethodCallback<AutomaticAssociatedFoods>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                content.remove(loading);
                content.add(WidgetFactory.createDefaultErrorMessage());
            }

            @Override
            public void onSuccess(Method method, AutomaticAssociatedFoods response) {
                content.remove(loading);

                final List<CheckBox> checkBoxes = new ArrayList<>();
                final Map<CheckBox, RawFood> foodMap = new LinkedHashMap<>();

                for (CategoryHeader category : response.categories) {
                    CheckBox cb = new CheckBox(SafeHtmlUtils.htmlEscape(category.description()));

                    RawFood f = new RawFood(FoodLink.newUnlinked(), category.description(), HashTreePSet.<String>empty(),
                            HashTreePMap.<String, String>empty().plus(RawFood.KEY_BROWSE_CATEGORY_INSTEAD_OF_LOOKUP, category.code));

                    content.add(cb);

                    checkBoxes.add(cb);
                    foodMap.put(cb, f);
                }

                Button continueButton = WidgetFactory.createGreenButton("Continue", "continue-button", new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent clickEvent) {
                        onComplete.call(MealOperation.update(
                                m -> {
                                    List<FoodEntry> newFoodEntries = new ArrayList<>();

                                    for (CheckBox cb : checkBoxes) {
                                        if (cb.getValue()) {
                                            newFoodEntries.add(foodMap.get(cb));
                                        }
                                    }

                                    return m.withFoods(m.foods.plusAll(newFoodEntries)).markAssociatedFoodsComplete();
                                }));
                    }
                });

                content.add(WidgetFactory.createButtonsPanel(continueButton));
            }
        });


        return new SurveyStageInterface.Aligned(content, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP,
                SurveyStageInterface.DEFAULT_OPTIONS, AutomaticAssociatedFoodsPrompt.class.getSimpleName());
    }

    public PVector<ShepherdTour.Step> getShepherdTourSteps() {
        if (isInBrowserMode)
            return foodBrowser.getShepherdTourSteps();
        else
            return tour;
    }

    @Override
    public String toString() {
        return "Food reminder prompt";
    }
}