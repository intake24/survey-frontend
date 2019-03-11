package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

public class WeightFactorSettings {
    public final String fieldName;
    public final boolean showLessOption;
    public final boolean showMoreOption;

    public WeightFactorSettings(String fieldName, boolean showLessOption, boolean showMoreOption) {
        this.fieldName = fieldName;
        this.showLessOption = showLessOption;
        this.showMoreOption = showMoreOption;
    }
}
