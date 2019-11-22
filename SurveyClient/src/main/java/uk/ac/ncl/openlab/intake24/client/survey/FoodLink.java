/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import org.workcraft.gwt.shared.client.Option;

public class FoodLink {
	public final UUID id;
	public final Option<UUID> linkedTo;

	public FoodLink(UUID id, Option<UUID> linkedTo) {
		this.id = id;
		this.linkedTo = linkedTo;
	}
	
	public boolean isLinked() {
		return !linkedTo.isEmpty();
	}

	public static FoodLink newUnlinked() {
		return new FoodLink(UUID.randomUUID(), Option.<UUID> none());
	}

	public static FoodLink newLinked(UUID linkedTo) {
		return new FoodLink(UUID.randomUUID(), Option.some(linkedTo));
	}

	public FoodLink relink(UUID to) {
		return new FoodLink(id, Option.some(to));
	}

	@Override
	public String toString() {
		return "FoodLink [id=" + id + ", linkedTo=" + linkedTo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodLink other = (FoodLink) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linkedTo == null) {
			if (other.linkedTo != null)
				return false;
		} else if (!linkedTo.equals(other.linkedTo))
			return false;
		return true;
	}	
}
