package dc.aap;

import soot.Value;

import java.util.Map;

/**
 * Created by ivan on 06/05/15.
 */
public class ParamBinding {

    public Map<String, Value> outputBinding;
    public PTL input;

    public ParamBinding(Map<String,Value> outputBinding, PTL input) {
        this.input = input;
        this.outputBinding = outputBinding;
    }

    public PTL getInput() {
        return input;
    }

    public Map<String, Value> getOutputBinding() {
        return outputBinding;
    }
}
