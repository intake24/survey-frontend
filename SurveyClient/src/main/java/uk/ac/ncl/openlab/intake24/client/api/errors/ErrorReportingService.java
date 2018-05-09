package uk.ac.ncl.openlab.intake24.client.api.errors;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface ErrorReportingService extends RestService {

  ErrorReportingService INSTANCE = GWT.create(ErrorReportingService.class);

  @POST
  @Path("/errors/report")
  void reportError(ErrorReport report, MethodCallback<Void> callback);
}
