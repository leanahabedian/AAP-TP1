package dc.aap;

import soot.Value;
import soot.jimple.ParameterRef;
import soot.jimple.VirtualInvokeExpr;
import soot.toolkits.graph.DirectedGraph;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej1PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej1PointsToAnalysis(DirectedGraph g) {
        super(g);
    }

}
