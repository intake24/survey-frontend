package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import uk.ac.ncl.openlab.intake24.client.survey.UUID;

import java.util.Arrays;

public class TestEvent extends AbstractUxEvent<String> {

    @Deprecated
    public TestEvent() {
    }

    public TestEvent(String data) {
        super( Arrays.asList("debug"), data);
    }
}
