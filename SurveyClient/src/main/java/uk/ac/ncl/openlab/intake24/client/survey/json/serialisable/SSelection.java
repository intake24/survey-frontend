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

@JsonSubTypes({@Type(SSelection.SSelectedMeal.class), @Type(SSelection.SSelectedFood.class), @Type(SSelection.SEmptySelection.class)})
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "selectionType")
public abstract class SSelection {
    public interface Visitor<R> {
        public R visitMeal(SSelectedMeal meal);

        public R visitFood(SSelectedFood food);

        public R visitNothing(SEmptySelection selection);
    }

    @JsonTypeName("food")
    public static class SSelectedFood extends SSelection {
        @JsonProperty
        public final int mealIndex;
        @JsonProperty
        public final int foodIndex;

        @JsonCreator
        public SSelectedFood(@JsonProperty("mealIndex") int mealIndex, @JsonProperty("foodIndex") int foodIndex, @JsonProperty("selectionMode") SelectionMode selectionMode) {
            super(selectionMode);
            this.mealIndex = mealIndex;
            this.foodIndex = foodIndex;
        }

        public SSelectedFood(Selection.SelectedFood selectedFood) {
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
    public static class SSelectedMeal extends SSelection {
        @JsonProperty
        public final int mealIndex;

        @JsonCreator
        public SSelectedMeal(@JsonProperty("mealIndex") int mealIndex, @JsonProperty("selectionMode") SelectionMode selectionMode) {
            super(selectionMode);
            this.mealIndex = mealIndex;
        }

        public SSelectedMeal(Selection.SelectedMeal selectedMeal) {
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
    public static class SEmptySelection extends SSelection {

        @JsonCreator
        public SEmptySelection(@JsonProperty("selectionMode") final SelectionMode selectionMode) {
            super(selectionMode);
        }

        public SEmptySelection(Selection.EmptySelection emptySelection) {
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

    public SSelection(SelectionMode type) {
        this.selectionMode = type;
    }

}