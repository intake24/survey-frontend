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

            if (m.getName().equals(name)) {
                String value = m.getContent();
                if (value == null)
                    return Option.none();
                return Option.some(value);
            }
        }

        return Option.none();
    }


    public static String surveyId = getMetaTagContent("intake24:surveyId").getOrDie();

    public static String surveySupportEmail = getMetaTagContent("intake24:surveySupportEmail").getOrDie();

    public static String apiBaseUrl = getMetaTagContent("intake24:apiBaseUrl").getOrDie();

    public static String localeId = getMetaTagContent("intake24:localeId").getOrDie();

    public static String privacyPolicyUrl = getMetaTagContent("intake24:privacyPolicyURL").getOrDie();

    public static String termsAndConditionsUrl = getMetaTagContent("intake24:termsAndConditionsURL").getOrDie();

    public static boolean displayLogos = Boolean.parseBoolean(getMetaTagContent("intake24:displayLogos").getOrDie());

    public static Option<String> originatingUrl = getMetaTagContent("intake24:originatingUrl");
}
