/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.ShepherdTour;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestion;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public abstract class AbstractMultipleChoicePrompt<T> implements SimplePrompt<T> {
    protected final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    protected final SafeHtml promptText;
    private final String promptType;
    protected final PVector<MultipleChoiceQuestionOption> options;
    private final String continueLabel;

    protected AbstractMultipleChoicePrompt(SafeHtml promptText, String promptType, PVector<MultipleChoiceQuestionOption> options, String continueLabel) {
        this.promptText = promptText;
        this.promptType = promptType;
        this.options = options;
        this.continueLabel = continueLabel;
    }

    protected abstract PVector<ShepherdTour.Step> createTour();

    protected abstract MultipleChoiceQuestion<T> createQuestion();

    @Override
    public FlowPanel getInterface(final Callback1<T> onComplete) {
        final FlowPanel content = new FlowPanel();

        final MultipleChoiceQuestion question = createQuestion();
        Button helpButton = ShepherdTour.createTourButton(createTour(), promptType);

        helpButton.getElement().getStyle().setFloat(com.google.gwt.dom.client.Style.Float.RIGHT);
        question.promptPanel.insert(helpButton, 0);

        content.add(question);

        Button continueButton = WidgetFactory.createGreenButton(continueLabel, "continueButton", event -> {
            question.getAnswer().accept(new Option.SideEffectVisitor<T>() {
                @Override
                public void visitSome(T answer) {
                    onComplete.call(answer);
                }

                @Override
                public void visitNone() {

                }
            });
        });

        continueButton.getElement().setId("intake24-mcq-continue-button");

        content.add(continueButton);

        ShepherdTour.makeShepherdTarget(question.promptPanel, question.optionList, continueButton);

        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }
}