package uk.ac.ncl.openlab.intake24.client.api.survey;

import org.workcraft.gwt.shared.client.Option;

public class SurveyParameters {
    public String id;
    public String schemeId;
    public String state;
    public Option<String> suspensionReason;
    public Option<String> description;
    public Option<String> finalPageHtml;
    public UxEventsSettings uxEventsSettings;
    public Boolean storeUserSessionOnServer;
}
