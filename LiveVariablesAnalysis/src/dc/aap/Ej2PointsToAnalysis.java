package dc.aap;

import soot.Value;
import soot.jimple.ParameterRef;
import soot.toolkits.graph.DirectedGraph;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej2PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej2PointsToAnalysis(DirectedGraph g) {
        super(g);
    }

    @Override
    protected void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue) {
        ParameterRef param = rightValue;
        genRelation(dest.getL(), leftValue, new Nodo("param-" + param.getType().toString() + param.getIndex()));
    }

    @Override
    protected void handleParameterFresh(PTL dest, Value leftValue, String label, EjeVariable l) {
        if (l.getDestino().getNombre().startsWith("param-")) { // load from parameter, new node needed for the label
            Nodo fresh = new Nodo(l.getDestino().getNombre() + "." + label);
            genRelation(dest.getL(), leftValue, fresh);
            dest.getR().add(new EjeNodo(l.getDestino(), label, fresh));
        }
    }
}
