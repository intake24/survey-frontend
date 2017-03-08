/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.api.foods;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DrinkwareDef implements IsSerializable {
	public String guide_id;
	public DrinkScaleDef[] scaleDefs;
	
	@Deprecated
	public DrinkwareDef() { };

	public DrinkwareDef(String guide_id, DrinkScaleDef[] scaleDefs) {
		this.guide_id = guide_id;
		this.scaleDefs = scaleDefs;
	}
}
