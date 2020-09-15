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
import uk.ac.ncl.openlab.intake24.client.survey.prompts.CheckListQuestion;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestion;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionAnswer;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;

import java.util.List;

public class CheckListPrompt extends AbstractMultipleChoicePrompt<List<MultipleChoiceQuestionAnswer>> {

    private boolean required = true;

    public CheckListPrompt(SafeHtml promptText, String promptType, PVector<MultipleChoiceQuestionOption> options, String continueLabel, Boolean required) {
        super(promptText, promptType, options, continueLabel);
        this.required = required;
    }

    public CheckListPrompt(SafeHtml promptText, String promptType, PVector<MultipleChoiceQuestionOption> options, String continueLabel) {
        super(promptText, promptType, options, continueLabel);
    }

    @Override
    protected PVector<ShepherdTour.Step> createTour() {
        return TreePVector
                .<ShepherdTour.Step>empty()
                .plus(new ShepherdTour.Step("prompt", "#intake24-mcq-prompt", helpMessages.checklist_questionTitle(), helpMessages.checklist_questionDescription()))
                .plus(new ShepherdTour.Step("radioButtons", "#intake24-mcq-options", helpMessages.checklist_choicesTitle(), helpMessages.checklist_choicesDescription(), false))
                .plus(new ShepherdTour.Step("continueButton", "#intake24-mcq-continue-button", helpMessages.checklist_continueButtonTitle(), helpMessages.checklist_continueButtonDescription(), false));
    }

    @Override
    protected MultipleChoiceQuestion<List<MultipleChoiceQuestionAnswer>> createQuestion() {
        return new CheckListQuestion(promptText, options, required);
    }

}
