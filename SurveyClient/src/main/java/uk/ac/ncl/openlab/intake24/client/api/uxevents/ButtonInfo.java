package uk.ac.ncl.openlab.intake24.client.api.uxevents;

public class ButtonInfo<T> {
    String buttonId;
    T data;

    @Deprecated
    public ButtonInfo() {
    }

    public ButtonInfo(String buttonId, T data) {
        this.buttonId = buttonId;
        this.data = data;
    }
}
