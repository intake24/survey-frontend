/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package org.workcraft.gwt.slidingscale.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.slidingscale.shared.SlidingScaleDef;

public class CanvasSlidingScale extends Composite {

    private final static int CANVAS_WIDTH = 1000;

    private final double limit;
    private final Canvas canvas;
    private final Context2d ctx;

    public double getValue() {
        return 0;
    }

    public void setValue(double value) {
    }

    native void consoleLog(String message) /*-{
        console.log(message);
    }-*/;

    public CanvasSlidingScale(final SlidingScaleDef definition, double limit, double initialLevel, final Function1<Double, String> labelfunc) {
        this.limit = Math.min(1.0, Math.max(0.0, limit));

        FlowPanel container = new FlowPanel();
        container.addStyleName("intake24-sliding-scale-container");

        canvas = Canvas.createIfSupported();

        canvas.getContext2d().fillText("ЖОПА", 0, 0);

        double aspect = (double) definition.imageHeight / (double) definition.imageWidth;

        canvas.getCanvasElement().setWidth(CANVAS_WIDTH);
        canvas.getCanvasElement().setHeight((int) (CANVAS_WIDTH * aspect));
        canvas.getCanvasElement().addClassName("intake24-sliding-scale-canvas");

        ctx = canvas.getContext2d();

        final Image baseImage = new Image(definition.baseImageUrl);
        baseImage.setVisible(false);


        baseImage.addLoadHandler(new LoadHandler() {
            @Override
            public void onLoad(LoadEvent loadEvent) {
                consoleLog("ЖОПА");
                ctx.drawImage(ImageElement.as(baseImage.getElement()), 0, 0);
            }
        });

        container.add(canvas);
        container.add(baseImage);

        initWidget(container);
    }
}
