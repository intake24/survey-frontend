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

import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.survey.SurveyParameters;
import uk.ac.ncl.openlab.intake24.client.survey.*;

public class DefaultScheme extends BasicScheme {

    public static final String ID = "default";
    public SurveyParameters surveyParameters;

    public DefaultScheme(SurveyParameters surveyParameters, String locale, final SurveyInterfaceManager interfaceManager) {
        super(locale, interfaceManager);
        this.surveyParameters = surveyParameters;
    }

    private IntakeSurvey cachedSurveyPage = null;

    @Override
    public void showNextPage() {
        final Survey state = stateManager.getCurrentState();
        // Logger log = Logger.getLogger("showNextPage");
        // log.info(SurveyXmlSerialiser.toXml(state));

        if (!state.flags.contains(WelcomePage.FLAG_WELCOME_PAGE_SHOWN)) {

            this.surveyParameters.description.accept(new Option.SideEffectVisitor<String>() {
                @Override
                public void visitSome(String description) {
                    interfaceManager.show(new WelcomePage(description, state));
                }

                @Override
                public void visitNone() {
                    interfaceManager.show(new WelcomePage(SurveyMessages.INSTANCE.welcomePage_welcomeText(), state));
                }
            });

        } else if (!state.completionConfirmed()) {
            if (cachedSurveyPage == null)
                cachedSurveyPage = new IntakeSurvey(stateManager, defaultPromptManager, defaultSelectionManager, defaultScriptManager);
            interfaceManager.show(cachedSurveyPage);
        } else {
            interfaceManager.show(new FlatFinalPage(SurveyMessages.INSTANCE.finalPage_text(), postProcess(state, basicPostProcess), log.log));
        }
    }

    @Override
    public String getDataVersion() {
        return "1";
    }

    @Override
    public String getSchemeId() {
        return ID;
    }

}