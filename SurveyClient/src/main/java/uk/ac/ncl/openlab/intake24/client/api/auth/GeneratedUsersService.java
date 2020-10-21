package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Options(serviceRootKey = "intake24-api")
public interface GeneratedUsersService extends RestService {
    GeneratedUsersService INSTANCE = GWT.create(GeneratedUsersService.class);

    @POST
    @Path("/surveys/{surveyId}/generate-user")
    void generateUser(@PathParam("surveyId") String surveyId, MethodCallback<GeneratedCredentials> callback);

    @POST
    @Path("/surveys/{surveyId}/create-user")
    void createUser(@PathParam("surveyId") String surveyId, @QueryParam("params") String encodedParameters,
                    MethodCallback<CreateUserResponse> callback);
}
