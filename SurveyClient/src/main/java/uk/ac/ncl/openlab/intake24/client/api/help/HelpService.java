package uk.ac.ncl.openlab.intake24.client.api.help;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Options(dispatcher = AccessDispatcher.class, serviceRootKey = "intake24-api")
public interface HelpService extends RestService {

    HelpService INSTANCE = GWT.create(HelpService.class);

    @POST
    @Path("/user/surveys/{id}/request-callback")
    void requestCallback(@PathParam("id") String surveyId, CallbackRequest callbackRequest, MethodCallback<Void> callback);
}
