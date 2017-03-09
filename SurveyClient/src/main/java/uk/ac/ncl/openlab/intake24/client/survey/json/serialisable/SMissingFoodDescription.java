/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;
import org.workcraft.gwt.shared.client.Option;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ncl.openlab.intake24.client.survey.MissingFoodDescription;

public class SMissingFoodDescription {
	
	@JsonProperty
	public final Option<String> brand;
	@JsonProperty
	public final Option<String> description;
	@JsonProperty
	public final Option<String> portionSize;
	@JsonProperty
	public final Option<String> leftovers;

	@JsonCreator
	public SMissingFoodDescription(@JsonProperty("brand") Option<String> brand, @JsonProperty("description") Option<String> description,
                                   @JsonProperty("portionSize") Option<String> portionSize, @JsonProperty("leftovers") Option<String> leftovers) {
		this.brand = brand;
		this.description = description;
		this.portionSize = portionSize;
		this.leftovers = leftovers;
	}
	
	public SMissingFoodDescription(MissingFoodDescription missingFoodDescription) {
		this(missingFoodDescription.brand, missingFoodDescription.description, missingFoodDescription.portionSize, missingFoodDescription.leftovers);		
	}
	
	public MissingFoodDescription toMissingFoodDescription() {
		return new MissingFoodDescription(this.brand, this.description, this.portionSize, this.leftovers);
	}
}
