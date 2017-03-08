/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.FlowPanel;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.YesNoQuestion;

public class YesNoPrompt implements SimplePrompt<Boolean> {
    private final SafeHtml promptText;
    private final String yesText;
    private final String noText;

    public YesNoPrompt(SafeHtml promptText, String yesText, String noText) {
        this.promptText = promptText;
        this.yesText = yesText;
        this.noText = noText;
    }

    @Override
    public FlowPanel getInterface(final Callback1<Boolean> onComplete) {
        final FlowPanel content = new FlowPanel();

        content.add(new YesNoQuestion(promptText, yesText, noText, new YesNoQuestion.ResultHandler() {
            @Override
            public void handleYes() {
                onComplete.call(true);
            }

            @Override
            public void handleNo() {
                onComplete.call(false);
            }
        }));

        return content;
    }
}