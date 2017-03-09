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
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SEncodedFood;
import uk.ac.ncl.openlab.intake24.client.survey.json.serialisable.SFoodEntry;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

public class SSameAsBefore {
    @JsonProperty
    final public String schemeId;
    @JsonProperty
    final public String versionId;
    @JsonProperty
    final public SEncodedFood mainFood;
    @JsonProperty
    final public PVector<SFoodEntry> linkedFoods;

    @JsonCreator
    public SSameAsBefore(
            @JsonProperty("schemeId") String schemeId, @JsonProperty("versionId") String versionId,
            @JsonProperty("mainFood") SEncodedFood mainFood, @JsonProperty("linkedFoods") List<SFoodEntry> linkedFoods) {
        this.schemeId = schemeId;
        this.versionId = versionId;
        this.mainFood = mainFood;
        this.linkedFoods = TreePVector.from(linkedFoods);
    }

    public SSameAsBefore(EncodedFood mainFood, PVector<FoodEntry> linkedFoods, String schemeId, String versionId) {
        this.schemeId = schemeId;
        this.versionId = versionId;
        this.mainFood = new SEncodedFood(mainFood);
        this.linkedFoods = SFoodEntry.toSerialisable(linkedFoods);
    }

    public SameAsBefore toSameAsBefore(PortionSizeScriptManager scriptManager, CompoundFoodTemplateManager templateManager) {
        return new SameAsBefore(mainFood.toEncodedFood(scriptManager), SFoodEntry.toRuntime(linkedFoods, scriptManager, templateManager));
    }
}
