/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.ConfirmCompletionPromptFlexibleRecall;

public class ConfirmCompletionFlexibleRecall implements PromptRule<Survey, SurveyOperation> {

    private final StateManager stateManager;

    public ConfirmCompletionFlexibleRecall(final StateManager sm) {
        stateManager = sm;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (state.completionConfirmed())
            return Option.none();
        else
            return Option.<Prompt<Survey, SurveyOperation>>some(new ConfirmCompletionPromptFlexibleRecall(stateManager));
    }

    @Override
    public String toString() {
        return "Confirm survey completion";
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, StateManager stateManager) {
        return new WithPriority<PromptRule<Survey, SurveyOperation>>(new ConfirmCompletionFlexibleRecall(stateManager), priority);
    }
}