package uk.ac.ncl.openlab.intake24.client.survey.scheme.sab;

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
                "<p>Which type of <strong>cooking</strong> fat or oil did you " +
                        "use most often when you completed this recall?</p>"),
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Did not use"))
                        .plus(new MultipleChoiceQuestionOption("Butter, salted"))
                        .plus(new MultipleChoiceQuestionOption("Coconut oil"))
                        .plus(new MultipleChoiceQuestionOption("Canola oil"))
                        .plus(new MultipleChoiceQuestionOption("Corn oil"))
                        .plus(new MultipleChoiceQuestionOption("Ghee"))
                        .plus(new MultipleChoiceQuestionOption("Mustard seed oil"))
                        .plus(new MultipleChoiceQuestionOption("Nut oil e.g. peanut/ groundnut oil"))
                        .plus(new MultipleChoiceQuestionOption("Olive oil"))
                        .plus(new MultipleChoiceQuestionOption("Palm oil"))
                        .plus(new MultipleChoiceQuestionOption("Rice bran oil"))
                        .plus(new MultipleChoiceQuestionOption("Safflower oil"))
                        .plus(new MultipleChoiceQuestionOption("Sunflower oil"))
                        .plus(new MultipleChoiceQuestionOption("Vegetable oil (rapeseed / colza)"))
                        .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true))
        ), priority);
    }
}
