package uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

public class CircledText extends ComplexPanel {
    public Element svgElement;
    public Element textElement = null;

    public void setText(final String text) {
        if (textElement != null)
            svgElement.removeChild(textElement);

        textElement = SVGUtils.createSvgNamespaceElement("text");
        textElement.setAttribute("class", "circled-text-text");
        textElement.setInnerText(text);
        textElement.setAttribute("x", "0");
        textElement.setAttribute("y", "0.1");
        textElement.setAttribute("dominant-baseline", "middle");
        textElement.setAttribute("text-anchor", "middle");

        svgElement.appendChild(textElement);
    }

    public CircledText(String initialText) {
        svgElement = SVGUtils.createSvgNamespaceElement("svg");
        svgElement.setAttribute("viewBox", "-1 -1 2 2");
        svgElement.setAttribute("class", "circled-text");

        Element circle = SVGUtils.createSvgNamespaceElement("circle");
        circle.setAttribute("class", "circled-text-circle");
        circle.setAttribute("cx", "0");
        circle.setAttribute("cy", "0");
        circle.setAttribute("r", "0.9");

        svgElement.appendChild(circle);

        setText(initialText);

        setElement(svgElement);
    }
}
