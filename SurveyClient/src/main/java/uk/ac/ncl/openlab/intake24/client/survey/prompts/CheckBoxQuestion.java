/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.*;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.BrowserConsole;
import uk.ac.ncl.openlab.intake24.client.UnorderedList;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxQuestion extends Composite {

    final private ArrayList<CheckBox> checkBoxes;
    final private FlowPanel contents;

    final public FlowPanel promptPanel;
    final public UnorderedList<Widget> choiceList;

    private boolean hasOtherOption = false;
    private CheckBox otherOption = null;
    private TextBox otherBox = null;

    public List<String> getChoices() {
        ArrayList<String> result = new ArrayList<>();

        for (CheckBox cb : checkBoxes) {
            if (cb.getValue())
                result.add(cb.getFormValue());
        }

        if (hasOtherOption && otherOption.getValue())
            result.add(otherBox.getText());

        return result;
    }

    public CheckBoxQuestion(SafeHtml promptText, PVector<String> choices, String groupId, Option<String> otherOptionName) {

        choiceList = new UnorderedList<>();
        choiceList.getElement().setId("intake24-checklist-choices");

        checkBoxes = new ArrayList<>();

        for (String option : choices) {
            CheckBox checkBox = new CheckBox(SafeHtmlUtils.htmlEscape(option));
            checkBox.setFormValue(option);
            checkBoxes.add(checkBox);
            choiceList.addItem(checkBox);
        }

        if (!otherOptionName.isEmpty()) {
            hasOtherOption = true;

            otherOption = new CheckBox(SafeHtmlUtils.htmlEscape(otherOptionName.getOrDie()) + ": ");
            FlowPanel otherPanel = new FlowPanel();

            otherPanel.add(otherOption);
            otherBox = new TextBox();
            otherPanel.add(otherBox);
            choiceList.addItem(otherPanel);

            otherBox.addFocusHandler(event -> otherOption.setValue(true));
        }

        contents = new FlowPanel();
        contents.addStyleName("intake24-radio-button-question");

        promptPanel = WidgetFactory.createPromptPanel(promptText);
        promptPanel.getElement().setId("intake24-checklist-question");
        contents.add(promptPanel);

        contents.add(choiceList);

        initWidget(contents);
    }
}