/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import org.workcraft.gwt.shared.client.Callback;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.logging.Logger;

/**
 * Manages client-side survey serialisation, cross-session persistence and
 * history.
 */
public class StateManager {
    private Survey currentState;
    private int historyEventCounter = 0;

    private final Logger log = Logger.getLogger(StateManager.class.getName());

    public final String versionId;
    public final String schemeId;

    public Survey getCurrentState() {
        return currentState;
    }

    public void makeHistoryEntry() {
        // log.info("Making history entry #" + historyEventCounter);
        // new RuntimeException().printStackTrace();
        // log.info(SurveyXmlSerialiser.toXml(currentState));

        History.newItem(Integer.toString(historyEventCounter), false);
        StateManagerUtil.setHistoryState(AuthCache.getCurrentUserKey(), historyEventCounter, currentState);
        historyEventCounter++;
    }

    public void updateState(Survey newState, boolean makeHistoryEntry) {
        currentState = newState;

        if (makeHistoryEntry) {
            log.fine("Making history entry");
            makeHistoryEntry();
        }

        StateManagerUtil.setLatestState(AuthCache.getCurrentUserKey(), currentState, schemeId, versionId);
        log.fine("Updated latest state");

        // updateUi.call(newState);
    }

    public StateManager(Survey initialState, String schemeId, String versionId, final Callback showNextPage,
                        final PortionSizeScriptManager scriptManager) {
        this.schemeId = schemeId;
        this.versionId = versionId;
        this.currentState = initialState;

        log.fine("Making initial history entry");
        makeHistoryEntry();

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {

                try {
                    final int state_id = Integer.parseInt(event.getValue());

                    StateManagerUtil.getHistoryState(AuthCache.getCurrentUserKey(), state_id, scriptManager)
                            .accept(new Option.SideEffectVisitor<Survey>() {
                                @Override
                                public void visitSome(Survey item) {
                                    log.fine("Switching to historical state #" + state_id);
                                    updateState(item, false);
                                    showNextPage.call();
                                }

                                @Override
                                public void visitNone() {
                                    log.warning("Failed to load historical state, keeping current state");
                                }
                            });
                } catch (NumberFormatException e) {
                    // Ignore malformed state ids
                }
            }
        });
    }
}
