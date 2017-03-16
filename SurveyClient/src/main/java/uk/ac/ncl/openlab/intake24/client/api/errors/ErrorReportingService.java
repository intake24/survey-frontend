package uk.ac.ncl.openlab.intake24.client.api.errors;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.EmbeddedData;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.survey.StateManagerUtil;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

public interface ErrorReportingService extends RestService {

    ErrorReportingService INSTANCE = GWT.create(ErrorReportingService.class);

    @POST
    @Path("/errors/report")
    void reportError(ErrorReport report, MethodCallback<Void> callback);

    static void reportError(Throwable exception) {
        String surveyStateJSON = "{}";

        String surveyId = EmbeddedData.getSurveyId();
        Option<String> userName = AuthCache.getCurrentUserNameOption();

        if (!userName.isEmpty())
            surveyStateJSON = StateManagerUtil.getLatestStateSerialised(userName.getOrDie()).getOrElse("{}");

        Throwable cur = exception;

        List<SThrowable> exceptionChain = new ArrayList<>();

        while (cur != null) {
            exceptionChain.add(new SThrowable(cur));
            cur = cur.getCause();
        }

        INSTANCE.reportError(new ErrorReport(surveyId, userName, GWT.getPermutationStrongName(), exceptionChain, surveyStateJSON), new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable errorServiceException) {
                GWT.log("Failed to report exception", errorServiceException);

            }

            @Override
            public void onSuccess(Method method, Void response) {
                GWT.log("Reported " + exception.getClass().getName());
            }
        });
    }
}
