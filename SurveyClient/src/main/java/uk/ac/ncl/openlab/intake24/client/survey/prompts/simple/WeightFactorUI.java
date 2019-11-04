package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

public class WeightFactorUI extends Composite {
    private final FlowPanel weightFactorLabel;
    private final WeightFactorLabels labelsImpl;

    public final int minNumerator;
    public final int maxNumerator;
    public final int denominator;
    public final double baseWeight;
    public final String portionDescription;

    private int numerator;

    private static int gcm(int a, int b) {
        return b == 0 ? a : gcm(b, a % b);
    }

    public int getWeightFactor() {
        return numerator;
    }

    public void setWeightFactor(int newNumerator) {
        numerator = newNumerator;

        boolean hasWhole = numerator >= denominator;
        boolean hasFraction = numerator % denominator > 0;
        boolean displayFraction = hasFraction || labelsImpl.displayZeroFraction();

        weightFactorLabel.clear();

        weightFactorLabel.add(new HTMLPanel("span", "I had "));

        if (hasWhole) {
            weightFactorLabel.add(labelsImpl.getWholeLabelWidget());
            labelsImpl.updateWholeLabel(numerator / denominator);

            if (displayFraction)
                weightFactorLabel.add(new HTMLPanel("span", " and "));
        }

        if (displayFraction) {
            weightFactorLabel.add(labelsImpl.getFractionLabelWidget());
            int gcm = gcm(numerator % denominator, denominator);
            labelsImpl.updateFractionLabel((numerator % denominator) / gcm, denominator/ gcm);
        }

        weightFactorLabel.add(new HTMLPanel("span", "<br/> of the " + portionDescription + "<br/> <strong>" + Math.round(baseWeight * numerator / denominator) + " g </strong>"));
    }

    public WeightFactorUI(int minNumerator, int maxNumerator, int denominator, double baseWeight, String portionDescription,
                          WeightFactorLabels labelsImpl) {

        this.denominator = denominator;
        this.minNumerator = minNumerator;
        this.maxNumerator = maxNumerator;
        this.baseWeight = baseWeight;
        this.portionDescription = portionDescription;
        this.labelsImpl = labelsImpl;

        numerator = minNumerator;

        FlowPanel container = new FlowPanel();
        container.addStyleName("intake24-as-served-weight-factor-container");

        Button more = new Button();

        HTMLPanel moreLabel = new HTMLPanel("i", "");
        moreLabel.addStyleName("fas fa-chevron-up intake24-as-served-weight-factor-button");
        more.getElement().appendChild(moreLabel.getElement());
        more.addClickHandler(e -> setWeightFactor(Math.min(maxNumerator, numerator + 1)));

        Button less = new Button();
        HTMLPanel lessLabel = new HTMLPanel("i", "");
        lessLabel.addStyleName("fas fa-chevron-down intake24-as-served-weight-factor-button");
        less.getElement().appendChild(lessLabel.getElement());
        less.addClickHandler(e -> setWeightFactor(Math.max(minNumerator, numerator - 1)));

        weightFactorLabel = new FlowPanel();
        weightFactorLabel.addStyleName("ui");

        setWeightFactor(numerator);

        container.add(more);
        container.add(weightFactorLabel);
        container.add(less);

        initWidget(container);
    }
}

