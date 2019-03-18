package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.user.client.ui.Widget;

public interface WeightFactorLabels {

    public boolean displayZeroFraction();

    public Widget getWholeLabelWidget();

    public Widget getFractionLabelWidget();

    public void updateWholeLabel(int whole);

    public void updateFractionLabel(int numerator, int denominator);
}
