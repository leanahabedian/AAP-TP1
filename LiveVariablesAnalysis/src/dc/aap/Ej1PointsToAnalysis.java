package dc.aap;

import soot.Value;
import soot.jimple.InvokeExpr;
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

    @Override
    protected Nodo handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        dest.getW().add(invokeExpr); return null;
    }

    @Override
    protected void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue) {

    }

    @Override
    protected void handleParameterFresh(PTL dest, Value leftValue, String label, EjeVariable l) {

    }
}
