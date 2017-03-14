package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface GeneratedUsersService extends RestService {
    GeneratedUsersService INSTANCE = GWT.create(GeneratedUsersService.class);

    @POST
    @Path("/user/surveys/{surveyId}/generate-user")
    void generateUser(@PathParam("surveyId") String surveyId, MethodCallback<GeneratedCredentials> callback);
}
