package uk.ac.ncl.openlab.intake24.client.survey.scheme.nz;

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
                "<p>Where was <strong><u>most</u></strong> of the food for your %s purchased or obtained from?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Supermarket / fruit and vegetable store / butcher"))
                        .plus(new MultipleChoiceQuestionOption("Meal delivery boxes (e.g. My Food Bag or HelloFresh)"))
                        .plus(new MultipleChoiceQuestionOption("Convenience store / dairy / bakery / petrol station"))
                        .plus(new MultipleChoiceQuestionOption("Fast food / take-away outlet / food truck / 'street food' (include delivery services)"))
                        .plus(new MultipleChoiceQuestionOption("Café / restaurant (include delivery services)"))
                        .plus(new MultipleChoiceQuestionOption("Canteen at work or school / university or student hall"))
                        .plus(new MultipleChoiceQuestionOption("Marae / church"))
                        .plus(new MultipleChoiceQuestionOption("School food programme (e.g. Ka ora Ka Ako, breakfast in schools) / food charity / food bank"))
                        .plus(new MultipleChoiceQuestionOption("Vending machine in any location"))
                        .plus(new MultipleChoiceQuestionOption("Home grown, hunted, gathered, or gifted"))
                        .plus(new MultipleChoiceQuestionOption("Other place (please specify):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
