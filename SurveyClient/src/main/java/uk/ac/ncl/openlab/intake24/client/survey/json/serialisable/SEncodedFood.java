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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Either;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;

import static org.workcraft.gwt.shared.client.CollectionUtils.map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import uk.ac.ncl.openlab.intake24.client.survey.CompletedPortionSize;
import uk.ac.ncl.openlab.intake24.client.survey.EncodedFood;
import uk.ac.ncl.openlab.intake24.client.survey.FoodPrompt;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSize;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.PortionSizeScriptManager;

@JsonTypeName("encoded")
public class SEncodedFood extends SFoodEntry {

	@JsonProperty
	public final SFoodData data;
	@JsonProperty
	public final Option<Integer> portionSizeMethodIndex;
	@JsonProperty
	public final Option<Either<SPortionSize, SCompletedPortionSize>> portionSize;
	@JsonProperty
	public final Option<String> brand;
	@JsonProperty
	public final String searchTerm;
	@JsonProperty
	public final PVector<SFoodPrompt> enabledPrompts;

	@JsonCreator
	public SEncodedFood(
			@JsonProperty("data") SFoodData data,
			@JsonProperty("link") SFoodLink link,
			@JsonProperty("portionSizeMethodIndex") Option<Integer> portionSizeMethodIndex,
			@JsonProperty("portionSize") Option<Either<SPortionSize, SCompletedPortionSize>> portionSize,
			@JsonProperty("brand") Option<String> brand,
			@JsonProperty("searchTerm") String searchTerm,
			@JsonProperty("enabledPrompts") List<SFoodPrompt> enabledPrompts,
			@JsonProperty("flags") Set<String> flags,
			@JsonProperty("customData") Map<String, String> customData) {
		super(link, HashTreePSet.from(flags), HashTreePMap.from(customData));
		this.data = data;
		this.portionSizeMethodIndex = portionSizeMethodIndex;
		this.portionSize = portionSize;
		this.brand = brand;
		this.searchTerm = searchTerm;
		this.enabledPrompts = TreePVector.from(enabledPrompts);
	}

	private static Option<Either<SPortionSize, SCompletedPortionSize>> toSerialisable(
			Option<Either<PortionSize, CompletedPortionSize>> portionSize) {
		return portionSize.map(new Function1<Either<PortionSize, CompletedPortionSize>, Either<SPortionSize, SCompletedPortionSize>>() {
					@Override
					public Either<SPortionSize, SCompletedPortionSize> apply(Either<PortionSize, CompletedPortionSize> argument) {
						return argument.accept(new Either.Visitor<PortionSize, CompletedPortionSize, Either<SPortionSize, SCompletedPortionSize>>() {
							@Override
							public Either<SPortionSize, SCompletedPortionSize> visitRight(CompletedPortionSize value) {
								return new Either.Right<SPortionSize, SCompletedPortionSize>(new SCompletedPortionSize(value));
							}

							@Override
							public Either<SPortionSize, SCompletedPortionSize> visitLeft(PortionSize value) {
								return new Either.Left<SPortionSize, SCompletedPortionSize>(new SPortionSize(value));
							}
						});
					}});
	}
	
	private static Option<Either<PortionSize, CompletedPortionSize>> toRuntime(Option<Either<SPortionSize, SCompletedPortionSize>> portionSize, final PortionSizeScriptManager scriptManager) {
		return portionSize.map(new Function1<Either<SPortionSize, SCompletedPortionSize>, Either<PortionSize, CompletedPortionSize>> () {
			@Override
			public Either<PortionSize, CompletedPortionSize> apply(Either<SPortionSize, SCompletedPortionSize> argument) {
				return argument.accept(new Either.Visitor<SPortionSize, SCompletedPortionSize, Either<PortionSize, CompletedPortionSize>> () {
					@Override
					public Either<PortionSize, CompletedPortionSize> visitRight(SCompletedPortionSize value) {
						return new Either.Right<PortionSize, CompletedPortionSize>(value.toCompletedPortionSize());
					}

					@Override
					public Either<PortionSize, CompletedPortionSize> visitLeft(SPortionSize value) {
						return new Either.Left<PortionSize, CompletedPortionSize>(value.toPortionSize(scriptManager));
					}					
				});
			}			
		});
	}

	public SEncodedFood(EncodedFood food) {
		this(
				new SFoodData(food.data),
				new SFoodLink(food.link),
				food.portionSizeMethodIndex, 
				toSerialisable(food.portionSize),
				food.brand,
				food.searchTerm,
				map(food.enabledPrompts, new Function1<FoodPrompt, SFoodPrompt>() {
					@Override
					public SFoodPrompt apply(FoodPrompt argument) {
						return new SFoodPrompt(argument);
					}			
				}),
				food.flags,
				food.customData
			);			
	}
	
	public EncodedFood toEncodedFood(PortionSizeScriptManager scriptManager) {
		return new EncodedFood(data.toFoodData(), link.toFoodLink(), portionSizeMethodIndex, toRuntime(portionSize, scriptManager), brand, searchTerm,
				map(enabledPrompts, new Function1<SFoodPrompt, FoodPrompt>() {
					@Override
					public FoodPrompt apply(SFoodPrompt argument) {
						return argument.toFoodPrompt();
					}
					
				}), 
				flags, 
				customData);
	}

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visitEncoded(this);
	}
}
