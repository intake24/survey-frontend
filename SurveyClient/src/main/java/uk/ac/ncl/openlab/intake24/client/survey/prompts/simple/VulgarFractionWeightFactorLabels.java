package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets.CircledText;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets.RadialFraction;

public class VulgarFractionWeightFactorLabels implements WeightFactorLabels {
    private final boolean reduceFractions;
    private HTMLPanel wholeLabel;
    private HTMLPanel fractionLabel;

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    @Override
    public boolean displayZeroFraction() {
        return false;
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
        wholeLabel.getElement().setInnerHTML(Integer.toString(whole));
    }

    @Override
    public void updateFractionLabel(int numerator, int denominator) {
        int gcd = gcd(numerator, denominator);

        int num = reduceFractions ? numerator / gcd : numerator;
        int den = reduceFractions ? denominator / gcd : denominator;

        fractionLabel.getElement().setInnerHTML("<sup>" + num + "</sup>/" + "<sub>" + den + "</sub>");
    }

    public VulgarFractionWeightFactorLabels(boolean reduceFractions) {
        this.reduceFractions = reduceFractions;
        wholeLabel = new HTMLPanel("span", "");
        wholeLabel.getElement().addClassName("whole");

        fractionLabel = new HTMLPanel("span", "");
        fractionLabel.getElement().addClassName("fraction");
    }
}
