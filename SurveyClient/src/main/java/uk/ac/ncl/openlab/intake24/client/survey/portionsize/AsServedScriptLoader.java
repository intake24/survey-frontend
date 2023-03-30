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

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.PMap;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.foods.AsServedSet;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodData;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodDataService;

public class AsServedScriptLoader implements PortionSizeScriptLoader {

    private final boolean leftovers;

    public AsServedScriptLoader(boolean leftovers) {
        this.leftovers = leftovers;
    }

    public final static String SERVING_SET_KEY = "serving-image-set";
    public final static String LEFTOVERS_SET_KEY = "leftovers-image-set";

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {

        FoodDataService.INSTANCE.getAsServedSet(data.get(SERVING_SET_KEY), new MethodCallback<AsServedSet>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                onComplete.onFailure(exception);
            }

            @Override
            public void onSuccess(Method method, AsServedSet servingSet) {
                if (data.containsKey(LEFTOVERS_SET_KEY))
                    FoodDataService.INSTANCE.getAsServedSet(data.get(LEFTOVERS_SET_KEY), new MethodCallback<AsServedSet>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            onComplete.onFailure(exception);
                        }

                        @Override
                        public void onSuccess(Method method, AsServedSet leftoversSet) {
                            onComplete.onSuccess(new AsServedScript(servingSet, Option.some(leftoversSet), leftovers));
                        }
                    });
                else
                    onComplete.onSuccess(new AsServedScript(servingSet, Option.none(), leftovers));

            }
        });
    }
}
