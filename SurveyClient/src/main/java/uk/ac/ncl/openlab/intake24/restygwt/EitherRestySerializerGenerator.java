package uk.ac.ncl.openlab.intake24.restygwt;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import org.fusesource.restygwt.rebind.JsonEncoderDecoderClassCreator;
import org.fusesource.restygwt.rebind.RestyJsonSerializerGenerator;
import org.workcraft.gwt.shared.client.Either;

public class EitherRestySerializerGenerator implements RestyJsonSerializerGenerator {

    public Class<? extends JsonEncoderDecoderClassCreator> getGeneratorClass() {
        return EitherCodecGenerator.class;
    }

    public JType getType(TypeOracle typeOracle) {
        return typeOracle.findType(Either.class.getName());
    }
}