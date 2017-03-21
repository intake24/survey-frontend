/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.prompts;

import org.workcraft.gwt.shared.client.Callback1;
import org.workcraft.gwt.shared.client.Function1;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSize;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class UnknownPortionSizeMethodPrompt implements Prompt<FoodEntry, FoodOperation> {
	private final String description;
	private final PromptMessages messages = GWT.create(PromptMessages.class);
	
	public UnknownPortionSizeMethodPrompt(String description) {
		this.description = description;
	}

	public SurveyStageInterface getInterface(final Callback1<FoodOperation> onComplete,
											 final Callback1<Function1<FoodEntry, FoodEntry>> onIntermediateStateChange) {

		final FlowPanel content = new FlowPanel();
		
		final Button contButton = WidgetFactory.createButton (messages.noPortionMethod_continueButtonLabel(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onComplete.call(FoodOperation.updateEncoded(new Function1<EncodedFood, EncodedFood>() {
					@Override
					public EncodedFood apply(EncodedFood argument) {
						return argument.withPortionSize(PortionSize.complete( CompletedPortionSize.ignore("No porton size estimation method defined for " + description)));
					}
				}));
			}
		});
		
		content.add(WidgetFactory.createPromptPanel(SafeHtmlUtils.fromSafeConstant(messages.noPortionMethod_promptText(SafeHtmlUtils.htmlEscape(description)))));
		content.add(WidgetFactory.createButtonsPanel(contButton));

		return new SurveyStageInterface.Aligned(content, HasHorizontalAlignment.ALIGN_LEFT, HasVerticalAlignment.ALIGN_TOP,
				SurveyStageInterface.DEFAULT_OPTIONS, UnknownPortionSizeMethodPrompt.class.getSimpleName());
	}

	@Override
	public String toString() {
		return "Unknown portion size method prompt";
	}
}