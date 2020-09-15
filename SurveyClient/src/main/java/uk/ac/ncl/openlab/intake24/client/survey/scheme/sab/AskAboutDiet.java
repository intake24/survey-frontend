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
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.Diet;

public class AskAboutDiet extends Diet {

    public AskAboutDiet(SafeHtml promptText, PVector<MultipleChoiceQuestionOption> options) {
        super(promptText, options);
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutDiet(SafeHtmlUtils.fromSafeConstant(
                "<p>Are you following any kind of special diet?</p>" +
                        "<p>You can specify more than one, e.g. vegetarian and gluten free.</p>"),
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Not on a special diet"))
                        .plus(new MultipleChoiceQuestionOption("Vegetarian (does not eat meat or fish)", "Vegetarian"))
                        .plus(new MultipleChoiceQuestionOption("Pescatarian (consumes fish, but not meat)", "Pescatarian"))
                        .plus(new MultipleChoiceQuestionOption("Vegan (does not eat meat or fish or animal products)", "Vegan"))
                        .plus(new MultipleChoiceQuestionOption("For medical reasons e.g. to manage diabetes", "For medical reasons"))
                        .plus(new MultipleChoiceQuestionOption("To lose weight"))
                        .plus(new MultipleChoiceQuestionOption("To gain weight"))
                        .plus(new MultipleChoiceQuestionOption("Gluten free"))
                        .plus(new MultipleChoiceQuestionOption("Wheat free"))
                        .plus(new MultipleChoiceQuestionOption("Dairy free"))
                        .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true))
        ), priority);
    }
}
