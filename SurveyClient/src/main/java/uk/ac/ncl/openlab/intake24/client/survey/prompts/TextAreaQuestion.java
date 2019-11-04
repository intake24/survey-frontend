/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.*;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class TextAreaQuestion extends Composite {
    final public TextArea textArea;
    final private FlowPanel warningDiv;

    public void clearWarning() {
        warningDiv.clear();
    }

    public void showWarning() {
        warningDiv.clear();
        warningDiv.add(new Label("Please answer this question before continuing"));
        warningDiv.getElement().scrollIntoView();
    }

    public Option<String> getAnswer() {
        return getAnswer(false);
    }

    public Option<String> getAnswer(boolean required) {
        if (textArea.getValue().isEmpty()) {
            if (required)
                showWarning();

            return Option.none();
        } else
            return Option.some(textArea.getValue());
    }

    public TextAreaQuestion(SafeHtml promptText) {
        FlowPanel contents = new FlowPanel();
        contents.addStyleName("intake24-text-box-question");
        contents.add(WidgetFactory.createPromptPanel(promptText));

        warningDiv = new FlowPanel();
        warningDiv.addStyleName("intake24-text-box-question-warning");

        textArea = new TextArea();

        textArea.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (!textArea.getValue().isEmpty())
                    clearWarning();
            }
        });

        contents.add(warningDiv);
        contents.add(textArea);

        initWidget(contents);
    }
}