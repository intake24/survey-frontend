package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.api.errors.ErrorReport;
import uk.ac.ncl.openlab.intake24.client.api.errors.ErrorReportingService;
import uk.ac.ncl.openlab.intake24.client.api.errors.SThrowable;
import uk.ac.ncl.openlab.intake24.client.survey.StateManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class UncaughtExceptionHandler implements GWT.UncaughtExceptionHandler {

  public static final UncaughtExceptionHandler INSTANCE = new UncaughtExceptionHandler();

  private static boolean reportErrorInProgress = false;

  public static void reportError(Throwable exception) {

    if (reportErrorInProgress) {
      GWT.log("Unhandled exception occurred during error reporting, will not report to avoid loops", exception);
      reportErrorInProgress = false;
      return;
    }

    String surveyStateJSON = "{}";

    String surveyId = EmbeddedData.surveyId;
    Option<String> userId = AuthCache.getCurrentUserIdOption();

    if (!userId.isEmpty() && EmbeddedData.reportSurveyState)
      surveyStateJSON = StateManagerUtil.getLatestStateSerialised(userId.getOrDie()).getOrElse("{}");

    Throwable cur = exception;

    List<SThrowable> exceptionChain = new ArrayList<>();

    if (EmbeddedData.reportStackTrace) {
      while (cur != null) {
        exceptionChain.add(new SThrowable(cur));
        cur = cur.getCause();
      }
    }

    reportErrorInProgress = true;

    ErrorReportingService.INSTANCE.reportError(new ErrorReport(surveyId, userId, GWT.getPermutationStrongName(), exceptionChain, surveyStateJSON), new MethodCallback<Void>() {
      @Override
      public void onFailure(Method method, Throwable errorServiceException) {
        GWT.log("Failed to report exception", errorServiceException);
        reportErrorInProgress = false;

      }

      @Override
      public void onSuccess(Method method, Void response) {
        GWT.log("Reported " + exception.getClass().getName());
        reportErrorInProgress = false;
      }
    });
  }

  @Override
  public void onUncaughtException(final Throwable e) {
    reportError(e);
  }
}
