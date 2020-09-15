/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey.portionsize;

import org.pcollections.PVector;
import org.pcollections.TreePVector;
import uk.ac.ncl.openlab.intake24.client.survey.prompts.messages.PromptMessages;

import java.util.HashMap;
import java.util.Map;

public class DefaultPortionSizeScripts {

    public static final PVector<StandardUnitDef> defaultMilkInHotDrinkPercentages =
            TreePVector.<StandardUnitDef>empty()
                    .plus(new StandardUnitDef(PromptMessages.INSTANCE.milkInHotDrink_amountLittle(), false, 0.10))
                    .plus(new StandardUnitDef(PromptMessages.INSTANCE.milkInHotDrink_amountAverage(), false, 0.16))
                    .plus(new StandardUnitDef(PromptMessages.INSTANCE.milkInHotDrink_amountLot(), false, 0.24));

    public static Map<String, PortionSizeScriptConstructor> getConstructors() {
        final Map<String, PortionSizeScriptConstructor> ctors = new HashMap<String, PortionSizeScriptConstructor>();

        ctors.put(AsServedScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new AsServedScriptLoader();
            }
        });

        ctors.put(GuideScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new GuideScriptLoader();
            }
        });

        ctors.put(DrinkScaleScript.name, new PortionSizeScriptConstructor() {

            @Override
            public PortionSizeScriptLoader newInstance() {
                return new DrinkScaleScriptLoader();
            }
        });

        ctors.put(StandardPortionScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new StandardPortionScriptLoader();
            }
        });

        ctors.put(CerealPortionSizeScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new CerealPortionSizeScriptLoader();
            }
        });

        ctors.put(MilkOnCerealPortionSizeScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new MilkOnCerealPortionSizeScriptLoader();
            }
        });

        ctors.put(PizzaPortionSizeScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new PizzaPortionSizeScriptLoader();
            }
        });

        ctors.put(MilkInHotDrinkPortionSizeScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new MilkInHotDrinkPortionSizeScriptLoader(defaultMilkInHotDrinkPercentages);
            }
        });

        ctors.put(StandardPortionScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new StandardPortionScriptLoader();
            }
        });

        ctors.put(WeightPortionScript.name, new PortionSizeScriptConstructor() {
            @Override
            public PortionSizeScriptLoader newInstance() {
                return new WeightPortionScriptLoader();
            }
        });

        return ctors;
    }
}
