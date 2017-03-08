/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import uk.ac.ncl.openlab.intake24.client.ui.LoadingWidget;
import uk.ac.ncl.openlab.intake24.client.ui.WidgetFactory;

public class LoadingPanel extends Composite {

	public LoadingPanel(final String message) {
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("100%");
		
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		panel.add(WidgetFactory.createLoadingPanelText(SafeHtmlUtils.fromString(message)));
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(new LoadingWidget());
		
		initWidget(panel);
	}
}
