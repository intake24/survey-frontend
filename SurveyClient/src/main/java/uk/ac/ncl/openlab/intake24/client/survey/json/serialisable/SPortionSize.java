/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import java.util.Map;

import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import org.workcraft.gwt.shared.client.Option;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSize;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScript;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

public class SPortionSize {
    @JsonProperty
    public final String scriptName;
    @JsonProperty
    public final PMap<String, String> data;

    @JsonCreator
    public SPortionSize(@JsonProperty("scriptName") String scriptName, @JsonProperty("data") Map<String, String> data) {
        this.scriptName = scriptName;
        this.data = HashTreePMap.from(data);
    }

    public SPortionSize(PortionSize portionSize) {
        this(portionSize.scriptName, portionSize.data);
    }

    public PortionSize toPortionSize(PortionSizeScriptManager scriptManager) {
        return new PortionSize(scriptName, data, scriptManager.getInstance(scriptName), Option.<PortionSizeScript>none());
    }
}