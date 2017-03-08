/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.workcraft.gwt.shared.client.CollectionUtils.*;

public class RuleBasedSelectionManager implements SelectionManager {
    private final PVector<SelectionRule> sortedRules;

    public RuleBasedSelectionManager(PVector<WithPriority<SelectionRule>> rules) {
        this.sortedRules = map(sort(rules, WithPriority.<SelectionRule>comparator()), new Function1<WithPriority<SelectionRule>, SelectionRule>() {
            @Override
            public SelectionRule apply(WithPriority<SelectionRule> argument) {
                return argument.value;
            }
        });
    }

    @Override
    public Option<Selection> nextSelection(final Survey state) {

        PVector<Selection> selections = flattenOption(map(sortedRules, new Function1<SelectionRule, Option<Selection>>() {
            @Override
            public Option<Selection> apply(SelectionRule argument) {
                return argument.apply(state);
            }
        }));

        PVector<SelectionRule> applicableRules = filter(sortedRules, new Function1<SelectionRule, Boolean>() {
            @Override
            public Boolean apply(SelectionRule argument) {
                return !argument.apply(state).isEmpty();
            }
        });

        Logger log = Logger.getLogger("DefaultSelectionManager");

        log.log(Level.INFO, "Choosing next selection");
        log.log(Level.INFO, "Applicable rules: " + applicableRules);


        if (selections.isEmpty()) {
            log.log(Level.INFO, "No more selections available");
            return new Option.None<Selection>();
        } else {
            log.log(Level.INFO, "Chosen selection: " + selections.get(0));
            return new Option.Some<Selection>(selections.get(0));
        }
    }
}