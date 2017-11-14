package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import org.workcraft.gwt.shared.client.Option;

public class ContainerPosition {
    public int top;
    public int left;
    public int offsetHeight;

    @Deprecated
    public ContainerPosition() {
    }

    public ContainerPosition(int top, int left, int offsetHeight) {
        this.top = top;
        this.left = left;
        this.offsetHeight = offsetHeight;
    }

    public static Option<ContainerPosition> fromElement(String id) {
        Element e = Document.get().getElementById(id);

        if (e != null)
            return Option.some(new ContainerPosition(e.getOffsetTop(), e.getOffsetLeft(), e.getOffsetHeight()));
        else
            return Option.none();
    }
}
