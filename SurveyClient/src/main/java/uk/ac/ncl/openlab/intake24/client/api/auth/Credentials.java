package uk.ac.ncl.openlab.intake24.client.api.auth;

import org.workcraft.gwt.shared.client.Option;

public class Credentials {
    Option<String> survey_id;
    String username;
    String password;

    public Credentials() { }

    public Credentials(Option<String> survey_id, String username, String password) {
        this.survey_id = survey_id;
        this.username = username;
        this.password = password;
    }
}
