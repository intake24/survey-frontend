package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.Survey;
import uk.ac.ncl.openlab.intake24.client.survey.SurveyOperation;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.CookingOil;

public class AskAboutCookingOil extends CookingOil {

    public AskAboutCookingOil(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options) {
        super(promptText, options);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutCookingOil(SafeHtmlUtils.fromSafeConstant(
                "<p>Which type of cooking fat/oil did you use most often when you completed this recall?</p>"),
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Sunflower oil"))
                        .plus(new MultipleChoiceQuestionOption("Vegetable oil (rapeseed)"))
                        .plus(new MultipleChoiceQuestionOption("Corn oil"))
                        .plus(new MultipleChoiceQuestionOption("Palm oil"))
                        .plus(new MultipleChoiceQuestionOption("Coconut oil"))
                        .plus(new MultipleChoiceQuestionOption("Olive oil"))
                        .plus(new MultipleChoiceQuestionOption("Butter"))
                        .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Did not use"))
        ), priority);
    }
}
