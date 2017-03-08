/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.json.serialisable;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import uk.ac.ncl.openlab.intake24.client.survey.Selection;
import uk.ac.ncl.openlab.intake24.client.survey.SelectionMode;

@JsonSubTypes({@Type(SerialisableSelection.SerialisableSelectedMeal.class), @Type(SerialisableSelection.SerialisableSelectedFood.class), @Type(SerialisableSelection.SerialisableEmptySelection.class)})
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "selectionType")
public abstract class SerialisableSelection {
    public interface Visitor<R> {
        public R visitMeal(SerialisableSelectedMeal meal);

        public R visitFood(SerialisableSelectedFood food);

        public R visitNothing(SerialisableEmptySelection selection);
    }

    @JsonTypeName("food")
    public static class SerialisableSelectedFood extends SerialisableSelection {
        @JsonProperty
        public final int mealIndex;
        @JsonProperty
        public final int foodIndex;

        @JsonCreator
        public SerialisableSelectedFood(@JsonProperty("mealIndex") int mealIndex, @JsonProperty("foodIndex") int foodIndex, @JsonProperty("selectionMode") SelectionMode selectionMode) {
            super(selectionMode);
            this.mealIndex = mealIndex;
            this.foodIndex = foodIndex;
        }

        public SerialisableSelectedFood(Selection.SelectedFood selectedFood) {
            this(selectedFood.mealIndex, selectedFood.foodIndex, selectedFood.selectionMode);
        }

        public Selection.SelectedFood toSelectedFood() {
            return new Selection.SelectedFood(mealIndex, foodIndex, selectionMode);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFood(this);
        }
    }

    @JsonTypeName("meal")
    public static class SerialisableSelectedMeal extends SerialisableSelection {
        @JsonProperty
        public final int mealIndex;

        @JsonCreator
        public SerialisableSelectedMeal(@JsonProperty("mealIndex") int mealIndex, @JsonProperty("selectionMode") SelectionMode selectionMode) {
            super(selectionMode);
            this.mealIndex = mealIndex;
        }

        public SerialisableSelectedMeal(Selection.SelectedMeal selectedMeal) {
            this(selectedMeal.mealIndex, selectedMeal.selectionMode);
        }

        public Selection.SelectedMeal toSelectedMeal() {
            return new Selection.SelectedMeal(mealIndex, selectionMode);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitMeal(this);
        }
    }

    @JsonTypeName("empty")
    public static class SerialisableEmptySelection extends SerialisableSelection {

        @JsonCreator
        public SerialisableEmptySelection(@JsonProperty("selectionMode") final SelectionMode selectionMode) {
            super(selectionMode);
        }

        public SerialisableEmptySelection(Selection.EmptySelection emptySelection) {
            this(emptySelection.selectionMode);
        }

        public Selection.EmptySelection toEmptySelection() {
            return new Selection.EmptySelection(selectionMode);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitNothing(this);
        }
    }

    public final SelectionMode selectionMode;

    public abstract <R> R accept(Visitor<R> visitor);

    public SerialisableSelection(SelectionMode type) {
        this.selectionMode = type;
    }

}