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

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.LoadingPanel;
import uk.ac.ncl.openlab.intake24.client.api.foods.AutomaticAssociatedFoods;
import uk.ac.ncl.openlab.intake24.client.api.foods.CategoryHeader;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodDataService;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodHeader;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.UxEventsHelper;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.Viewport;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods.AutomaticData;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AutomaticAssociatedFoodsPrompt implements Prompt<Meal, MealOperation> {
    private final static PromptMessages messages = PromptMessages.Util.getInstance();
    private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final Meal meal;

    private PVector<ShepherdTour.Step> tour;

    private FoodBrowser foodBrowser;
    private boolean isInBrowserMode = false;
    private final String locale;

    private final Logger logger = Logger.getLogger("AutomaticAssociatedFoodsPrompt");

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

        final ArrayList<FoodHeader> encodedFoods = new ArrayList<>();
        final ArrayList<String> foodCodes = new ArrayList<>();

        for (FoodEntry e : meal.foods) {
            if (e.isEncoded()) {

                EncodedFood ef = e.asEncoded();

                encodedFoods.add(new FoodHeader(ef.data.code, ef.data.localDescription));
                foodCodes.add(ef.data.code);
            }
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
                final Map<CheckBox, CategoryHeader> foodMap = new LinkedHashMap<>();

                List<CategoryHeader> categories = response.categories.stream()
                        .filter(c -> milkIsRerevant(c)).collect(Collectors.toList());

                List<String> codes = categories.stream().map(c -> c.code).collect(Collectors.toList());

                if (!cachedAssociatedFoodsChanged(codes) || codes.size() == 0) {
                    cacheAssociatedFoods(listToJsArray(new ArrayList<>()));
                    onComplete.call(MealOperation.update(m -> m.markAssociatedFoodsComplete()));
                } else {
                    cacheAssociatedFoods(listToJsArray(codes));
                    UxEventsHelper.postAutomaticAssociatedFoodsReceived(new AutomaticData(Viewport.getCurrent(), encodedFoods, response.categories, new ArrayList<>()));
                }

                for (CategoryHeader category : categories) {
                    CheckBox cb = new CheckBox(SafeHtmlUtils.fromString(category.description()));

                    FlowPanel div = new FlowPanel();
                    div.getElement().getStyle().setPaddingBottom(4, Style.Unit.PX);
                    div.add(cb);
                    cb.getElement().getFirstChildElement().getStyle().setMargin(0, Style.Unit.PX);
                    content.add(div);

                    checkBoxes.add(cb);
                    foodMap.put(cb, category);
                }

                Button continueButton = WidgetFactory.createGreenButton("Continue", "continue-button", new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent clickEvent) {

                        ArrayList<String> selectedCategories = new ArrayList<>();

                        for (CheckBox cb : checkBoxes) {
                            if (cb.getValue()) {
                                selectedCategories.add(foodMap.get(cb).code);
                            }
                        }

                        UxEventsHelper.postAutomaticAssociatedFoodsResponse(new AutomaticData(Viewport.getCurrent(), encodedFoods, response.categories, selectedCategories));

                        onComplete.call(MealOperation.update(
                                m -> {
                                    List<FoodEntry> newFoodEntries = new ArrayList<>();

                                    for (CheckBox cb : checkBoxes) {
                                        if (cb.getValue()) {
                                            Optional<FoodEntry> assocFood;
                                            CategoryHeader ch = foodMap.get(cb);
                                            switch (ch.code) {
                                                case SpecialData.FOOD_CODE_MILK_ON_CEREAL:
                                                    assocFood = meal.foods.stream().filter(f -> isCerealWithMilk(f)).findFirst();
                                                    if (assocFood.isPresent()) {
                                                        addFood(newFoodEntries, ch, assocFood);
                                                    }
                                                    break;
                                                case SpecialData.FOOD_CODE_MILK_IN_HOT_DRINK:
                                                    assocFood = meal.foods.stream().filter(f -> isHotDrinkWithMilk(f)).findFirst();
                                                    if (assocFood.isPresent()) {
                                                        addFood(newFoodEntries, ch, assocFood);
                                                    }
                                                    break;
                                                default:
                                                    addFood(newFoodEntries, ch, Optional.empty());
                                                    break;

                                            }
                                        }
                                    }

                                    if (newFoodEntries.size() > 0) {
                                        return m.withFoods(m.foods.plusAll(newFoodEntries));
                                    } else {
                                        cacheAssociatedFoods(listToJsArray(new ArrayList<>()));
                                        return m.withFoods(m.foods.plusAll(newFoodEntries)).markAssociatedFoodsComplete();
                                    }

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

    private void addFood(List<FoodEntry> foodEntries, CategoryHeader ch, Optional<FoodEntry> assocFood) {
        FoodLink fl = assocFood.map(f -> FoodLink.newLinked(f.link.id)).orElse(FoodLink.newUnlinked());
        foodEntries.add(
                new RawFood(fl,
                        ch.description(),
                        HashTreePSet.<String>empty().plus(RawFood.FLAG_DISABLE_SPLIT),
                        HashTreePMap.<String, String>empty().plus(RawFood.KEY_BROWSE_CATEGORY_INSTEAD_OF_LOOKUP,
                                ch.code)
                )
        );
    }

    private boolean isHotDrinkWithMilk(FoodEntry f) {
        EncodedFood enc = f.asEncoded();
        return enc.data.code.equals(SpecialData.FOOD_CODE_COFFEE) ||
                enc.isInCategory(SpecialData.CATEGORY_TEA_CODE);
    }

    private boolean isCerealWithMilk(FoodEntry f) {
        EncodedFood enc = f.asEncoded();
        return enc.isInCategory(SpecialData.CATEGORY_BREAKFAST_CEREALS) &&
                SpecialData.CATEGORIES_CEREAL_NO_MILK.stream().anyMatch(c -> !enc.isInCategory(c));
    }

    private boolean hotDrinkIsPresent() {
        return meal.foods.stream().filter(FoodEntry::isEncoded).anyMatch(this::isHotDrinkWithMilk);
    }

    private boolean cerealIsPresent() {
        return meal.foods.stream().filter(FoodEntry::isEncoded).anyMatch(this::isCerealWithMilk);
    }

    private boolean milkIsRerevant(CategoryHeader categoryHeader) {
        return (!categoryHeader.code.equals(SpecialData.FOOD_CODE_MILK_IN_HOT_DRINK) || hotDrinkIsPresent()) &&
                (!categoryHeader.code.equals(SpecialData.FOOD_CODE_MILK_ON_CEREAL) || cerealIsPresent());
    }

    private final native void cacheAssociatedFoods(JsArrayString l)/*-{
        return $wnd.lastAssociatedFoods = l;
    }-*/;

    private final boolean cachedAssociatedFoodsChanged(List<String> l) {
        return cachedAssociatedFoodsChangedNative(listToJsArray(l));
    }

    private final JsArrayString listToJsArray(List<String> l) {
        JsArrayString jsArray = (JsArrayString) JsArrayString.createArray();
        for (String str : l) {
            jsArray.push(str);
        }
        return jsArray;
    }

    private final native boolean cachedAssociatedFoodsChangedNative(JsArrayString l)/*-{
        var cached = $wnd.lastAssociatedFoods || [];
        if (l.length !== cached.length) {
            return true;
        }
        for (var i = 0; i < l.length; i++) {
            if (cached.indexOf(l[i]) === -1) {
                return true;
            }
        }
        return false;
    }-*/;

}