/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.*;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SelectDayQuestion extends Composite {
    final public ListBox select;
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
        if (select.getSelectedValue().isEmpty()) {
            if (required)
                showWarning();

            return Option.none();
        } else
            return Option.some(select.getSelectedValue());
    }

    public SelectDayQuestion(SafeHtml promptText) {
        FlowPanel contents = new FlowPanel();
        contents.addStyleName("intake24-text-box-question");
        contents.add(WidgetFactory.createPromptPanel(promptText));

        warningDiv = new FlowPanel();
        warningDiv.addStyleName("intake24-text-box-question-warning");

        FlowPanel inputDiv = new FlowPanel();
        Label label = new Label("ENTER number of days");
        label.addStyleName("intake24-input-label");

        select = new ListBox();

        List<Integer> days = IntStream.rangeClosed(0, 31).boxed().collect(Collectors.toList());

        for (Integer day : days) {
            select.addItem(day.toString());
        }

        select.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (!select.getSelectedValue().isEmpty())
                    clearWarning();
            }
        });

        inputDiv.add(label);
        inputDiv.add(select);

        contents.add(warningDiv);
        contents.add(inputDiv);

        initWidget(contents);
    }
}
