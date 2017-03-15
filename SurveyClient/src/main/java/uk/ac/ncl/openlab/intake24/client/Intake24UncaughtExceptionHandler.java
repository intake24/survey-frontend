package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import org.fusesource.restygwt.client.Json;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.api.errors.*;
import uk.ac.ncl.openlab.intake24.client.survey.StateManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class Intake24UncaughtExceptionHandler implements UncaughtExceptionHandler {

    public static final Intake24UncaughtExceptionHandler INSTANCE = new Intake24UncaughtExceptionHandler();

    private static final ExceptionChainCodec exceptionChainCodec = GWT.create(ExceptionChainCodec.class);

    @Override
    public void onUncaughtException(final Throwable e) {
        String surveyStateJSON = "{}";

        String surveyId = EmbeddedData.getSurveyId();
        Option<String> userName = AuthCache.getCurrentUserNameOption();

        if (!userName.isEmpty())
            surveyStateJSON = StateManagerUtil.getLatestStateSerialised(userName.getOrDie()).getOrElse("{}");

        Throwable cur = e;

        List<SThrowable> exceptionChain = new ArrayList<>();

        while (cur != null) {
            exceptionChain.add(new SThrowable(cur));
            cur = cur.getCause();
        }

        ErrorReportingService.INSTANCE.reportError(new ErrorReport(surveyId, userName, GWT.getPermutationStrongName(), exceptionChain, surveyStateJSON), new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                GWT.log("Failed to reported uncaught exception to the server:");
                GWT.log("Uncaught exception", e);
            }

            @Override
            public void onSuccess(Method method, Void response) {
                GWT.log("Reported uncaught exception to the server:");
                GWT.log("Uncaught exception", e);
            }
        });
    }
}
