package dc.aap;

import soot.Value;
import soot.jimple.ParameterRef;
import soot.jimple.VirtualInvokeExpr;
import soot.toolkits.graph.DirectedGraph;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej2PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej2PointsToAnalysis(DirectedGraph g) {
        super(g);
    }

    @Override
    protected Nodo handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        dest.getW().add(invokeExpr);
        return null;
    }

    @Override
    protected void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue) {
        ParameterRef param = rightValue;
        dest.genParamRelation(leftValue, new Nodo("param_" + param.getType().toString(),param.getIndex()));
    }

    @Override
    protected void handleParameterFresh(PTL dest, Value leftValue, String label, EjeVariable l) {
        if (l.getDestino().getClassName().startsWith("param_")) { // load from parameter, new node needed for the label
            Nodo fresh = new Nodo(l.getDestino().toString() + "." + label,-1);
            dest.genRelation(leftValue, fresh);
            dest.getR().add(new EjeNodo(l.getDestino(), label, fresh));
        }
    }
}
