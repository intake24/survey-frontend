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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import uk.ac.ncl.openlab.intake24.client.api.foods.AsServedSet;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodDataService;
import uk.ac.ncl.openlab.intake24.client.api.foods.SImageMap;

import java.util.ArrayList;
import java.util.List;

public class CerealPortionSizeScriptLoader implements PortionSizeScriptLoader {

    public static final String bowlImageMap = "gbowl";

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {
        final String cerealType = data.get("type");

        FoodDataService.INSTANCE.getImageMap(bowlImageMap, new MethodCallback<SImageMap>() {

            @Override
            public void onFailure(Method method, Throwable exception) {
                onComplete.onFailure(exception);
            }

            @Override
            public void onSuccess(Method method, SImageMap imageMap) {
                final ArrayList<String> ids = new ArrayList<String>();

                for (String bowl : CerealPortionSizeScript.bowlCodes) {
                    ids.add("cereal_" + cerealType + bowl);
                    ids.add("cereal_" + cerealType + bowl + "_leftovers");
                }

                FoodDataService.INSTANCE.getAsServedSets(ids, new MethodCallback<List<AsServedSet>>() {

                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        onComplete.onFailure(exception);
                    }

                    @Override
                    public void onSuccess(Method method, List<AsServedSet> response) {
                        PMap<String, AsServedSet> defs = HashTreePMap.empty();

                        for (int i = 0; i < ids.size(); i++)
                            defs = defs.plus(ids.get(i), response.get(i));

                        onComplete.onSuccess(new CerealPortionSizeScript(imageMap.toImageMap(), defs));
                    }
                });
            }
        });
    }
}