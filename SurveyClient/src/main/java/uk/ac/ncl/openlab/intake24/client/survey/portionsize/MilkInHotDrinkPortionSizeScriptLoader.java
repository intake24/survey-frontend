/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.portionsize;

import org.pcollections.PMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.pcollections.PVector;

public class MilkInHotDrinkPortionSizeScriptLoader implements PortionSizeScriptLoader {

    private final PVector<StandardUnitDef> percentages;

    public MilkInHotDrinkPortionSizeScriptLoader(PVector<StandardUnitDef> percentages) {
        this.percentages = percentages;
    }

    @Override
    public void loadResources(final PMap<String, String> data, final AsyncCallback<PortionSizeScript> onComplete) {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                onComplete.onSuccess(new MilkInHotDrinkPortionSizeScript(percentages));
            }
        });
    }
}
