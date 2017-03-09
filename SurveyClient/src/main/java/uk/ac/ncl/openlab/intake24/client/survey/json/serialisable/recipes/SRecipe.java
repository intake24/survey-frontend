package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.recipes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.CompoundFoodTemplateManager;
import uk.ac.ncl.openlab.intake24.client.survey.Recipe;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SFoodEntry;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.STemplateFood;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.List;

public class SRecipe {

    @JsonProperty
    public final STemplateFood mainFood;
    @JsonProperty
    public final PVector<SFoodEntry> ingredients;

    @JsonCreator
    public SRecipe(@JsonProperty("mainFood") STemplateFood mainFood, @JsonProperty("ingredients") List<SFoodEntry> ingredients) {
        this.mainFood = mainFood;
        this.ingredients = TreePVector.from(ingredients);
    }

    public SRecipe(Recipe recipe) {
        this.mainFood = new STemplateFood(recipe.mainFood);
        this.ingredients = SFoodEntry.toSerialisable(recipe.ingredients);
    }

    public Recipe toRecipe(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        return new Recipe(mainFood.toTemplateFood(templateManager), SFoodEntry.toRuntime(ingredients, scriptManager, templateManager));
    }
}
