package dc.aap;

import soot.SootMethod;

/**
 * Created by ivan on 11/05/15.
 */
public interface CallGraphBuilder {
    void addEdge(SootMethod rootMethod, SootMethod method);
}
