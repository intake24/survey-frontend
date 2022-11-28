package uk.ac.ncl.openlab.intake24.client.survey.scheme.debeat;

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
                        .plus(new MultipleChoiceQuestionOption("supermarket / local shop / market / grocery delivery / food bank - as part of household shopping", "0"))
                        .plus(new MultipleChoiceQuestionOption("supermarket / local shop / market / petrol station - food on the go", "1"))
                        .plus(new MultipleChoiceQuestionOption("canteen at work / school / university / college", "2"))
                        .plus(new MultipleChoiceQuestionOption("fast food / takeaway shop / van", "3"))
                        .plus(new MultipleChoiceQuestionOption("online food delivery service (e.g. Deliveroo, Just Eat) and delivered to you", "4"))
                        .plus(new MultipleChoiceQuestionOption("directly from a food outlet (e.g. over the phone or via the outlet's own website or app) and delivered to you", "5"))
                        .plus(new MultipleChoiceQuestionOption("restaurant / café / bar / club", "6"))
                        .plus(new MultipleChoiceQuestionOption("vending machine", "7"))
                        .plus(new MultipleChoiceQuestionOption("other (please specify):", "8", true))
                        .plus(new MultipleChoiceQuestionOption("don't know", "9"))
        ), priority);
    }
}
