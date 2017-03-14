package uk.ac.ncl.openlab.intake24.client.api.survey;

public class CallbackRequest {
    public String name;
    public String phone;

    @Deprecated
    public CallbackRequest() {
    }

    public CallbackRequest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
