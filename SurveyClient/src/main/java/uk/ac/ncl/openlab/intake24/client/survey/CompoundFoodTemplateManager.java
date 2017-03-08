/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.pcollections.PMap;

public class CompoundFoodTemplateManager {
	private final PMap<String, TemplateFoodData> templates;

	public CompoundFoodTemplateManager(PMap<String, TemplateFoodData> templates) {
		this.templates = templates;
	}
	
	public TemplateFoodData getTemplate(String id) {
		return templates.get(id);
	}
}
