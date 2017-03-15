package uk.ac.ncl.openlab.intake24.client.api.errors;

import java.util.ArrayList;
import java.util.List;

public class SThrowable {
    public String className;
    public String message;
    public List<StackTraceElement> stackTrace;

    @Deprecated
    public SThrowable() {

    }

    public SThrowable(Throwable e) {
        className = e.getClass().getName();
        message = e.getMessage();
        stackTrace = new ArrayList<>();

        for (StackTraceElement ste : e.getStackTrace()) {
            stackTrace.add(ste);
        }
    }
}
