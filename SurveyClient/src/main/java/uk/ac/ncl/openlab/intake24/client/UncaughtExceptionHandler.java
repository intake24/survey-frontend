package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.GWT;
import uk.ac.ncl.openlab.intake24.client.api.errors.ErrorReportingService;

public class UncaughtExceptionHandler implements GWT.UncaughtExceptionHandler {

    public static final UncaughtExceptionHandler INSTANCE = new UncaughtExceptionHandler();

    @Override
    public void onUncaughtException(final Throwable e) {
        GWT.log("Uncaught exception", e);

        ErrorReportingService.reportError(e);

    }
}
