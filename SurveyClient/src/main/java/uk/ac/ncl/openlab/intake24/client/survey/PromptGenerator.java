/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;

public interface PromptGenerator<T, Op> {
    Option<WithPriority<Prompt<T, Op>>> nextPrompt(final T state, final Selection selection, final PSet<String> surveyFlags);
}
