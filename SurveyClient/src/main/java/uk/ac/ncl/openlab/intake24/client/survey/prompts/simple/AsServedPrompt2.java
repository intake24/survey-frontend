/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.api.foods.AsServedImage;
import uk.ac.ncl.openlab.intake24.client.survey.ShepherdTour;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;


public class AsServedPrompt2 implements SimplePrompt<AsServed2Result> {
    private static final HelpMessages helpMessages = HelpMessages.INSTANCE;

    private final AsServedImage[] images;
    private final SafeHtml promptText;
    private final String prevButtonLabel;
    private final String nextButtonLabel;
    private final String confirmButtonLabel;
    private final boolean moreThanOptionEnabled;

    private FlowPanel[] imageDivs;
    private Image[] thumbs;
    private Button nextButton;
    private Button prevButton;
    private Button confirmButton;
    private FlowPanel imageContainer;
    private FlowPanel thumbsContainer;

    private int index;
    private boolean animation = false;

    public AsServedPrompt2(AsServedImage[] images, SafeHtml promptText, String prevButtonLabel, String nextButtonLabel,
                           String confirmButtonLabel, boolean moreThanOptionEnabled) {
        this.images = images;
        this.promptText = promptText;
        this.prevButtonLabel = prevButtonLabel;
        this.nextButtonLabel = nextButtonLabel;
        this.confirmButtonLabel = confirmButtonLabel;
        this.moreThanOptionEnabled = moreThanOptionEnabled;
    }

    private final static PVector<ShepherdTour.Step> tour = TreePVector
            .<ShepherdTour.Step>empty()
            .plus(new ShepherdTour.Step("image", "#intake24-as-served-image-container", helpMessages.asServed_imageTitle(), helpMessages
                    .asServed_imageDescription()))
            .plus(new ShepherdTour.Step("label", "#intake24-as-served-image-container", helpMessages.asServed_labelTitle(), helpMessages
                    .asServed_labelDescription(), "top right", "bottom right"))
            .plus(new ShepherdTour.Step("thumbs", "#intake24-as-served-thumbs-container", helpMessages.asServed_thumbsTitle(), helpMessages
                    .asServed_thumbsDescription()))
            .plus(new ShepherdTour.Step("hadLess", "#intake24-as-served-prev-button", helpMessages.asServed_prevButtonTitle(), helpMessages
                    .asServed_prevButtonDescription(), "bottom left", "top left"))
            .plus(new ShepherdTour.Step("hadMore", "#intake24-as-served-next-button", helpMessages.asServed_nextButtonTitle(), helpMessages
                    .asServed_nextButtonDescription(), "bottom left", "top left"))
            .plus(new ShepherdTour.Step("hadThisMuch", "#intake24-as-served-confirm-button", helpMessages.asServed_confirmButtonTitle(), helpMessages
                    .asServed_confirmButtonDescription(), "bottom right", "top right"));

    private void transition(final int newIndex) {
        if (animation || index == newIndex)
            return;

        animation = true;

        if (newIndex == 0)
            prevButton.setEnabled(false);
        else
            prevButton.setEnabled(true);

        if (newIndex == imageDivs.length - 1)
            nextButton.setEnabled(false);
        else
            nextButton.setEnabled(true);

        imageDivs[newIndex].getElement().getStyle().clearDisplay();
        imageDivs[newIndex].getElement().getStyle().setOpacity(0);
        imageDivs[newIndex].getElement().getStyle().setZIndex(400);
        imageDivs[newIndex].addStyleName("intake24-as-served-image-overlay");

        thumbs[newIndex].addStyleName("intake24-as-served-thumbnail-selected");

        imageDivs[index].getElement().getStyle().setZIndex(399);

        thumbs[index].removeStyleName("intake24-as-served-thumbnail-selected");

        Animation fadeIn = new Animation() {
            @Override
            protected void onUpdate(double progress) {
                imageDivs[newIndex].getElement().getStyle().setOpacity(progress);
            }

            @Override
            protected void onComplete() {
                imageDivs[newIndex].getElement().getStyle().setOpacity(1);
                imageDivs[newIndex].removeStyleName("intake24-as-served-image-overlay");

                imageDivs[index].getElement().getStyle().setDisplay(Display.NONE);
                imageDivs[index].getElement().getStyle().setZIndex(0);

                index = newIndex;
                animation = false;
            }
        };

        fadeIn.run(400);
    }

    private void prev() {
        int newIndex = index;
        if (newIndex > 0) {
            newIndex--;
            transition(newIndex);
        }
    }

    private void next() {
        int newIndex = index;
        if (newIndex < imageDivs.length - 1) {
            newIndex++;
            transition(newIndex);
        }
    }

    @Override
    public FlowPanel getInterface(final Callback1<AsServed2Result> onComplete) {
        final FlowPanel content = new FlowPanel();

//        PromptMessages.INSTANCE.asServed_servedLessButtonLabel(), PromptMessages.INSTANCE.asServed_servedMoreButtonLabel(),
  //              PromptMessages.INSTANCE.asServed_servedContinueButtonLabel(),

        FlowPanel promptPanel = WidgetFactory.createPromptPanel(promptText, ShepherdTour.createTourButton(tour, AsServedPrompt2.class.getSimpleName()));
        content.add(promptPanel);

        FlowPanel asServedContainer = new FlowPanel();
        asServedContainer.addStyleName("intake24-as-served-image-chooser");

        imageContainer = new FlowPanel();
        imageContainer.addStyleName("intake24-as-served-image-container");
        imageContainer.getElement().setId("intake24-as-served-image-container");

        thumbsContainer = new FlowPanel();
        thumbsContainer.addStyleName("intake24-as-served-thumbs-container");
        thumbsContainer.getElement().setId("intake24-as-served-thumbs-container");

        prevButton = WidgetFactory.createButton(prevButtonLabel, "intake24-as-served-prev-button", e -> prev());
        nextButton = WidgetFactory.createButton(nextButtonLabel, "intake24-as-served-next-button", e -> next());
        confirmButton = WidgetFactory.createGreenButton(confirmButtonLabel, "intake24-as-served-confirm-button", e -> {
            //onComplete.call(new AsServed2Result(index, def.));
        });

        asServedContainer.add(imageContainer);
        asServedContainer.add(thumbsContainer);
        asServedContainer.add(WidgetFactory.createButtonsPanel(prevButton, nextButton, confirmButton));

        imageDivs = new FlowPanel[images.length];
        thumbs = new Image[images.length];
        final NumberFormat nf = NumberFormat.getDecimalFormat();

        for (int i = 0; i < images.length; i++) {
            imageDivs[i] = new FlowPanel();
            imageDivs[i].getElement().getStyle().setDisplay(Display.NONE);

            Image image = new Image(images[i].mainImageUrl);
            image.addStyleName("intake24-as-served-image");
            imageDivs[i].add(image);

            Label label = new Label(nf.format(Math.round(images[i].weight)) + " " + PromptMessages.INSTANCE.asServed_weightUnitLabel());

            label.addStyleName("intake24-as-served-image-label");
            label.getElement().setId("intake24-as-served-image-label");

            imageDivs[i].add(label);

            imageContainer.add(imageDivs[i]);

            thumbs[i] = new Image(images[i].thumbnailUrl);

            final int k = i;

            thumbs[i].addClickHandler(e -> transition(k));

            thumbs[i].addStyleName("intake24-as-served-thumbnail");
            thumbsContainer.add(thumbs[i]);
        }

        int startIndex = images.length / 2;

        imageDivs[startIndex].getElement().getStyle().clearDisplay();
        thumbs[startIndex].addStyleName("intake24-as-served-thumbnail-selected");

        index = startIndex;

        content.add(asServedContainer);

        ShepherdTour.makeShepherdTarget(promptPanel, imageContainer, thumbsContainer, nextButton, prevButton, confirmButton);

        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "As served portion size prompt";
    }
}