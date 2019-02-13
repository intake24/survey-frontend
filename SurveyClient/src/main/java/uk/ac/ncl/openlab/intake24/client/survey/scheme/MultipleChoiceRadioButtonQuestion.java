/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.scheme;


import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Callback2;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.SimpleSurveyStageInterface;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyStage;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionAnswer;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.RadioButtonQuestion;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

public class MultipleChoiceRadioButtonQuestion implements SurveyStage<Survey> {
    final private SafeHtml questionText;
    final private PVector<MultipleChoiceQuestionOption> options;
    final private String acceptText;
    final private String dataField;
    final private Survey state;
    final private Option<String> otherOption;

    public MultipleChoiceRadioButtonQuestion(final Survey state, final SafeHtml questionText, final String acceptText, PVector<String> options, String dataField,
                                             Option<String> otherOption) {
        this.state = state;
        this.questionText = questionText;
        this.acceptText = acceptText;
        this.options = otherOption.accept(new Option.Visitor<String, PVector<MultipleChoiceQuestionOption>>() {
            @Override
            public PVector<MultipleChoiceQuestionOption> visitSome(String otherLabel) {
                return map(options, label -> new MultipleChoiceQuestionOption(label)).plus(new MultipleChoiceQuestionOption(otherLabel));
            }

            @Override
            public PVector<MultipleChoiceQuestionOption> visitNone() {
                return map(options, label -> new MultipleChoiceQuestionOption(label));
            }
        });
        this.dataField = dataField;
        this.otherOption = otherOption;
    }

    @Override
    public SimpleSurveyStageInterface getInterface(final Callback1<Survey> onComplete, Callback2<Survey, Boolean> onIntermediateStateChange) {
        final FlowPanel content = new FlowPanel();
        content.addStyleName("intake24-multiple-choice-question");
        content.addStyleName("intake24-survey-content-container");

        final RadioButtonQuestion question = new RadioButtonQuestion(questionText, options, dataField);

        Button accept = WidgetFactory.createGreenButton(acceptText, "multipleChoiceAcceptButton", event -> {
            Option<MultipleChoiceQuestionAnswer> maybeAnswer = question.getAnswer();

            maybeAnswer.accept(new Option.SideEffectVisitor<MultipleChoiceQuestionAnswer>() {
                @Override
                public void visitSome(MultipleChoiceQuestionAnswer answer) {
                    onComplete.call(state.withData(dataField, answer.details.getOrElse(answer.value)));
                }

                @Override
                public void visitNone() {

                }
            });
        });

        content.add(question);
        content.add(accept);

        return new SimpleSurveyStageInterface(content, MultipleChoiceRadioButtonQuestion.class.getSimpleName());
    }
}