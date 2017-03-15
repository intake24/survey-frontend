package uk.ac.ncl.openlab.intake24.client.api.errors;

import org.workcraft.gwt.shared.client.Option;

import java.util.List;

public class ErrorReport {

    public String surveyId;
    public Option<String> userName;
    public String gwtPermutationStrongName;
    public List<SThrowable> exceptionChain;
    public String surveyStateJSON;

    @Deprecated
    public ErrorReport() {

    }

    public ErrorReport(String surveyId, Option<String> userName, String gwtPermutationStrongName, List<SThrowable> exceptionChain, String surveyStateJSON) {
        this.surveyId = surveyId;
        this.userName = userName;
        this.gwtPermutationStrongName = gwtPermutationStrongName;
        this.exceptionChain = exceptionChain;
        this.surveyStateJSON = surveyStateJSON;
    }
}
