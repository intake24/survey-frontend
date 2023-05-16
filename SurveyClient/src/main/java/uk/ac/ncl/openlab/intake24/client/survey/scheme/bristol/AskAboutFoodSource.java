package uk.ac.ncl.openlab.intake24.client.survey.scheme.bristol;

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
                "<p>Where did you get <strong>most</strong> of the food/drink at %s?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Supermarket"))
                        .plus(new MultipleChoiceQuestionOption("Local shop / convenience store / traditional market / food bank / farm shop / delicatessen"))
                        .plus(new MultipleChoiceQuestionOption("Transport hubs e.g. petrol station / motorway service station / airport"))
                        .plus(new MultipleChoiceQuestionOption("Bakery / sandwich shop"))
                        .plus(new MultipleChoiceQuestionOption("Canteen e.g. at work / college / university"))
                        .plus(new MultipleChoiceQuestionOption("Fast food / takeaway shop / street food outlet"))
                        .plus(new MultipleChoiceQuestionOption("Pub / bar / club"))
                        .plus(new MultipleChoiceQuestionOption("Restaurant"))
                        .plus(new MultipleChoiceQuestionOption("Café / coffee house / tea rooms"))
                        .plus(new MultipleChoiceQuestionOption("Non-food shops e.g. pharmacies, discount stores, off licences"))
                        .plus(new MultipleChoiceQuestionOption("Leisure facility e.g. tourist attraction, gym, cinema, concert venue, hotels"))
                        .plus(new MultipleChoiceQuestionOption("Vending machine"))
                        .plus(new MultipleChoiceQuestionOption("Other (please name the outlet):", "Other", true))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
