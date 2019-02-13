/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;


import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import org.pcollections.PSet;
import org.pcollections.PVector;
import org.pcollections.TreePVector;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.GoogleAnalytics;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionAnswer;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.MultipleChoiceQuestionOption;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.simple.RadioButtonPrompt;

public class AskIfHomeRecipeFlexibleRecall implements PromptRule<FoodEntry, FoodOperation> {

    /**
     * Experimental. Flexible recall. Compared to original ignores FLAG_FREE_ENTRY_COMPLETE.
     */

    private final PromptMessages messages = GWT.create(PromptMessages.class);

    @Override
    public Option<Prompt<FoodEntry, FoodOperation>> apply(FoodEntry data, SelectionMode selectionType, PSet<String> surveyFlags) {
        if (data.isMissing() && !(data.flags.contains(MissingFood.NOT_HOME_RECIPE_FLAG) || data.flags.contains(MissingFood.HOME_RECIPE_FLAG)))
            return new Option.Some<Prompt<FoodEntry, FoodOperation>>(buildPrompt(data.description(), data.isDrink()));
        else
            return new Option.None<Prompt<FoodEntry, FoodOperation>>();
    }

    @Override
    public String toString() {
        return "Ask if the missing food was a home-made dish";
    }

    public static WithPriority<PromptRule<FoodEntry, FoodOperation>> withPriority(int priority) {
        return new WithPriority<PromptRule<FoodEntry, FoodOperation>>(new AskIfHomeRecipeFlexibleRecall(), priority);
    }

    private Prompt<FoodEntry, FoodOperation> buildPrompt(final String foodName, final boolean isDrink) {
        PVector<MultipleChoiceQuestionOption> options = TreePVector.<MultipleChoiceQuestionOption>empty()
                .plus(new MultipleChoiceQuestionOption(messages.homeRecipe_haveRecipeChoice()))
                .plus(new MultipleChoiceQuestionOption(messages.homeRecipe_noRecipeChoice()));

        return PromptUtil.asFoodPrompt(new RadioButtonPrompt(SafeHtmlUtils.fromSafeConstant(messages.homeRecipe_promptText(SafeHtmlUtils.htmlEscape(foodName.toLowerCase()))), AskIfHomeRecipeFlexibleRecall.class.getSimpleName(), options, messages.homeRecipe_continueButtonLabel(), "homeRecipeOption"), new Function1<MultipleChoiceQuestionAnswer, FoodOperation>() {
            @Override
            public FoodOperation apply(MultipleChoiceQuestionAnswer argument) {
                if (argument.index == 0) {
                    GoogleAnalytics.trackMissingFoodHomeRecipe();
                    return FoodOperation.replaceWith(new CompoundFood(FoodLink.newUnlinked(), foodName, isDrink));
                } else {
                    return FoodOperation.update(new Function1<FoodEntry, FoodEntry>() {
                        @Override
                        public FoodEntry apply(FoodEntry argument) {
                            GoogleAnalytics.trackMissingFoodNotHomeRecipe();
                            return argument.withFlag(MissingFood.NOT_HOME_RECIPE_FLAG);
                        }
                    });
                }
            }
        });
    }
}
