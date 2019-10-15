/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package org.workcraft.gwt.slidingscale.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.slidingscale.shared.SlidingScaleDef;

public class CanvasSlidingScale extends Composite {

    private final static int SLIDER_WIDTH = 40;
    private final static int SLIDER_PADDING = 50;
    private final static int POINTER_SIZE = 20;

    private final static int CANVAS_WIDTH = 1000;
    private final int CANVAS_HEIGHT;

    private final SlidingScaleDef definition;
    private final Function1<Double, String> labelFunc;

    private final double limit;
    private final double imageScale;
    private final Canvas canvas;
    private final Context2d ctx;
    private final Image baseImage;
    private final Image overlayImage;
    public final Label label;

    private boolean baseImageLoaded = false;
    private boolean overlayImageLoaded = false;

    private double fillLevel;
    private boolean pointerMoving;

    public double getValue() {
        return fillLevel;
    }

    public void setValue(double value) {
        fillLevel = Math.min(limit, Math.max(0.0, value));
        label.setText(labelFunc.apply(fillLevel));
        redrawCanvas();
    }

    native void consoleLog(String message) /*-{
        console.log(message);
    }-*/;

    private static native JavaScriptObject getBoundingClientRect(Element element) /*-{
        return element.getBoundingClientRect();
    }-*/;

    private static native double getPropertyAsNumber(JavaScriptObject jso, String property) /*-{
        return jso[property];
    }-*/;


    public CanvasSlidingScale(final SlidingScaleDef definition, double limit, double initialLevel, final Function1<Double, String> labelFunc) {
        this.limit = Math.min(1.0, Math.max(0.0, limit));
        this.definition = definition;
        this.labelFunc = labelFunc;

        FlowPanel container = new FlowPanel();
        container.addStyleName("intake24-sliding-scale-container");

        canvas = Canvas.createIfSupported();

        canvas.getElement().setId("intake24-sliding-scale-image");;

        double aspect = (double) definition.imageHeight / (double) definition.imageWidth;
        imageScale = (double) CANVAS_WIDTH / (double) definition.imageWidth;

        CANVAS_HEIGHT = (int) (CANVAS_WIDTH * aspect);

        canvas.getCanvasElement().setWidth(CANVAS_WIDTH);
        canvas.getCanvasElement().setHeight(CANVAS_HEIGHT);
        canvas.getCanvasElement().addClassName("intake24-sliding-scale-canvas");

        ctx = canvas.getContext2d();

        baseImage = new Image(definition.baseImageUrl);
        baseImage.setVisible(false);

        overlayImage = new Image(definition.overlayUrl);
        overlayImage.setVisible(false);

        baseImage.addLoadHandler(loadEvent -> {
            baseImageLoaded = true;
            redrawCanvas();
        });

        overlayImage.addLoadHandler(loadEvent -> {
            overlayImageLoaded = true;
            redrawCanvas();
        });

        canvas.addMouseDownHandler(mouseDownEvent -> {
            if (mouseDownEvent.getNativeButton() == 1)
                pointerMoving = true;
        });

        canvas.addMouseUpHandler(mouseUpEvent -> {
            if (mouseUpEvent.getNativeButton() == 1)
                pointerMoving = false;
        });

        canvas.addMouseOutHandler(mouseOutEvent -> {
            pointerMoving = false;
        });

        canvas.addMouseMoveHandler(mouseMoveEvent -> {
            if (pointerMoving) {
                JavaScriptObject rect = getBoundingClientRect(canvas.getCanvasElement());

                double canvasY = mouseMoveEvent.getClientY() - getPropertyAsNumber(rect, "top");
                double canvasHeight = getPropertyAsNumber(rect, "height");
                double relativePosition = 1.0 - Math.min(1, Math.max(canvasY / canvasHeight, 0));

                double relativeMin = (double) definition.emptyLevel / definition.imageHeight;
                double relativeMax = (double) definition.fullLevel / definition.imageHeight;

                setValue((relativePosition - relativeMin) / (relativeMax - relativeMin));
            }
        });

        label = new Label(labelFunc.apply(fillLevel));
        label.addStyleName("intake24-as-served-image-label");
        label.getElement().setId("intake24-sliding-scale-label");

        setValue(initialLevel);

        container.add(canvas);
        container.add(label);
        container.add(baseImage);
        container.add(overlayImage);

        initWidget(container);
    }


    private void redrawCanvas() {
        if (baseImageLoaded && overlayImageLoaded) {
            ctx.drawImage(ImageElement.as(baseImage.getElement()), 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

            int liquidSegment = definition.fullLevel - definition.emptyLevel;
            int liquidLevel = (int) (fillLevel * liquidSegment);

            int srcX = 0;
            int srcY = definition.imageHeight - (liquidLevel + definition.emptyLevel);
            int srcWidth = definition.imageWidth;
            int srcHeight = definition.imageHeight - srcY;

            int dstX = 0;
            int dstY = (int) (srcY * imageScale);
            int dstWidth = (int) (srcWidth * imageScale);
            int dstHeight = (int) (srcHeight * imageScale);

            ctx.drawImage(ImageElement.as(overlayImage.getElement()), srcX, srcY, srcWidth, srcHeight,
                    dstX, dstY, dstWidth, dstHeight);

            int sliderTop = (int) (imageScale * (definition.imageHeight - (limit * liquidSegment) - definition.emptyLevel));
            int sliderHeight = (int) (liquidSegment * limit * imageScale);

            int pointerY = dstY;

            int sliderLeft = CANVAS_WIDTH - SLIDER_WIDTH - SLIDER_PADDING;

            ctx.setFillStyle("#aaaaaaaa");
            ctx.fillRect(sliderLeft, sliderTop, SLIDER_WIDTH, sliderHeight);

            ctx.setFillStyle("#eeeeee");
            ctx.setStrokeStyle("#000000");
            ctx.setLineWidth(3);
            ctx.setLineJoin("round");
            ctx.beginPath();
            ctx.moveTo(sliderLeft - POINTER_SIZE, pointerY);
            ctx.lineTo(sliderLeft, pointerY + POINTER_SIZE);
            ctx.lineTo(sliderLeft + SLIDER_WIDTH + POINTER_SIZE / 2, pointerY + POINTER_SIZE);
            ctx.lineTo(sliderLeft + SLIDER_WIDTH + POINTER_SIZE / 2, pointerY - POINTER_SIZE);
            ctx.lineTo(sliderLeft, pointerY - POINTER_SIZE);
            ctx.closePath();
            ctx.fill();
            ctx.stroke();

        }
    }
}
