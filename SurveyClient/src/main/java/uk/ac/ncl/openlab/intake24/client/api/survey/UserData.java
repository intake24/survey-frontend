package uk.ac.ncl.openlab.intake24.client.api.survey;

import org.workcraft.gwt.shared.client.Option;

public class UserData {
    public String id;
    public Option<String> name;
    public int recallNumber;
    public Boolean redirectToFeedback;
    public Boolean maximumDailySubmissionsReached;
}
