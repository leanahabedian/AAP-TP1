package dc.aap;

import soot.jimple.VirtualInvokeExpr;
import soot.toolkits.graph.DirectedGraph;

/**
 * Created by ivan on 06/05/15.
 */
public class Ej3PointsToAnalysis extends Ej1PointsToAnalysis {

    public Ej3PointsToAnalysis(DirectedGraph g) {
        super(g);
    }

//    @Override
//    protected Ref handleMethodCall(PTL dest, VirtualInvokeExpr invoke) {
//        List<Unit> oldSuccs = this.graph.getSuccsOf(invoke);
//        Body targetBody = invoke.getInvokeExpr().getMethod().retrieveActiveBody();
//        ExceptionalUnitGraph targetGraph = new ExceptionalUnitGraph(targetBody);
//
//        //this would be easy if soot allows it
//        this.body.getUnits().insertAfter(invoke,targetBody.getUnits().getFirst());
//
//        for (Unit tail : targetGraph.getTails()) {
//            for (Unit oldSucc : oldSuccs) {
//                targetGraph.getSuccsOf(tail).listIterator().add(oldSucc);
//            }
//        }
//        this.graph.getSuccsOf(invoke).clear();
//        this.graph.getSuccsOf(invoke).addAll(targetGraph.getHeads());
//        invoke.getInvokeExpr().getArgs();
//        return null;
//    }

}
