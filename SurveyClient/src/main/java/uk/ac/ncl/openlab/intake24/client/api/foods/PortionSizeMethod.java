/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.api.foods;

import java.util.Map;

public class PortionSizeMethod {
	public String method;
	public Map<String, String> parameters;
	public String description;
	public String imageUrl;
	public boolean useForRecipes;
	
	@Deprecated
	public PortionSizeMethod() { }
	
	public PortionSizeMethod(String method, String description, String imageUrl, boolean useForRecipes, Map<String, String> params) {
		this.method = method;
		this.description = description;
		this.imageUrl = imageUrl;
		this.useForRecipes = useForRecipes;
		this.parameters = params;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortionSizeMethod other = (PortionSizeMethod) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (useForRecipes != other.useForRecipes)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PortionSizeMethod [method=" + method + ", params=" + parameters + ", description=" + description + ", imageUrl=" + imageUrl
				+ ", useForRecipes=" + useForRecipes + "]";
	}
}