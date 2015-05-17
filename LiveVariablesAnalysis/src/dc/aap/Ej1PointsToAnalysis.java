package dc.aap;

import soot.Value;
import soot.jimple.*;
import soot.toolkits.graph.DirectedGraph;

import java.util.List;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej1PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej1PointsToAnalysis(DirectedGraph g) {
        super(g,new PTL(),new Ej1InvokeHandler());
    }

    private static class Ej1InvokeHandler extends InvokeHandler {
        @Override
        protected List<Ref> handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
            dest.getW().add(invokeExpr);
            return null;
        }

        @Override
        protected List<Ref> handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
            dest.getW().add(invokeExpr);
            return null;
        }

        @Override
        protected List<Ref> handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
            dest.getW().add(invokeExpr);
            return null;
        }

        @Override
        protected void dispatchMethodCallAndAssign(PTL dest, InvokeExpr rightValue, Value leftValue) {

        }
    }
}
