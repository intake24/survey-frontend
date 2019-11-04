package uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets;

import com.google.gwt.dom.client.Element;

public class SVGUtils {
    private static final String SVG_NAMESPACE = "http://www.w3.org/2000/svg";

    private static native Element createElementNS(final String ns,
                                                  final String name)/*-{
        return document.createElementNS(ns, name);
    }-*/;

    public static Element createSvgNamespaceElement(final String name) {
        return createElementNS(SVG_NAMESPACE, name);
    }
}
