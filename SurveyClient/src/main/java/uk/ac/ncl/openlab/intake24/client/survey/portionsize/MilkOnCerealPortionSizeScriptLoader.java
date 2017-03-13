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

public class MilkOnCerealPortionSizeScriptLoader implements PortionSizeScriptLoader {

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {
        final ArrayList<String> milkLevelImageMapNames = new ArrayList<String>();

        for (String bowl : CerealPortionSizeScript.bowlCodes)
            milkLevelImageMapNames.add(MilkOnCerealPortionSizeScript.milkLevelImageMapPrefix + bowl);

        milkLevelImageMapNames.add(CerealPortionSizeScriptLoader.bowlImageMap);

        FoodDataService.INSTANCE.getImageMaps(milkLevelImageMapNames, new MethodCallback<List<SImageMap>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                onComplete.onFailure(exception);
            }

            @Override
            public void onSuccess(Method method, List<SImageMap> response) {
                PMap<String, ImageMap> defs = HashTreePMap.empty();

                for (int i = 0; i < milkLevelImageMapNames.size() - 1; i++)
                    defs = defs.plus(milkLevelImageMapNames.get(i), response.get(i).toImageMap());

                onComplete.onSuccess(new MilkOnCerealPortionSizeScript(response.get(milkLevelImageMapNames.size() - 1).toImageMap(), defs));
            }
        });
    }
}