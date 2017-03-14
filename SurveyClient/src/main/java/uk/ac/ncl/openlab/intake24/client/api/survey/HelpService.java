package uk.ac.ncl.openlab.intake24.client.api.survey;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Options(dispatcher = AccessDispatcher.class)
public interface HelpService extends RestService {

    HelpService INSTANCE = GWT.create(HelpService.class);

    @POST
    @Path("/user/surveys/{id}/callback-request")
    void requestCallback(CallbackRequest callbackRequest, MethodCallback<Void> callback);
}
