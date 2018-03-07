package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import org.workcraft.gwt.shared.client.Option;

public class BackButtonData {
    public Viewport viewport;

    public Option<ContainerPosition> foodsContainer;
    public Option<ContainerPosition> categoriesContainer;
    public Option<ContainerPosition> buttonsContainer;

    @Deprecated
    public BackButtonData() {
    }

    public BackButtonData(Viewport viewport,
                          Option<ContainerPosition> foodsContainer,
                          Option<ContainerPosition> categoriesContainer,
                          Option<ContainerPosition> buttonsContainer) {
        this.viewport = Viewport.getCurrent();
        this.foodsContainer = foodsContainer;
        this.categoriesContainer = categoriesContainer;
        this.buttonsContainer = buttonsContainer;
    }
}
