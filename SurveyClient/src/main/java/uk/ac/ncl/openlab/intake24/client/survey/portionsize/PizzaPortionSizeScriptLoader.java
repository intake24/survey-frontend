/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.portionsize;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.pcollections.PMap;

import java.util.ArrayList;


public class PizzaPortionSizeScriptLoader implements PortionSizeScriptLoader {
    public static final String pizzaImageMapName = "gpizza";
    public static final String pizzaThicknessImageMapName = "gpthick";
    public static final String sliceImageMapPrefix = "gpiz";
    public static final int pizzaTypesCount = 9;

    // private final FoodLookupServiceAsync lookupService = FoodLookupServiceAsync.Util.getInstance();

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {
        ArrayList<String> imageMapNames = new ArrayList<String>();

        for (int i = 0; i < pizzaTypesCount; i++)
            imageMapNames.add(sliceImageMapPrefix + Integer.toString(i + 1));

        imageMapNames.add(pizzaImageMapName);
        imageMapNames.add(pizzaThicknessImageMapName);

        throw new RuntimeException("Not implemented");
/*
    lookupService.getImageMaps(imageMapNames, new AsyncCallback<List<ImageMapDefinition>>() {
      @Override
      public void onFailure(Throwable caught) {
        onComplete.onFailure(caught);
      }

      @Override
      public void onSuccess(List<ImageMapDefinition> result) {
        PMap<Integer, ImageMapDefinition> sliceMaps = HashTreePMap.<Integer, ImageMapDefinition>empty();

        for (int i = 0; i < pizzaTypesCount; i++)
          sliceMaps = sliceMaps.plus(i + 1, result.get(i));

        onComplete.onSuccess(new PizzaPortionSizeScript(result.get(pizzaTypesCount), result.get(pizzaTypesCount + 1), sliceMaps));
      }
    });*/
    }
}
