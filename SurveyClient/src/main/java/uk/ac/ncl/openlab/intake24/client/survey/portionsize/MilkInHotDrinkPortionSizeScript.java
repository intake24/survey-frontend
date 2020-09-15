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

package uk.ac.ncl.openlab.intake24.client.survey.portionsize;


import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PMap;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodData;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.StandardUnitPrompt;

import static uk.ac.ncl.openlab.intake24.client.survey.PromptUtil.map;
import static uk.ac.ncl.openlab.intake24.client.survey.PromptUtil.withBackLink;
import static uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptUtil.done;


public class MilkInHotDrinkPortionSizeScript implements PortionSizeScript {
    public static final String name = "milk-in-a-hot-drink";

    public static final String MILK_PART_INDEX_KEY = "milkPartIndex";

    public static final String MILK_VOLUME_KEY = "milkVolumePercentage";

    private final static PromptMessages messages = GWT.create(PromptMessages.class);

    private final PVector<StandardUnitDef> percentages;

    public MilkInHotDrinkPortionSizeScript(PVector<StandardUnitDef> percentages) {
        this.percentages = percentages;
    }

    @Override
    public Option<SimplePrompt<UpdateFunc>> nextPrompt(PMap<String, String> data, final FoodData foodData) {
        if (!(data.containsKey(MILK_VOLUME_KEY) && data.containsKey(MILK_PART_INDEX_KEY))) {
            SimplePrompt<Integer> prompt = new StandardUnitPrompt(
                    SafeHtmlUtils.fromSafeConstant(messages.milkInHotDrink_promptText(foodData.description().toLowerCase())),
                    messages.milkInHotDrink_confirmButtonLabel(),
                    percentages, argument -> argument.name);

            return Option.some(withBackLink(map(prompt,
                    index -> new UpdateFunc()
                            .setField(MILK_PART_INDEX_KEY, Integer.toString(index))
                            .setField(MILK_VOLUME_KEY, Double.toString(percentages.get(index).weight)))));
        } else
            return done();
    }
}
