package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.recipes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.CompoundFoodTemplateManager;
import uk.ac.ncl.openlab.intake24.client.survey.Recipe;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SerialisableFoodEntry;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SerialisableTemplateFood;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.List;

public class SerialisableRecipe {

    @JsonProperty
    public final SerialisableTemplateFood mainFood;
    @JsonProperty
    public final PVector<SerialisableFoodEntry> ingredients;

    @JsonCreator
    public SerialisableRecipe(@JsonProperty("mainFood") SerialisableTemplateFood mainFood, @JsonProperty("ingredients") List<SerialisableFoodEntry> ingredients) {
        this.mainFood = mainFood;
        this.ingredients = TreePVector.from(ingredients);
    }

    public SerialisableRecipe(Recipe recipe) {
        this.mainFood = new SerialisableTemplateFood(recipe.mainFood);
        this.ingredients = SerialisableFoodEntry.toSerialisable(recipe.ingredients);
    }

    public Recipe toRecipe(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        return new Recipe(mainFood.toTemplateFood(templateManager), SerialisableFoodEntry.toRuntime(ingredients, scriptManager, templateManager));
    }
}
