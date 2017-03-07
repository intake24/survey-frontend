package uk.ac.ncl.openlab.intake24.client.api.survey;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Options(dispatcher=AccessDispatcher.class)
public interface SurveyService extends RestService {

    SurveyService INSTANCE = GWT.create(SurveyService.class);

    @GET
    @Path("/user/surveys/{id}/parameters")
    void getSurveyParameters(@PathParam("id") String surveyId, MethodCallback<SurveyParameters> callback);
}
