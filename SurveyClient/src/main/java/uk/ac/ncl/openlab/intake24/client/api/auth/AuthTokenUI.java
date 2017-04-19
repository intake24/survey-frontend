package uk.ac.ncl.openlab.intake24.client.api.auth;

public interface AuthTokenUI {
    void onSigninAttemptFailed();
    void onAuthenticationServiceError();
}
