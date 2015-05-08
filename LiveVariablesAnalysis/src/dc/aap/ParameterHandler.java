package dc.aap;

import soot.Value;
import soot.jimple.ParameterRef;

/**
 * Created by ivan on 08/05/15.
 */
public abstract class ParameterHandler {
    public abstract void handleParameterFresh(PTL dest, Value leftValue, Value y, String field);

    public abstract void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue);
}
