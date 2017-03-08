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

public class SerialisableRecipes {

    @JsonProperty
    public final String version_id;
    @JsonProperty
    public final String scheme_id;
    @JsonProperty
    public final PVector<SerialisableRecipe> recipes;

    @JsonCreator
    public SerialisableRecipes(@JsonProperty("schemeId") String scheme_id, @JsonProperty("versionId") String version_id, @JsonProperty("recipes") List<SerialisableRecipe> recipes) {
        this.scheme_id = scheme_id;
        this.version_id = version_id;
        this.recipes = TreePVector.from(recipes);
    }

    public SerialisableRecipes(String scheme_id, String version_id, PVector<Recipe> recipes) {
        this.scheme_id = scheme_id;
        this.version_id = version_id;
        this.recipes = map(recipes, new Function1<Recipe, SerialisableRecipe>() {
            @Override
            public SerialisableRecipe apply(Recipe argument) {
                return new SerialisableRecipe(argument);
            }
        });
    }

    public PVector<Recipe> toRecipes(final PortionSizeScriptManager scriptManager, final CompoundFoodTemplateManager templateManager) {
        return map(recipes, new Function1<SerialisableRecipe, Recipe>() {
            @Override
            public Recipe apply(SerialisableRecipe argument) {
                return argument.toRecipe(scriptManager, templateManager);
            }
        });
    }
}
