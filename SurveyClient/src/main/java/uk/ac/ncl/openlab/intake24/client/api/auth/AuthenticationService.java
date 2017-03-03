package uk.ac.ncl.openlab.intake24.client.api.auth;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.dispatcher.DefaultDispatcher;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface AuthenticationService extends RestService {
    AuthenticationService INSTANCE = GWT.create(AuthenticationService.class);

    @POST
    @Path("/signin")
    void signin(Credentials request, MethodCallback<SigninResult> callback);

    @POST
    @Path("/refresh")
    @Options(dispatcher=RefreshDispatcher.class)
    void refresh(MethodCallback<RefreshResult> callback);
}
