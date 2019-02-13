/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.EnergyValidationPrompt;

public class ShowEnergyValidationPrompt implements PromptRule<Survey, SurveyOperation> {
    final private double energyValueThreshold;

    public ShowEnergyValidationPrompt(double energyValueThreshold) {
        this.energyValueThreshold = energyValueThreshold;
    }

    private double totalEnergyValue(Survey survey) {
        double sum = 0.0;

        for (Meal m : survey.meals)
            for (FoodEntry f : m.foods) {
                sum += f.accept(new FoodEntry.Visitor<Double>() {
                    @Override
                    public Double visitRaw(RawFood food) {
                        return 0.0;
                    }

                    @Override
                    public Double visitEncoded(EncodedFood food) {

                        if (food.data.categories.contains(SpecialData.FOOD_CODE_MILK_IN_HOT_DRINK))
                            return 0.0;
                        else {
                            CompletedPortionSize completedPortionSize = food.completedPortionSize();
                            return food.data.caloriesPer100g * (completedPortionSize.servingWeight() - completedPortionSize.leftoversWeight()) / 100.0;
                        }
                    }

                    @Override
                    public Double visitTemplate(TemplateFood food) {
                        return 0.0;
                    }

                    @Override
                    public Double visitMissing(MissingFood food) {
                        return 0.0;
                    }

                    @Override
                    public Double visitCompound(CompoundFood food) {
                        return 0.0;
                    }
                });
            }

        return sum;
    }

    @Override
    public Option<Prompt<Survey, SurveyOperation>> apply(Survey state, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (!state.portionSizeComplete() || state.meals.isEmpty() || state.energyValueConfirmed())
            return Option.none();
        else if (totalEnergyValue(state) < energyValueThreshold)
            return Option.<Prompt<Survey, SurveyOperation>>some(new EnergyValidationPrompt());
        else
            return Option.none();
    }

    @Override
    public String toString() {
        return "Show energy value confirmation prompt";
    }

    public static WithPriority<PromptRule<Survey, SurveyOperation>> withPriority(int priority, double energyValueThreshold) {
        return new WithPriority<PromptRule<Survey, SurveyOperation>>(new ShowEnergyValidationPrompt(energyValueThreshold), priority);
    }
}