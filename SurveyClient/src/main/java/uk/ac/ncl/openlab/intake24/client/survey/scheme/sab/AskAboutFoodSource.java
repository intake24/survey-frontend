package uk.ac.ncl.openlab.intake24.client.survey.scheme.sab;

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
                "<p>Where was <strong>most</strong> of the food for your %s from?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Supermarket"))
                        .plus(new MultipleChoiceQuestionOption("Wholesaler / Distributor"))
                        .plus(new MultipleChoiceQuestionOption("Convenience shop / Corner shop / Small shop / General dealer"))
                        .plus(new MultipleChoiceQuestionOption("Stall / Roadside stall / Street vendor"))
                        .plus(new MultipleChoiceQuestionOption("Street food / Food truck / Bicycle or kebab van"))
                        .plus(new MultipleChoiceQuestionOption("Fast food joint"))
                        .plus(new MultipleChoiceQuestionOption("Take away / Delivery / Tiffin service"))
                        .plus(new MultipleChoiceQuestionOption("Café / Coffee shop"))
                        .plus(new MultipleChoiceQuestionOption("Restaurant"))
                        .plus(new MultipleChoiceQuestionOption("Informal abattoir or slaughterhouse"))
                        .plus(new MultipleChoiceQuestionOption("From friends or neighbours"))
                        .plus(new MultipleChoiceQuestionOption("Home-grown"))
                        .plus(new MultipleChoiceQuestionOption("Canteen or tuck shop at work/school/college/university"))
                        .plus(new MultipleChoiceQuestionOption("Recreation or entertainment venue / Cinema or multiplex"))
                        .plus(new MultipleChoiceQuestionOption("Other place (please specify):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
