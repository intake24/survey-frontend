package uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets;

import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class RadialFraction extends ComplexPanel {
    private Element svg;

    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";

    private static native Element createElementNS(final String ns,
                                                  final String name)/*-{
        return document.createElementNS(ns, name);
    }-*/;

    public String buildArcCommand(double rx, double ry, double rotation, boolean largeArc, boolean sweep, double endX, double endY) {
        return "A" + rx + " " + ry + " " + rotation + " " + (largeArc ? "1" : "0") + " " + (sweep ? "1" : "0") + " " + endX + " " + endY;
    }


    public Element makeSegment(double degreesStart, double degreesEnd) {

        degreesStart -= 90;
        degreesEnd -= 90;

        double startX = Math.cos(Math.toRadians(degreesStart));
        double startY = Math.sin(Math.toRadians(degreesStart));

        double endX = Math.cos(Math.toRadians(degreesEnd));
        double endY = Math.sin(Math.toRadians(degreesEnd));


        Element path = createElementNS(SVG_NAMESPACE, "path");
        path.setAttribute("d", "M0 0 L" + startX + " " + startY + buildArcCommand(1, 1, 0,
                Math.abs(degreesEnd - degreesStart) > 180, true, endX, endY));

        return path;
    }

    public void setValue(double v) {
        svg.removeAllChildren();
        svg.appendChild(makeSegment(0, 360 * v));

    }

    public RadialFraction(float initialValue) {
        svg = createElementNS(SVG_NAMESPACE, "svg");
        svg.setAttribute("viewBox", "-1 -1 2 2");
        svg.getStyle().setHeight(1, Style.Unit.EM);
        svg.getStyle().setWidth(1, Style.Unit.EM);
        svg.getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        setValue(initialValue);
        setElement(svg);
    }
}
