package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import org.workcraft.gwt.shared.client.Option;

public class EmbeddedData {

    private static Option<String> getMetaTagContent(String name) {
        final NodeList<Element> elements = Document.get().getElementsByTagName("meta");

        for (int i = 0; i < elements.getLength(); i++) {
            final MetaElement m = MetaElement.as(elements.getItem(i));

            if (m.getName().equals(name))
                return Option.fromNullable(m.getContent());
        }

        return Option.none();
    }


    public static Option<String> getSurveyId() {
        return getMetaTagContent("intake24:surveyId");
    }

    public static Option<String> getApiBaseUrl() {
        return getMetaTagContent("intake24:apiBaseUrl");
    }
}
