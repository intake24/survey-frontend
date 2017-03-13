/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.imagemap.shared.ImageMap;
import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.ShepherdTour;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class GuidePrompt implements SimplePrompt<Integer> {
    private final static HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final static PVector<ShepherdTour.Step> tour = TreePVector.<ShepherdTour.Step>empty()
            .plus(new ShepherdTour.Step("guidePrompt", "#intake24-guide-prompt-panel", helpMessages.guide_promptTitle(), helpMessages.guide_promptDescription()))
            .plus(new ShepherdTour.Step("guideImage", "#intake24-guide-image-map", helpMessages.guide_imageMapTitle(), helpMessages.guide_imageMapDescription(), false));

    private final SafeHtml promptText;
    private final ImageMap imageMapDef;
    private final Option<Function1<Callback1<Integer>, Panel>> additionalControlsCtor;

    public GuidePrompt(SafeHtml promptText, ImageMap imageMapDef) {
        this(promptText, imageMapDef, Option.<Function1<Callback1<Integer>, Panel>>none());
    }

    public GuidePrompt(SafeHtml promptText, ImageMap imageMapDef, Function1<Callback1<Integer>, Panel> additionalControlsCtor) {
        this(promptText, imageMapDef, Option.some(additionalControlsCtor));
    }

    private GuidePrompt(SafeHtml promptText, ImageMap imageMapDef, Option<Function1<Callback1<Integer>, Panel>> additionalControlsCtor) {
        this.promptText = promptText;
        this.imageMapDef = imageMapDef;
        this.additionalControlsCtor = additionalControlsCtor;
    }

    @Override
    public FlowPanel getInterface(final Callback1<Integer> onComplete) {
        final FlowPanel content = new FlowPanel();

        FlowPanel promptPanel = WidgetFactory.createPromptPanel(promptText, ShepherdTour.createTourButton(tour, GuidePrompt.class.getSimpleName()));
        ShepherdTour.makeShepherdTarget(promptPanel);
        promptPanel.getElement().setId("intake24-guide-prompt-panel");
        content.add(promptPanel);

        additionalControlsCtor.accept(new Option.SideEffectVisitor<Function1<Callback1<Integer>, Panel>>() {
            @Override
            public void visitSome(Function1<Callback1<Integer>, Panel> ctor) {
                content.add(ctor.apply(onComplete));
            }

            @Override
            public void visitNone() {
            }
        });

        org.workcraft.gwt.imagemap.client.ImageMap imageMap = new org.workcraft.gwt.imagemap.client.ImageMap(imageMapDef, new org.workcraft.gwt.imagemap.client.ImageMap.ResultHandler() {
            @Override
            public void handleResult(final int choice) {
                onComplete.call(choice);
            }
        });

        ShepherdTour.makeShepherdTarget(imageMap);
        imageMap.getElement().setId("intake24-guide-image-map");

        content.add(imageMap);

        return content;
    }
}