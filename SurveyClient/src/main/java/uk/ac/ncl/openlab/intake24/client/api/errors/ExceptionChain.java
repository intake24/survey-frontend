package uk.ac.ncl.openlab.intake24.client.api.errors;

import java.util.List;

public class ExceptionChain {
    public List<SThrowable> exceptionChain;

    @Deprecated
    public ExceptionChain() {

    }

    public ExceptionChain(List<SThrowable> exceptionChain) {
        this.exceptionChain = exceptionChain;
    }
}
