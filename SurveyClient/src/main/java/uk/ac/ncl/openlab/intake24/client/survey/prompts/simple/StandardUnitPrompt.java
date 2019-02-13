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
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.StandardUnitDef;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionAnswer;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.RadioButtonQuestion;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.List;

public class StandardUnitPrompt implements SimplePrompt<Integer> {
    final private SafeHtml promptText;
    final private List<StandardUnitDef> units;
    final private String acceptText;
    private final Function1<StandardUnitDef, String> label;

    public StandardUnitPrompt(SafeHtml promptText, String acceptText, List<StandardUnitDef> units, Function1<StandardUnitDef, String> label) {
        this.promptText = promptText;
        this.acceptText = acceptText;
        this.units = units;
        this.label = label;
    }

    @Override
    public FlowPanel getInterface(final Callback1<Integer> onComplete) {
        final FlowPanel content = new FlowPanel();
        content.addStyleName("intake24-multiple-choice-question");

        PVector<MultipleChoiceQuestionOption> choices = TreePVector.empty();

        for (StandardUnitDef def : units)
            choices = choices.plus(new MultipleChoiceQuestionOption(label.apply(def)));

        final RadioButtonQuestion question = new RadioButtonQuestion(promptText, choices, "standard-unit-choice");

        question.selectFirst();

        Button accept = WidgetFactory.createGreenButton(acceptText, "standardUnitAcceptButton", event -> {
            question.getAnswer().accept(new Option.SideEffectVisitor<MultipleChoiceQuestionAnswer>() {
                @Override
                public void visitSome(MultipleChoiceQuestionAnswer answer) {
                    onComplete.call(answer.index);
                }

                @Override
                public void visitNone() {

                }
            });
        });

        content.add(question);
        content.add(WidgetFactory.createButtonsPanel(accept));

        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }
}