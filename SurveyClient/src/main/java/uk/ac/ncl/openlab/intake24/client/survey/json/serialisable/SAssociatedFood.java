/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ncl.openlab.intake24.client.survey.AssociatedFood;

public class SAssociatedFood {
    @JsonProperty
    public final String code;
    @JsonProperty
    public final boolean isCategoryCode;
    @JsonProperty
    public final String text;
    @JsonProperty
    public final boolean linkAsMain;
    @JsonProperty
    public final String genericName;

    @JsonCreator
    public SAssociatedFood(@JsonProperty("code") String code, @JsonProperty("isCategoryCode") boolean isCategoryCode, @JsonProperty("text") String text, @JsonProperty("linkAsMain") boolean linkAsMain,
                           @JsonProperty("genericName") String genericName) {
        this.isCategoryCode = isCategoryCode;
        this.code = code;
        this.text = text;
        this.linkAsMain = linkAsMain;
        this.genericName = genericName;
    }

    public SAssociatedFood(AssociatedFood prompt) {
        this(prompt.code, prompt.isCategoryCode, prompt.text, prompt.linkAsMain, prompt.genericName);
    }

    public AssociatedFood toFoodPrompt() {
        return new AssociatedFood(code, isCategoryCode, text, linkAsMain, genericName);
    }
}
