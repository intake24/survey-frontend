package uk.ac.ncl.openlab.intake24.client.survey.scheme.nz;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.Diet;

public class AskAboutDiet extends Diet {

    public AskAboutDiet(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options) {
        super(promptText, options);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutDiet(SafeHtmlUtils.fromSafeConstant(
                "<p>Are you currently trying to do any of the following?</p>"),
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Lose weight"))
                        .plus(new MultipleChoiceQuestionOption("Stay the same weight"))
                        .plus(new MultipleChoiceQuestionOption("Gain weight"))
                        .plus(new MultipleChoiceQuestionOption("No – not trying to do anything about my weight"))
                        .plus(new MultipleChoiceQuestionOption("Don’t know"))
        ), priority);
    }
}
