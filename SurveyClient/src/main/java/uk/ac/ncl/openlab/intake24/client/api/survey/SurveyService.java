package uk.ac.ncl.openlab.intake24.client.api.survey;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;
import uk.ac.ncl.openlab.intake24.client.survey.CompletedSurvey;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Options(dispatcher=AccessDispatcher.class, serviceRootKey = "intake24-api")
public interface SurveyService extends RestService {

    SurveyService INSTANCE = GWT.create(SurveyService.class);

    @GET
    @Path("/surveys/{id}/parameters")
    void getSurveyParameters(@PathParam("id") String surveyId, MethodCallback<SurveyParameters> callback);

    @POST
    @Path("/surveys/{id}")
    void submitSurvey(@PathParam("id") String surveyId, CompletedSurvey survey, MethodCallback<Void> callback);
}
