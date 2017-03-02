package uk.ac.ncl.openlab.intake24.client.api.survey;

import org.workcraft.gwt.shared.client.Option;

public class SurveyParameters {
    public String schemeId;
    public String localeId;
    public String state;
    public Option<String> suspensionReason;
    public Option<String> externalFollowUpURL;
    public String supportEmail;
}
