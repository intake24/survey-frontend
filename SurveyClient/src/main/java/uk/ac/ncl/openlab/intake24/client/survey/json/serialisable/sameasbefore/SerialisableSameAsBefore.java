package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.sameasbefore;

import java.util.List;

import org.pcollections.PVector;
import org.pcollections.TreePVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import uk.ac.ncl.openlab.intake24.client.survey.CompoundFoodTemplateManager;
import uk.ac.ncl.openlab.intake24.client.survey.EncodedFood;
import uk.ac.ncl.openlab.intake24.client.survey.FoodEntry;
import uk.ac.ncl.openlab.intake24.client.survey.SameAsBefore;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SerialisableEncodedFood;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SerialisableFoodEntry;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

public class SerialisableSameAsBefore {
    @JsonProperty
    final public String scheme_id;
    @JsonProperty
    final public String version_id;
    @JsonProperty
    final public SerialisableEncodedFood mainFood;
    @JsonProperty
    final public PVector<SerialisableFoodEntry> linkedFoods;

    @JsonCreator
    public SerialisableSameAsBefore(
            @JsonProperty("schemeId") String scheme_id, @JsonProperty("versionId") String version_id,
            @JsonProperty("mainFood") SerialisableEncodedFood mainFood, @JsonProperty("linkedFoods") List<SerialisableFoodEntry> linkedFoods) {
        this.scheme_id = scheme_id;
        this.version_id = version_id;
        this.mainFood = mainFood;
        this.linkedFoods = TreePVector.from(linkedFoods);
    }

    public SerialisableSameAsBefore(EncodedFood mainFood, PVector<FoodEntry> linkedFoods, String scheme_id, String version_id) {
        this.scheme_id = scheme_id;
        this.version_id = version_id;
        this.mainFood = new SerialisableEncodedFood(mainFood);
        this.linkedFoods = SerialisableFoodEntry.toSerialisable(linkedFoods);
    }

    public SameAsBefore toSameAsBefore(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        return new SameAsBefore(mainFood.toEncodedFood(scriptManager), SerialisableFoodEntry.toRuntime(linkedFoods, scriptManager, templateManager));
    }
}
