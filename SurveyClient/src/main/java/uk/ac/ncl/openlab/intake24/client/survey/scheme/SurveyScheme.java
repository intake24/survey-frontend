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

package uk.ac.ncl.openlab.intake24.client.survey.scheme;

import com.google.gwt.user.client.ui.Anchor;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyParameters;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyInterfaceManager;

import java.util.List;

public interface SurveyScheme {
    String getSchemeId();

    String getDataVersion();

    void showNextPage();

    List<Anchor> navBarLinks();

    static SurveyScheme createScheme(SurveyParameters surveyParameters, final String locale, final SurveyInterfaceManager interfaceManager) {

        switch (surveyParameters.schemeId) {
            case DefaultScheme.ID:
                return new DefaultScheme(surveyParameters, locale, interfaceManager);
            case SHeSJun15.ID:
                return new SHeSJun15(locale, interfaceManager);
            default:
                throw new RuntimeException("Unknown survey scheme: " + surveyParameters.schemeId);
        }
    }
}
