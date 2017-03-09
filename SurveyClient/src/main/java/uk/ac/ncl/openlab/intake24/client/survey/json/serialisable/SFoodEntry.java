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

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.pcollections.PMap;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.workcraft.gwt.shared.client.Function1;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

@JsonSubTypes({@Type(SRawFood.class), @Type(SEncodedFood.class), @Type(SCompoundFood.class),
        @Type(STemplateFood.class), @Type(SMissingFood.class)})
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "entryType")
public abstract class SFoodEntry {

    @JsonProperty
    public final SFoodLink link;
    @JsonProperty
    public final PSet<String> flags;
    @JsonProperty
    public final PMap<String, String> customData;

    public interface Visitor<T> {
        public T visitRaw(SRawFood food);

        public T visitEncoded(SEncodedFood food);

        public T visitCompound(SCompoundFood food);

        public T visitTemplate(STemplateFood food);

        public T visitMissing(SMissingFood food);
    }

    public SFoodEntry(SFoodLink link, PSet<String> flags, PMap<String, String> customData) {
        this.link = link;
        this.flags = flags;
        this.customData = customData;
    }

    abstract public <T> T accept(Visitor<T> visitor);

    public FoodEntry toFoodEntry(final PortionSizeScriptManager scriptManager, final CompoundFoodTemplateManager templateManager) {
        return accept(new Visitor<FoodEntry>() {
            @Override
            public FoodEntry visitRaw(SRawFood food) {
                return food.toRawFood();
            }

            @Override
            public FoodEntry visitEncoded(SEncodedFood food) {
                return food.toEncodedFood(scriptManager);
            }

            @Override
            public FoodEntry visitCompound(SCompoundFood food) {
                return food.toCompoundFood();
            }

            @Override
            public FoodEntry visitTemplate(STemplateFood food) {
                return food.toTemplateFood(templateManager);
            }

            @Override
            public FoodEntry visitMissing(SMissingFood food) {
                return food.toMissingFood();
            }
        });
    }

    public static PVector<SFoodEntry> toSerialisable(PVector<FoodEntry> foods) {
        return map(foods, new Function1<FoodEntry, SFoodEntry>() {
            @Override
            public SFoodEntry apply(FoodEntry argument) {
                return argument.accept(new FoodEntry.Visitor<SFoodEntry>() {
                    @Override
                    public SFoodEntry visitRaw(RawFood food) {
                        return new SRawFood(food);
                    }

                    @Override
                    public SFoodEntry visitEncoded(EncodedFood food) {
                        return new SEncodedFood(food);
                    }

                    @Override
                    public SFoodEntry visitCompound(CompoundFood food) {
                        return new SCompoundFood(food);
                    }

                    @Override
                    public SFoodEntry visitTemplate(TemplateFood food) {
                        return new STemplateFood(food);
                    }

                    @Override
                    public SFoodEntry visitMissing(MissingFood food) {
                        return new SMissingFood(food);
                    }
                });
            }
        });
    }

    public static PVector<FoodEntry> toRuntime(PVector<SFoodEntry> foods, final PortionSizeScriptManager scriptManager, final CompoundFoodTemplateManager templateManager) {
        return map(foods, new Function1<SFoodEntry, FoodEntry>() {
            @Override
            public FoodEntry apply(SFoodEntry argument) {
                return argument.accept(new Visitor<FoodEntry>() {
                    @Override
                    public FoodEntry visitRaw(SRawFood food) {
                        return food.toRawFood();
                    }

                    @Override
                    public FoodEntry visitEncoded(SEncodedFood food) {
                        return food.toEncodedFood(scriptManager);
                    }

                    @Override
                    public FoodEntry visitCompound(SCompoundFood food) {
                        return food.toCompoundFood();
                    }

                    @Override
                    public FoodEntry visitTemplate(STemplateFood food) {
                        return food.toTemplateFood(templateManager);
                    }

                    @Override
                    public FoodEntry visitMissing(SMissingFood food) {
                        return food.toMissingFood();
                    }
                });
            }
        });
    }
}
