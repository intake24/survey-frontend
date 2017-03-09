/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import uk.ac.ncl.openlab.intake24.client.survey.CompletedPortionSize;

import java.util.HashMap;
import java.util.Map;

public class SCompletedPortionSize {
    @JsonProperty
    public final String scriptName;
    @JsonProperty
    public PMap<String, String> data;

    @JsonCreator
    public SCompletedPortionSize(@JsonProperty("scriptName") String scriptName, @JsonProperty("data") Map<String, String> data) {
        this.scriptName = scriptName;
        this.data = HashTreePMap.from(data);
    }

    public SCompletedPortionSize(CompletedPortionSize completedPortionSize) {
        this(completedPortionSize.scriptName, completedPortionSize.data);
    }

    public CompletedPortionSize toCompletedPortionSize() {
        // TODO: to support GWT serialisation, remove at some point
        HashMap<String, String> hmdata = new HashMap<String, String>();
        hmdata.putAll(this.data);

        return new CompletedPortionSize(scriptName, hmdata);
    }
}
