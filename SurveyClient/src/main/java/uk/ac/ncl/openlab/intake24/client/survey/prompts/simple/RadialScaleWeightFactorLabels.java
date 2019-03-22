package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.user.client.ui.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets.CircledText;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets.RadialFraction;

public class RadialScaleWeightFactorLabels implements WeightFactorLabels {
    private CircledText wholeLabel;
    private RadialFraction fractionLabel;


    public RadialScaleWeightFactorLabels() {
        this.wholeLabel = new CircledText("1");
        this.fractionLabel = new RadialFraction(0.5f);
    }

    @Override
    public boolean displayZeroFraction() {
        return true;
    }

    @Override
    public Widget getWholeLabelWidget() {
        return wholeLabel;
    }

    @Override
    public Widget getFractionLabelWidget() {
        return fractionLabel;
    }

    @Override
    public void updateWholeLabel(int whole) {
        wholeLabel.setText(Integer.toString(whole));
    }

    @Override
    public void updateFractionLabel(int numerator, int denominator) {
        fractionLabel.setValue((double) numerator / denominator);
    }
}


