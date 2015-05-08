package dc.aap;

import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 08/05/15.
 */
public class Ej4InvokeHandler extends InvokeHandler{

    @Override
    public void dispatchMethodCallAndAssign(PTL dest, InvokeExpr invokeExpr, Value leftValue) {
        Ref ref = dispatchMethodCall(dest,invokeExpr);
        if (ref == null) return; //case not supported
        dest.killVarToRef(leftValue);
        dest.genVarToRef(leftValue, ref);
    }

    @Override
    public Ref dispatchMethodCall(PTL dest, InvokeExpr invokeExpr) {
        if (invokeExpr instanceof VirtualInvokeExpr) {
             return handleMethodCall(dest,(VirtualInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof InterfaceInvokeExpr) {
            return handleMethodCall(dest,(InterfaceInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof StaticInvokeExpr) {
            return handleMethodCall(dest,(StaticInvokeExpr) invokeExpr);
        }
        return null;
    }

    @Override
    protected Ref handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();
        Body body = method.retrieveActiveBody();
        ExceptionalUnitGraph graph = new ExceptionalUnitGraph(body);
        ParamBinding binding = bindParameters(dest, invokeExpr, body);
        Ej4PointsToAnalysis pointsTo = new Ej4PointsToAnalysis(graph,binding.getInput());
        PTL subptl = pointsTo.getFlowAfter(graph.getTails().get(0));
        dest = unbindParameters(subptl,binding,dest);

        //todo falta el return
        return null;
    }

    private PTL unbindParameters(PTL subptl, ParamBinding binding, PTL dest) {

        for (VarToRef l : subptl.getL()) {
            String origen = l.getVar().getName();
            if (binding.getOutputBinding().containsKey(origen)){
                dest.genVarToRef(binding.getOutputBinding().get(origen), l.getRef());
            }
        }
        // check if are new relations a.h = y not efficiently
        for (RefToRef refToRef : subptl.getE()) {
            if (!dest.hasRefToRef(refToRef) && dest.hasVarPointingTo(refToRef.getOrigin())) { // n^2

            }
        }

        //Todo esto funciona?
        subptl.getE().copy(dest.getE());
        return dest;
    }

    private ParamBinding bindParameters(PTL dest, InvokeExpr invokeExpr, Body body) {
        Map<String,Value> outputBinding = new HashMap<>();
        PTL inputPtl = new PTL();
        for (int i = 0; i < invokeExpr.getArgs().size(); i++) {
            for (Ref ref : dest.getRefs(invokeExpr.getArg(i))) {
                String paramName = body.getParameterLocal(i).getName();
                Value paramValue = invokeExpr.getArg(i);
                dest.killVarToRef(invokeExpr.getArg(i));
                outputBinding.put(paramName, paramValue);
                inputPtl.getL().add(new VarToRef(new Var(paramName),ref)); //agrega los nodos relevantes de L
            }
        }

        dest.getE().copy(inputPtl.getE());
        return new ParamBinding(outputBinding,inputPtl);
    }

    @Override
    protected Ref handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();

        for (Ref ref: dest.getRefs(invokeExpr.getBaseBox().getValue())) {
            SootClass refClass = Scene.v().getSootClass(ref.getClassName());
            SootMethod implMethod = refClass.getMethod(method.getSubSignature());
            Body implMethodBody = implMethod.retrieveActiveBody();
        }

        return null;
    }

    @Override
    protected Ref handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
        return null;
    }



}
