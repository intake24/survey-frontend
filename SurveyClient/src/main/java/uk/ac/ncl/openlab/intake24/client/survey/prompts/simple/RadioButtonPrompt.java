/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.ShepherdTour;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestion;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionAnswer;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.RadioButtonQuestion;

public class RadioButtonPrompt extends AbstractMultipleChoicePrompt<MultipleChoiceQuestionAnswer> {

    private final String radioGroupId;

    public RadioButtonPrompt(SafeHtml promptText, String promptType, PVector<MultipleChoiceQuestionOption> options, String continueLabel, String radioGroupId) {
        super(promptText, promptType, options, continueLabel);
        this.radioGroupId = radioGroupId;
    }

    @Override
    protected PVector<ShepherdTour.Step> createTour() {
        return TreePVector
                .<ShepherdTour.Step>empty()
                .plus(new ShepherdTour.Step("prompt", "#intake24-mcq-prompt", helpMessages.multipleChoice_questionTitle(), helpMessages.multipleChoice_questionDescription()))
                .plus(new ShepherdTour.Step("radioButtons", "#intake24-mcq-options", helpMessages.multipleChoice_choicesTitle(), helpMessages.multipleChoice_choicesDescription(), false))
                .plus(new ShepherdTour.Step("continueButton", "#intake24-mcq-continue-button", helpMessages.multipleChoice_continueButtonTitle(), helpMessages.multipleChoice_continueButtonDescription(), false));
    }

    @Override
    protected MultipleChoiceQuestion<MultipleChoiceQuestionAnswer> createQuestion() {
        return new RadioButtonQuestion(promptText, options, radioGroupId);
    }

}