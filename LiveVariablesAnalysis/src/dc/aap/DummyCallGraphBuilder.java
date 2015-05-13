package dc.aap;

import soot.SootMethod;

/**
 * Created by ivan on 11/05/15.
 */
public class DummyCallGraphBuilder implements CallGraphBuilder {

    @Override
    public void addEdge(SootMethod rootMethod, SootMethod method) {
        System.out.println(rootMethod.toString() + " -> " + method.toString());
    }
}
