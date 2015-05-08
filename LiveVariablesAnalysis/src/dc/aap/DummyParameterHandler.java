package dc.aap;

import soot.Value;
import soot.jimple.ParameterRef;

/**
 * Created by ivan on 08/05/15.
 */
public class DummyParameterHandler extends ParameterHandler {
    @Override
    public void handleParameterFresh(PTL dest, Value leftValue, Value y, String field) {

    }

    @Override
    public void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue) {

    }
}
