/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.BrowserConsole;
import uk.ac.ncl.openlab.intake24.client.survey.ShepherdTour;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.CheckBoxQuestion;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.List;

public class CheckBoxPrompt implements SimplePrompt<List<String>> {
    private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final static PVector<ShepherdTour.Step> tour = TreePVector
            .<ShepherdTour.Step>empty()
            .plus(new ShepherdTour.Step("prompt", "#intake24-checklist-question", helpMessages.checklist_questionTitle(), helpMessages.checklist_questionDescription()))
            .plus(new ShepherdTour.Step("choices", "#intake24-checklist-choices", helpMessages.checklist_choicesTitle(), helpMessages.checklist_choicesDescription(), false))
            .plus(new ShepherdTour.Step("continueButton", "#intake24-checklist-continue-button", helpMessages.checklist_continueButtonTitle(), helpMessages.checklist_continueButtonDescription(), false));

    private final SafeHtml promptText;
    private final PVector<String> options;
    private final String continueLabel;
    private final String buttonGroupId;
    private final Option<String> otherOption;
    private final String promptType;

    public CheckBoxPrompt(SafeHtml promptText, String promptType, PVector<String> options, String continueLabel, String buttonGroupId, Option<String> otherOption) {
        this.promptText = promptText;
        this.options = options;
        this.continueLabel = continueLabel;
        this.buttonGroupId = buttonGroupId;
        this.otherOption = otherOption;
        this.promptType = promptType;
    }

    @Override
    public FlowPanel getInterface(final Callback1<List<String>> onComplete) {
        final FlowPanel content = new FlowPanel();

        BrowserConsole.log("1");

        final CheckBoxQuestion checkBoxBlock = new CheckBoxQuestion(promptText, options, buttonGroupId, otherOption);

        BrowserConsole.log("2");

        Button helpButton = ShepherdTour.createTourButton(tour, promptType);
        helpButton.getElement().getStyle().setFloat(com.google.gwt.dom.client.Style.Float.RIGHT);
        checkBoxBlock.promptPanel.insert(helpButton, 0);

        content.add(checkBoxBlock);

        Button continueButton = WidgetFactory.createButton(continueLabel, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(checkBoxBlock.getChoices());
            }
        });

        continueButton.getElement().setId("intake24-checklist-continue-button");

        content.add(continueButton);

        ShepherdTour.makeShepherdTarget(checkBoxBlock.promptPanel, checkBoxBlock.choiceList, continueButton);

        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }
}