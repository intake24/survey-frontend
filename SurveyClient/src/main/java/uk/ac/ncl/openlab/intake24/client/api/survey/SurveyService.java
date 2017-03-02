package uk.ac.ncl.openlab.intake24.client.api.survey;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public interface SurveyService extends RestService {

    @GET
    @Path("/user/surveys/{id}/parameters")
    void getSurveyParameters(@PathParam("id") String surveyId, MethodCallback<SurveyParameters> callback);
}
