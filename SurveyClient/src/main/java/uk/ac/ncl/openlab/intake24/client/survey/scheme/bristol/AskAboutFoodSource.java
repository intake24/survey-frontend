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
                "<p>Where did you get <strong>most</strong> of the food/drink for your %s?</p>",
                TreePVector.<MultipleChoiceQuestionOption>empty()
                        .plus(new MultipleChoiceQuestionOption("Supermarket"))
                        .plus(new MultipleChoiceQuestionOption("Local / convenience store (e.g. Premier, Spar, Nisa, etc.)", "Local / convenience store"))
                        .plus(new MultipleChoiceQuestionOption("Traditional market / delicatessen / butchers / fishmongers / farm shop"))
                        .plus(new MultipleChoiceQuestionOption("Bakery / sandwich shop (e.g. Greggs, Cooplands, Pret a Manger, etc.)", "Bakery / sandwich shop"))
                        .plus(new MultipleChoiceQuestionOption("Transport hub (e.g.  petrol station, motorway service station, airport, etc.)", "Transport hub"))
                        .plus(new MultipleChoiceQuestionOption("Restaurant"))
                        .plus(new MultipleChoiceQuestionOption("Café / coffee house / tea rooms"))
                        .plus(new MultipleChoiceQuestionOption("Pub / bar / club"))
                        .plus(new MultipleChoiceQuestionOption("Fast food / takeaway outlet / street food outlet"))
                        .plus(new MultipleChoiceQuestionOption("Non-food shops (e.g. pharmacies, discount stores, off licences, etc.)", "Non-food shops"))
                        .plus(new MultipleChoiceQuestionOption("Leisure facility (e.g. tourist attraction, gym, cinema, concert venue, hotels, etc.)", "Leisure facility"))
                        .plus(new MultipleChoiceQuestionOption("Vending machine"))
                        .plus(new MultipleChoiceQuestionOption("Canteen (e.g. at work, college, university, etc.)", "Canteen"))
                        .plus(new MultipleChoiceQuestionOption("Grown at home / allotment"))
                        .plus(new MultipleChoiceQuestionOption("Other (e.g. online store, food bank, etc.)", "Other"))
                        .plus(new MultipleChoiceQuestionOption("Don't know"))
        ), priority);
    }
}
