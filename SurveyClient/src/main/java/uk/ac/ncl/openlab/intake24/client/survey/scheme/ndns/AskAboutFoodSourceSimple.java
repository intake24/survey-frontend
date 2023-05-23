package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.Meal;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.FoodSource;

public class AskAboutFoodSourceSimple extends FoodSource {

    public AskAboutFoodSourceSimple(String promptText, PVector<MultipleChoiceQuestionOption> options) {
        super(promptText, options);
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutFoodSourceSimple(
                "<p>Where was <strong><u>most</u></strong> of the food for your %s purchased from?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Home"))
                        .plus(new MultipleChoiceQuestionOption("Supermarket / local shop"))
                        .plus(new MultipleChoiceQuestionOption("School canteen"))
                        .plus(new MultipleChoiceQuestionOption("Fast food / takeaway"))
                        .plus(new MultipleChoiceQuestionOption("Café / restaurant/ pub"))
                        .plus(new MultipleChoiceQuestionOption("Food bank"))
                        .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
