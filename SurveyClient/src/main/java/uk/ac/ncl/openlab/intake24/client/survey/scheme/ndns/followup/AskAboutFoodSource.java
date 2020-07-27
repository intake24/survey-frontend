package uk.ac.ncl.openlab.intake24.client.survey.scheme.ndns.followup;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.Meal;
import uk.ac.ncl.openlab.intake24.client.survey.PromptRule;
import uk.ac.ncl.openlab.intake24.client.survey.WithPriority;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MealOperation;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.scheme.base.FoodSource;

public class AskAboutFoodSource extends FoodSource {

    public AskAboutFoodSource(String promptText, PVector<MultipleChoiceQuestionOption> options) {
        super(promptText, options);
    }

    public static WithPriority<PromptRule<Meal, MealOperation>> withPriority(int priority) {
        return new WithPriority<>(new AskAboutFoodSource(
                "<p>Where was <strong><u>most</u></strong> of the food for %s purchased or obtained from?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Supermarket / convenience store / corner shop / petrol station – household shopping"))
                        .plus(new MultipleChoiceQuestionOption("Supermarket / convenience store / corner shop / petrol station – food on the go"))
                        .plus(new MultipleChoiceQuestionOption("Fast food / take-away outlet"))
                        .plus(new MultipleChoiceQuestionOption("Café / coffee shop / sandwich bar / deli"))
                        .plus(new MultipleChoiceQuestionOption("Sit-down restaurant or pub"))
                        .plus(new MultipleChoiceQuestionOption("Canteen at work or school / university / college"))
                        .plus(new MultipleChoiceQuestionOption("Burger, chip or kebab van / 'street food'"))
                        .plus(new MultipleChoiceQuestionOption("Food charity / food bank"))
                        .plus(new MultipleChoiceQuestionOption("Government or local authority food scheme e.g. food boxes / parcels"))
                        .plus(new MultipleChoiceQuestionOption("Leisure centre / recreation or entertainment venue"))
                        .plus(new MultipleChoiceQuestionOption("Vending machine in any location"))
                        .plus(new MultipleChoiceQuestionOption("Other place (please specify):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
