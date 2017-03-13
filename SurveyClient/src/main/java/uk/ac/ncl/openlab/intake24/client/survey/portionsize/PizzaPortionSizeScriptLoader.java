/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.portionsize;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import org.workcraft.gwt.imagemap.shared.ImageMap;
import uk.ac.ncl.openlab.intake24.client.api.foods.FoodDataService;
import uk.ac.ncl.openlab.intake24.client.api.foods.SImageMap;

import java.util.ArrayList;
import java.util.List;


public class PizzaPortionSizeScriptLoader implements PortionSizeScriptLoader {
    public static final String PIZZA_IMAGE_MAP_NAME = "gpizza";
    public static final String PIZZA_THICKNESS_IMAGE_MAP_NAME = "gpthick";
    public static final String SLICE_IMAGE_MAP_PREFIX = "gpiz";
    public static final int PIZZA_TYPES_COUNT = 9;

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {
        ArrayList<String> imageMapNames = new ArrayList<String>();

        for (int i = 0; i < PIZZA_TYPES_COUNT; i++)
            imageMapNames.add(SLICE_IMAGE_MAP_PREFIX + Integer.toString(i + 1));

        imageMapNames.add(PIZZA_IMAGE_MAP_NAME);
        imageMapNames.add(PIZZA_THICKNESS_IMAGE_MAP_NAME);

        FoodDataService.INSTANCE.getImageMaps(imageMapNames, new MethodCallback<List<SImageMap>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                onComplete.onFailure(exception);
            }

            @Override
            public void onSuccess(Method method, List<SImageMap> response) {
                PMap<Integer, ImageMap> sliceMaps = HashTreePMap.empty();

                for (int i = 0; i < PIZZA_TYPES_COUNT; i++)
                    sliceMaps = sliceMaps.plus(i + 1, response.get(i).toImageMap());

                onComplete.onSuccess(new PizzaPortionSizeScript(response.get(PIZZA_TYPES_COUNT).toImageMap(), response.get(PIZZA_TYPES_COUNT + 1).toImageMap(), sliceMaps));

            }
        });
    }
}
