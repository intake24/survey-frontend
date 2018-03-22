package uk.ac.ncl.openlab.intake24.client.api.survey;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;

import javax.ws.rs.*;

@Options(dispatcher = AccessDispatcher.class, serviceRootKey = "intake24-api")
public interface UserSessionService extends RestService {

    UserSessionService INSTANCE = GWT.create(UserSessionService.class);

    @GET
    @Path("/surveys/{id}/user-session")
    void get(@PathParam("id") String surveyId, MethodCallback<UserSessionResponse> callback);

    @POST
    @Path("/surveys/{id}/user-session")
    void save(@PathParam("id") String surveyId, UserSessionRequest userSessionRequest, MethodCallback<UserSession> callback);

    @DELETE
    @Path("/surveys/{id}/user-session")
    void clean(@PathParam("id") String surveyId, MethodCallback<UserSession> callback);
}
