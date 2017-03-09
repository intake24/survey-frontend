package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.recipes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.survey.CompoundFoodTemplateManager;
import uk.ac.ncl.openlab.intake24.client.survey.Recipe;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import java.util.List;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

public class SRecipes {

    @JsonProperty
    public final String versionId;
    @JsonProperty
    public final String schemeId;
    @JsonProperty
    public final PVector<SRecipe> recipes;

    @JsonCreator
    public SRecipes(@JsonProperty("schemeId") String scheme_id, @JsonProperty("versionId") String version_id, @JsonProperty("recipes") List<SRecipe> recipes) {
        this.schemeId = scheme_id;
        this.versionId = version_id;
        this.recipes = TreePVector.from(recipes);
    }

    public SRecipes(String schemeId, String versionId, PVector<Recipe> recipes) {
        this.schemeId = schemeId;
        this.versionId = versionId;
        this.recipes = map(recipes, new Function1<Recipe, SRecipe>() {
            @Override
            public SRecipe apply(Recipe argument) {
                return new SRecipe(argument);
            }
        });
    }

    public PVector<Recipe> toRecipes(final PortionSizeScriptManager scriptManager, final CompoundFoodTemplateManager templateManager) {
        return map(recipes, new Function1<SRecipe, Recipe>() {
            @Override
            public Recipe apply(SRecipe argument) {
                return argument.toRecipe(scriptManager, templateManager);
            }
        });
    }
}
