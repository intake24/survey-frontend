/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Function2;

public class IntakeStateManager {
    private final PVector<Function2<Survey, Survey, Survey>> stateTransitionFilters;
    private final StateManager baseManager;
    private final NavigationPanel navPanel;

    public Survey getCurrentState() {
        return baseManager.getCurrentState();
    }

    public void updateState(Survey newState, boolean makeHistoryEntry) {
        Survey s0 = getCurrentState();
        Survey s1 = newState;

        for (Function2<Survey, Survey, Survey> f : stateTransitionFilters)
            s1 = f.apply(s0, s1);

        baseManager.updateState(s1, makeHistoryEntry);
        navPanel.stateChanged(s1);
    }

    public IntakeStateManager(StateManager baseManager, NavigationPanel navPanel) {
        this.baseManager = baseManager;
        this.navPanel = navPanel;
        this.stateTransitionFilters = TreePVector.<Function2<Survey, Survey, Survey>>empty()
                .plus(new HandleDeleted())
                .plus(new SaveSameAsBefore(baseManager.schemeId, baseManager.versionId))
                .plus(new ResetSelectionOnFreeEntryComplete())
                .plus(new ResetSelectionOnPortionSizeComplete());
    }
}
