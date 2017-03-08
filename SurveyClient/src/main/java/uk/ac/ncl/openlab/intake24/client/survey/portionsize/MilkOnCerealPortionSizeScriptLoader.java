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

public class MilkOnCerealPortionSizeScriptLoader implements PortionSizeScriptLoader {
    // private final FoodLookupServiceAsync lookupService = FoodLookupServiceAsync.Util.getInstance();

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {
        final ArrayList<String> milkLevelImageMapNames = new ArrayList<String>();

        for (String bowl : CerealPortionSizeScript.bowlCodes)
            milkLevelImageMapNames.add(MilkOnCerealPortionSizeScript.milkLevelImageMapPrefix + bowl);

        milkLevelImageMapNames.add(CerealPortionSizeScriptLoader.bowlImageMap);

        throw new RuntimeException("Not implemented");

    /* lookupService.getImageMaps(milkLevelImageMapNames, new AsyncCallback<List<ImageMapDefinition>>() {
      @Override
      public void onFailure(Throwable caught) {
        onComplete.onFailure(caught);
      }

      @Override
      public void onSuccess(final List<ImageMapDefinition> imageMapDef) {
        PMap<String, ImageMapDefinition> defs = HashTreePMap.empty();

        for (int i = 0; i < milkLevelImageMapNames.size() - 1; i++)
          defs = defs.plus(milkLevelImageMapNames.get(i), imageMapDef.get(i));

        onComplete.onSuccess(new MilkOnCerealPortionSizeScript(imageMapDef.get(milkLevelImageMapNames.size() - 1), defs));
      }
    });*/
    }
}