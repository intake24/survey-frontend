/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.scheme;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Callback2;
import org.workcraft.gwt.shared.client.Option;
import org.workcraft.gwt.shared.client.Pair;
import uk.ac.ncl.openlab.intake24.client.survey.SimpleSurveyStageInterface;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyStage;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionAnswer;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.RadioButtonQuestion;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

import java.util.HashMap;
import java.util.Map;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

public class LunchFrequenciesQuestion implements SurveyStage<Survey> {
    final private Survey state;
    final private PVector<MultipleChoiceQuestionOption> frequencyOptions;

    public LunchFrequenciesQuestion(final Survey state, PVector<String> frequencyOptions) {
        this.state = state;
        this.frequencyOptions = map(frequencyOptions, option -> new MultipleChoiceQuestionOption(option));
    }

    @Override
    public SimpleSurveyStageInterface getInterface(final Callback1<Survey> onComplete, Callback2<Survey, Boolean> onIntermediateStateChange) {
        final FlowPanel content = new FlowPanel();
        content.addStyleName("intake24-survey-content-container");

        final RadioButtonQuestion shopFreq = new RadioButtonQuestion(
                SafeHtmlUtils.fromSafeConstant("<p>In a normal school week how often do you go out to the shops for <strong>lunch</strong>?</p>"),
                frequencyOptions, "shopFreq");
        final RadioButtonQuestion packFreq = new RadioButtonQuestion(
                SafeHtmlUtils.fromSafeConstant("<p>In a normal school week how often do you bring in a packed <strong>lunch</strong> from home?</p>"),
                frequencyOptions, "packFreq");
        final RadioButtonQuestion schoolLunchFreq = new RadioButtonQuestion(
                SafeHtmlUtils.fromSafeConstant("<p>In a normal school week how often do you have a school <strong>lunch</strong>?</p>"), frequencyOptions,
                "schoolLunchFreq");
        final RadioButtonQuestion homeFreq = new RadioButtonQuestion(
                SafeHtmlUtils.fromSafeConstant("<p>In a normal school week how often do you go home/to a friend's house for <strong>lunch</strong>?</p>"),
                frequencyOptions, "homeFreq");
        final RadioButtonQuestion skipFreq = new RadioButtonQuestion(
                SafeHtmlUtils.fromSafeConstant("<p>In a normal school week how often do you skip <strong>lunch</strong>?</p>"), frequencyOptions, "skipFreq");
        final RadioButtonQuestion workFreq = new RadioButtonQuestion(
                SafeHtmlUtils.fromSafeConstant("<p>In a normal school week how often do you work through <strong>lunch</strong>?</p>"),
                frequencyOptions, "workFreq");

        content.add(shopFreq);
        content.add(packFreq);
        content.add(schoolLunchFreq);
        content.add(homeFreq);
        content.add(skipFreq);
        content.add(workFreq);

        final Button accept = WidgetFactory.createButton("Continue");

        accept.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                Map<String, Option<MultipleChoiceQuestionAnswer>> answers = new HashMap<>();

                answers.put("shopFreq", shopFreq.getAnswer());
                answers.put("packFreq", packFreq.getAnswer());
                answers.put("schoolLunchFreq", schoolLunchFreq.getAnswer());
                answers.put("homeFreq", homeFreq.getAnswer());
                answers.put("skipFreq", skipFreq.getAnswer());
                answers.put("workFreq", workFreq.getAnswer());

                if (answers.values().stream().anyMatch(answer -> answer.isEmpty()))
                    return;

                PMap<String, String> newData = answers.entrySet().stream()
                        .map(e -> HashTreePMap.singleton(e.getKey(), e.getValue().getOrDie().value))
                        .reduce(HashTreePMap.empty(), (m1, m2) -> m1.plusAll(m2));


                accept.setEnabled(false);
                onComplete.call(state.withData(state.customData.plusAll(newData)));
            }
        });

        content.add(WidgetFactory.createButtonsPanel(accept));

        return new SimpleSurveyStageInterface(content, LunchFrequenciesQuestion.class.getSimpleName());
    }
}