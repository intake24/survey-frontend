/*
This file is part of Intake24.

Copyright 2015, 2016 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Callback1;
import uk.ac.ncl.openlab.intake24.client.survey.ShepherdTour;
import uk.ac.ncl.openlab.intake24.client.survey.SimplePrompt;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.HelpMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.widgets.QuantityCounter;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class FractionalQuantityPrompt implements SimplePrompt<Double> {
    private static final HelpMessages helpMessages = HelpMessages.Util.getInstance();

    private final static PVector<ShepherdTour.Step> tour = TreePVector.<ShepherdTour.Step>empty()
            .plus(new ShepherdTour.Step("wholeCounter", "#intake24-quantity-prompt-whole-counter", helpMessages.quantity_wholeCounterTitle(),
                    helpMessages.quantity_wholeCounterDescription()))
            .plus(new ShepherdTour.Step("fracCounter", "#intake24-quantity-prompt-frac-counter", helpMessages.quantity_fractionCounterTitle(),
                    helpMessages.quantity_fractionCounterDescription()))
            .plus(new ShepherdTour.Step("continueButton", "#intake24-quantity-prompt-continue-button", helpMessages.quantity_continueButtonTitle(),
                    helpMessages.quantity_continueButtonDescription()));

    final private SafeHtml promptText;
    final private String buttonLabel;

    public FractionalQuantityPrompt(SafeHtml promptText, String buttonLabel) {
        this.promptText = promptText;
        this.buttonLabel = buttonLabel;
    }

    @Override
    public FlowPanel getInterface(final Callback1<Double> onComplete) {
        final FlowPanel content = new FlowPanel();
        content.addStyleName("intake24-quantity-prompt");

        FlowPanel promptPanel = WidgetFactory.createPromptPanel(promptText,
                ShepherdTour.createTourButton(tour, FractionalQuantityPrompt.class.getSimpleName()));
        content.add(promptPanel);

        final FlowPanel amountPanel = new FlowPanel();
        amountPanel.addStyleName("intake24-quantity-prompt-counter-container");

        final QuantityCounter counter = new QuantityCounter(0.25, 30, 1.0);
        amountPanel.add(counter);

        Button cont = WidgetFactory.createGreenButton(buttonLabel, "fractionalQuantityContinueButton", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onComplete.call(counter.getValue());
            }
        });

        cont.getElement().setId("intake24-quantity-prompt-continue-button");

        content.add(amountPanel);
        content.add(WidgetFactory.createButtonsPanel(cont));

        ShepherdTour.makeShepherdTarget(promptPanel, counter.wholeCounter, counter.fractionalCounter, counter.wholeLabel, cont);

        return content;
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }
}