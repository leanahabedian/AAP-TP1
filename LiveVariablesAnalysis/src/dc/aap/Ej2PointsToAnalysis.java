package dc.aap;

import soot.Value;
import soot.jimple.ParameterRef;
import soot.jimple.VirtualInvokeExpr;
import soot.toolkits.graph.DirectedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej2PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej2PointsToAnalysis(DirectedGraph g) {
        super(g, new ParameterHandler() {
            List<Value> definedParams = new ArrayList<Value>();

            @Override
            public void handleParameterFresh(PTL dest, Value x, Value a, String field) { //x = a.f
                if (definedParams.contains(a) && dest.getRefsToRef(a,field).isEmpty()){ // a is a parameter
                    dest.genFresh(x,a,field);
                }
                for (Ref ref : dest.getRefs(a)) {
                    if (ref.getClassName().startsWith("param_")){ // a points to a fresh param object
                        List<Ref> paramRefs = dest.getParamRefs(ref, field);
                        if (paramRefs.isEmpty()) {
                            //need a fresh parameter
                            dest.genFresh(x,a,field);
                        } else {
                            //has the fresh parameter
                            for (Ref paramRef : paramRefs) {
                                dest.genVarToRef(x,paramRef);
                            }
                        }
                    }
                }
            }

            @Override
            public void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue) {
                ParameterRef param = rightValue;
                dest.genParamRelation(leftValue, new Ref("param_" + param.getType().toString(), param.getIndex()));
                definedParams.add(leftValue);
            }
        });
    }
}
