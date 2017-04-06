package uk.ac.ncl.openlab.intake24.client.api.auth;

public class Credentials {
    String surveyId;
    String userName;
    String password;

    public Credentials() {
    }

    public Credentials(String surveyId, String userName, String password) {
        this.surveyId = surveyId;
        this.userName = userName;
        this.password = password;
    }
}
