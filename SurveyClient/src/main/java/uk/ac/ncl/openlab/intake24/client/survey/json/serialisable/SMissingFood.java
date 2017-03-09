/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
 */

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.MissingFood;
import uk.ac.ncl.openlab.intake24.client.survey.MissingFoodDescription;

import java.util.Map;
import java.util.Set;

@JsonTypeName("missing")
public class SMissingFood extends SFoodEntry {
    @JsonProperty
    public final String name;
    @JsonProperty
    public final Option<SMissingFoodDescription> description;
    @JsonProperty
    public final boolean isDrink;

    @JsonCreator
    public SMissingFood(@JsonProperty("link") SFoodLink link, @JsonProperty("name") String name, @JsonProperty("isDrink") boolean isDrink,
                        @JsonProperty("description") Option<SMissingFoodDescription> description, @JsonProperty("flags") Set<String> flags, @JsonProperty("customData") Map<String, String> customData) {
        super(link, HashTreePSet.from(flags), HashTreePMap.from(customData));

        this.name = name;
        this.description = description;
        this.isDrink = isDrink;
    }

    public SMissingFood(MissingFood food) {
        this(new SFoodLink(food.link), food.name, food.isDrink, food.description.map(new Function1<MissingFoodDescription, SMissingFoodDescription>() {
            @Override
            public SMissingFoodDescription apply(MissingFoodDescription argument) {
                return new SMissingFoodDescription(argument);
            }
        }), food.flags, food.customData);
    }

    public MissingFood toMissingFood() {
        return new MissingFood(link.toFoodLink(), name, isDrink, description.map(new Function1<SMissingFoodDescription, MissingFoodDescription>() {
            @Override
            public MissingFoodDescription apply(SMissingFoodDescription argument) {
                return argument.toMissingFoodDescription();
            }
        }), flags, customData);
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitMissing(this);
    }
}
