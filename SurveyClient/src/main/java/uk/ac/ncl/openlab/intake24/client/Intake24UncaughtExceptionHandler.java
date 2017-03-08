package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import uk.ac.ncl.openlab.intake24.client.api.auth.AuthCache;
import uk.ac.ncl.openlab.intake24.client.survey.StateManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class Intake24UncaughtExceptionHandler implements UncaughtExceptionHandler {

    // private final static HelpServiceAsync helpService = HelpServiceAsync.Util.getInstance();

    @Override
    public void onUncaughtException(final Throwable e) {
        String encodedSurveyState = "{}";


        if (AuthCache.currentUserNameKnown())
            encodedSurveyState = StateManagerUtil.getLatestStateSerialised(AuthCache.getCurrentUserName()).getOrElse("{}");


        Throwable cur = e;

        List<String> classNames = new ArrayList<String>();
        List<String> messages = new ArrayList<String>();
        List<StackTraceElement[]> stackTraces = new ArrayList<StackTraceElement[]>();

        while (cur != null)

        {
            classNames.add(cur.getClass()
                    .getName());
            messages.add(cur.getMessage());
            stackTraces.add(cur.getStackTrace());
            cur = cur.getCause();
        }

    /*
    helpService.reportUncaughtException(GWT.getPermutationStrongName(), classNames, messages, stackTraces,
        encodedSurveyState, new AsyncCallback<Void>() {

          @Override
          public void onSuccess(Void result) {
            GWT.log("Reported uncaught exception to the server:");
            GWT.log("Uncaught exception", e);
          }

          @Override
          public void onFailure(Throwable caught) {
            GWT.log("Failed to reported uncaught exception to the server:");
            GWT.log("Uncaught exception", e);
          }
        });*/
    }
}
