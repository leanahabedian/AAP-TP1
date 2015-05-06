package dc.aap;

import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 06/05/15.
 */
public class Ej4PointsToAnalysis extends PointsToGraphAnalysis {

    public Ej4PointsToAnalysis(DirectedGraph g) {
        super(g);
    }

    public Ej4PointsToAnalysis(DirectedGraph g,PTL initial) {
        super(g,initial);
    }

    @Override
    public boolean handleCalls(){
        return true;
    }

    @Override
    protected Nodo handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();
        Body body = method.retrieveActiveBody();
        ExceptionalUnitGraph graph = new ExceptionalUnitGraph(body);
        ParamBinding binding = bindParameters(dest, invokeExpr, body);
        Ej4PointsToAnalysis pointsTo = new Ej4PointsToAnalysis(graph,binding.getInput());
        PTL subptl = pointsTo.getFlowAfter(graph.getTails().get(0));
        dest = unbindParameters(subptl,binding,dest);

        return null;
    }

    private PTL unbindParameters(PTL subptl, ParamBinding binding, PTL dest) {
        for (EjeVariable l : subptl.getL()) {
            String origen = l.getOrigen().getNombre();
            if (binding.getOutputBinding().containsKey(origen)){
                dest.genRelation(binding.getOutputBinding().get(origen),l.getDestino());
            }
        }
        // TODO: falta la parte de E
        return dest;
    }

    private ParamBinding bindParameters(PTL dest, InvokeExpr invokeExpr, Body body) {
        Map<String,Value> outputBinding = new HashMap<>();
        PTL inputPtl = new PTL();
        for (int i = 0; i < invokeExpr.getArgs().size(); i++) {
            for (Nodo ref : dest.getReferencias(invokeExpr.getArg(i))) {
                String paramName = body.getParameterLocal(i).getName();
                Value paramValue = invokeExpr.getArg(i);
                dest.killRelation(invokeExpr.getArg(i));
                outputBinding.put(paramName, paramValue);
                inputPtl.getL().add(new EjeVariable(new Variable(paramName),ref));
            }
        }
        return new ParamBinding(outputBinding,inputPtl);
    }

    @Override
    protected Nodo handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();

        for (Nodo ref: dest.getReferencias(invokeExpr.getBaseBox().getValue())) {
            SootClass refClass = Scene.v().getSootClass(ref.getClassName());
            SootMethod implMethod = refClass.getMethod(method.getSubSignature());
            Body implMethodBody = implMethod.retrieveActiveBody();
        }

        return null;
    }

    @Override
    protected Nodo handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue) {

    }

    @Override
    protected void handleParameterFresh(PTL dest, Value leftValue, String label, EjeVariable l) {

    }
}
