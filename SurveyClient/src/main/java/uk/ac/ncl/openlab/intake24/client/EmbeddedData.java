package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import org.workcraft.gwt.shared.client.Option;

public class EmbeddedData {

    private static String getMetaTagContent(String name) {
        final NodeList<Element> elements = Document.get().getElementsByTagName("meta");

        for (int i = 0; i < elements.getLength(); i++) {
            final MetaElement m = MetaElement.as(elements.getItem(i));

            if (m.getName().equals(name)) {
                String value = m.getContent();
                if (value == null)
                    throw new RuntimeException("Required meta tag's \"" + name + "\" content is null");
                return value;
            }
        }

        throw new RuntimeException("Required meta tag \"" + name + "\" does not exist");
    }


    public static String getSurveyId() {
        return getMetaTagContent("intake24:surveyId");
    }

    public static String getApiBaseUrl() {
        return getMetaTagContent("intake24:apiBaseUrl");
    }
}
