package uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

public class RadialFraction extends ComplexPanel {
    private Element svg;
    private Element full;
    private final double RADIUS = 0.85;

    public String buildArcCommand(double rx, double ry, double rotation, boolean largeArc, boolean sweep, double endX, double endY) {
        return "A" + rx + " " + ry + " " + rotation + " " + (largeArc ? "1" : "0") + " " + (sweep ? "1" : "0") + " " + endX + " " + endY;
    }


    public Element makeSegment(double degreesStart, double degreesEnd) {

        degreesStart -= 90;
        degreesEnd -= 90;

        double startX = Math.cos(Math.toRadians(degreesStart)) * RADIUS;
        double startY = Math.sin(Math.toRadians(degreesStart)) * RADIUS;

        double endX = Math.cos(Math.toRadians(degreesEnd)) * RADIUS;
        double endY = Math.sin(Math.toRadians(degreesEnd)) * RADIUS;


        Element path = SVGUtils.createSvgNamespaceElement("path");
        path.setAttribute("class", "radial-fraction-full");
        path.setAttribute("d", "M0 0 L" + startX + " " + startY + buildArcCommand(RADIUS, RADIUS, 0,
                Math.abs(degreesEnd - degreesStart) > 180, true, endX, endY));

        return path;
    }

    public void setValue(double v) {

        if (full != null)
            svg.removeChild(full);

        full = makeSegment(0, 360 * v);
        svg.appendChild(full);

    }

    public RadialFraction(float initialValue) {
        svg = SVGUtils.createSvgNamespaceElement("svg");
        svg.setAttribute("viewBox", "-1 -1 2 2");
        svg.setAttribute("class", "radial-fraction");

        Element circle = SVGUtils.createSvgNamespaceElement("circle");
        circle.setAttribute("cx", "0");
        circle.setAttribute("cy", "0");
        circle.setAttribute("r", "0.9");

        circle.setAttribute("class", "radial-fraction-empty");

        svg.appendChild(circle);

        setValue(initialValue);
        setElement(svg);
    }
}
