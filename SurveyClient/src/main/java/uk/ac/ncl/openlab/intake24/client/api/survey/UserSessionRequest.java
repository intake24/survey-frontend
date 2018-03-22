package uk.ac.ncl.openlab.intake24.client.api.survey;

public class UserSessionRequest {
    public String data;

    @Deprecated
    public UserSessionRequest() {
    }

    public UserSessionRequest(String data) {
        this.data = data;
    }

}
