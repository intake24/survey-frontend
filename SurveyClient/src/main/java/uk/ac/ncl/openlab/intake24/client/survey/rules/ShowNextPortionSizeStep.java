/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.rules;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import org.pcollections.PSet;
import org.workcraft.gwt.shared.client.Either;
import org.workcraft.gwt.shared.client.Function1;
import org.workcraft.gwt.shared.client.Option;
import uk.ac.ncl.openlab.intake24.client.api.AsyncRequest;
import uk.ac.ncl.openlab.intake24.client.api.foods.PortionSizeMethod;
import uk.ac.ncl.openlab.intake24.client.survey.*;
import uk.ac.ncl.openlab.intake24.client.survey.portionsize.*;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;

public class ShowNextPortionSizeStep implements PromptRule<FoodEntry, FoodOperation> {
    private final PortionSizeScriptManager scriptManager;
    private final PromptMessages messages = GWT.create(PromptMessages.class);

    public ShowNextPortionSizeStep(PortionSizeScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public Option<Prompt<FoodEntry, FoodOperation>> apply(FoodEntry data, SelectionMode selectionType, PSet<String> surveyFlags) {
        // wait until all meals are marked as complete ("free-entry pass") before
        // showing any portion size dialogues
        if (!surveyFlags.contains(Survey.FLAG_FREE_ENTRY_COMPLETE) || data.isTemplate())
            return new Option.None<Prompt<FoodEntry, FoodOperation>>();
        else
            return data.accept(new FoodEntry.Visitor<Option<Prompt<FoodEntry, FoodOperation>>>() {
                @Override
                public Option<Prompt<FoodEntry, FoodOperation>> visitRaw(RawFood food) {
                    return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                }

                @Override
                public Option<Prompt<FoodEntry, FoodOperation>> visitTemplate(TemplateFood food) {
                    return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                }

                @Override
                public Option<Prompt<FoodEntry, FoodOperation>> visitEncoded(final EncodedFood food) {
                    return food.portionSize.accept(new Option.Visitor<Either<PortionSize, CompletedPortionSize>, Option<Prompt<FoodEntry, FoodOperation>>>() {

                        // some portion size estimation method is already in progress
                        @Override
                        public Option<Prompt<FoodEntry, FoodOperation>> visitSome(Either<PortionSize, CompletedPortionSize> portionSize) {
                            return portionSize.accept(new Either.Visitor<PortionSize, CompletedPortionSize, Option<Prompt<FoodEntry, FoodOperation>>>() {

                                // portion size estimation complete, no associatedFoods to show
                                @Override
                                public Option<Prompt<FoodEntry, FoodOperation>> visitRight(CompletedPortionSize value) {
                                    return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                                }

                                // portion size estimation is incomplete, choose a prompt to
                                // show
                                @Override
                                public Option<Prompt<FoodEntry, FoodOperation>> visitLeft(final PortionSize incompletePoritonSize) {
                                    return incompletePoritonSize.loadedScript.accept(new Option.Visitor<PortionSizeScript, Option<Prompt<FoodEntry, FoodOperation>>>() {

                                        // resources for this portion size are loaded
                                        // show next prompt
                                        @Override
                                        public Option<Prompt<FoodEntry, FoodOperation>> visitSome(final PortionSizeScript loaded) {
                                            return loaded.nextPrompt(incompletePoritonSize.data, food.data).accept(
                                                    new Option.Visitor<SimplePrompt<UpdateFunc>, Option<Prompt<FoodEntry, FoodOperation>>>() {

                                                        // still some associatedFoods to show
                                                        @Override
                                                        public Option<Prompt<FoodEntry, FoodOperation>> visitSome(final SimplePrompt<UpdateFunc> simplePrompt) {
                                                            return Option.<Prompt<FoodEntry, FoodOperation>>some(PromptUtil.asFoodPrompt(simplePrompt,
                                                                    new Function1<UpdateFunc, FoodOperation>() {
                                                                        @Override
                                                                        public FoodOperation apply(final UpdateFunc updateFunc) {
                                                                            return FoodOperation.updateEncoded(new Function1<EncodedFood, EncodedFood>() {
                                                                                @Override
                                                                                public EncodedFood apply(EncodedFood argument) {
                                                                                    PMap<String, String> newData = updateFunc.apply(incompletePoritonSize.data);

                                                                                    // check for next prompt
                                                                                    // if there isn't one, mark the
                                                                                    // portion size as completed
                                                                                    if (loaded.nextPrompt(newData, food.data).isEmpty()) {
                                                                                        return argument.withPortionSize(PortionSize.complete(new CompletedPortionSize(
                                                                                                incompletePoritonSize.scriptName, newData)));
                                                                                    } else
                                                                                        // keep the incomplete portion size
                                                                                        // otherwise
                                                                                        return argument.withPortionSize(PortionSize.incomplete(incompletePoritonSize.withData(newData)));
                                                                                }
                                                                            });
                                                                        }
                                                                    }));
                                                        }

                                                        @Override
                                                        public Option<Prompt<FoodEntry, FoodOperation>> visitNone() {
                                                            return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                                                        }
                                                    });
                                        }

                                        // resources (image map definitions, sliding scale
                                        // definitions, etc.) for this portion size not loaded yet
                                        // show a "loading" prompt
                                        @Override
                                        public Option<Prompt<FoodEntry, FoodOperation>> visitNone() {
                                            return Option.some(PromptUtil.loading(messages.loadingPortionSize(), new AsyncRequest<PortionSizeScript>() {
                                                @Override
                                                public void execute(AsyncCallback<PortionSizeScript> callback) {
                                                    incompletePoritonSize.script.loadResources(incompletePoritonSize.data, callback);
                                                }
                                            }, new Function1<PortionSizeScript, FoodOperation>() {
                                                @Override
                                                public FoodOperation apply(final PortionSizeScript loadedScript) {
                                                    return FoodOperation.updateEncoded(new Function1<EncodedFood, EncodedFood>() {
                                                        @Override
                                                        public EncodedFood apply(EncodedFood argument) {
                                                            return argument.withPortionSize(PortionSize.incomplete(incompletePoritonSize.withLoadedScript(loadedScript)));
                                                        }
                                                    }, false);
                                                }
                                            }));
                                        }
                                    });
                                }
                            });
                        }

                        // portion size estimation has not been started
                        @Override
                        public Option<Prompt<FoodEntry, FoodOperation>> visitNone() {
                            final Option.Visitor<Either<PortionSize, CompletedPortionSize>, Option<Prompt<FoodEntry, FoodOperation>>> outer = this;

                            return food.portionSizeMethodIndex.accept(new Option.Visitor<Integer, Option<Prompt<FoodEntry, FoodOperation>>>() {

                                // a specific portion size method has been chosen
                                // initialise it and get the first prompt
                                @Override
                                public Option<Prompt<FoodEntry, FoodOperation>> visitSome(Integer portionSizeMethodIndex) {
                                    PortionSizeMethod portionSizeMethod = food.data.portionSizeMethods.get(portionSizeMethodIndex);
                                    PortionSizeScriptLoader instance = scriptManager.getInstance(portionSizeMethod.method);
                                    PortionSize portionSize = new PortionSize(portionSizeMethod.method, HashTreePMap.<String, String>empty().plusAll(
                                            portionSizeMethod.parameters), instance);

                                    // return the first prompt
                                    return outer.visitSome(PortionSize.incomplete(portionSize));
                                }

                                // a specific portion size method is not yet known
                                // cannot show any associatedFoods
                                @Override
                                public Option<Prompt<FoodEntry, FoodOperation>> visitNone() {
                                    return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                                }
                            });
                        }
                    });
                }

                @Override
                public Option<Prompt<FoodEntry, FoodOperation>> visitMissing(MissingFood food) {
                    return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                }

                @Override
                public Option<Prompt<FoodEntry, FoodOperation>> visitCompound(CompoundFood food) {
                    return new Option.None<Prompt<FoodEntry, FoodOperation>>();
                }

            });
    }

    @Override
    public String toString() {
        return "Show next stage of a scripted portion size estimation";
    }

    public static WithPriority<PromptRule<FoodEntry, FoodOperation>> withPriority(PortionSizeScriptManager scriptManager, int priority) {
        return new WithPriority<PromptRule<FoodEntry, FoodOperation>>(new ShowNextPortionSizeStep(scriptManager), priority);
    }
}