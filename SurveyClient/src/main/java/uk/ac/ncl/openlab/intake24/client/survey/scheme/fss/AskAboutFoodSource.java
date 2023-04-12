package uk.ac.ncl.openlab.intake24.client.survey.scheme.fss;

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
                "<p>Where was <strong><u>most</u></strong> of the food for your %s purchased from?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Supermarket / local shop / petrol station"))
                        .plus(new MultipleChoiceQuestionOption("Canteen at school"))
                        .plus(new MultipleChoiceQuestionOption("Fast food / take-away outlet"))
                        .plus(new MultipleChoiceQuestionOption("Café / coffee shop / sandwich bar / deli"))
                        .plus(new MultipleChoiceQuestionOption("Restaurant or pub"))
                        .plus(new MultipleChoiceQuestionOption("Burger, chip or kebab van / 'street food'"))
                        .plus(new MultipleChoiceQuestionOption("Food bank (charity/community) or government food delivery scheme (food boxes/parcels)"))
                        .plus(new MultipleChoiceQuestionOption("Leisure centre / recreation or entertainment venue"))
                        .plus(new MultipleChoiceQuestionOption("Vending machine in any location"))
                        .plus(new MultipleChoiceQuestionOption("Other (please specify):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
