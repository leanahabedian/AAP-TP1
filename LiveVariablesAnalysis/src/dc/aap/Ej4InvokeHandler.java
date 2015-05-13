package dc.aap;

import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 08/05/15.
 */
public class Ej4InvokeHandler extends InvokeHandler{

    public CallGraphBuilder callGraphBuilder = new DummyCallGraphBuilder();

    public Ej4InvokeHandler(){}

    public Ej4InvokeHandler(CallGraphBuilder callGraphBuilder){
        this.callGraphBuilder = callGraphBuilder;
    }

    @Override
    public void dispatchMethodCallAndAssign(PTL dest, InvokeExpr invokeExpr, Value leftValue) {
        List<Ref> refs = dispatchMethodCall(dest, invokeExpr);
        for (Ref ref : refs) {
            dest.killVarToRef(leftValue);
            dest.genVarToRef(leftValue, ref);
        }
    }


    @Override
    protected List<Ref> handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();
        Body body = method.retrieveActiveBody();
        callGraphBuilder.addEdge(this.rootMethod,method);
        return processBody(dest, invokeExpr, body);
    }

    private List<Ref> processBody(PTL dest, InvokeExpr invokeExpr, Body body) {
        ExceptionalUnitGraph graph = new ExceptionalUnitGraph(body);
        ParamBinding binding = bindParameters(dest, invokeExpr, body);
        Ej4PointsToAnalysis pointsTo = new Ej4PointsToAnalysis(graph,binding.getInput());
        PTL subptl = pointsTo.getFlowAfter(graph.getTails().get(0));
        List<Ref> result = unbindParameters(subptl,binding,dest,graph.getTails().get(0));
        return result;
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


    private List<Ref> unbindParameters(PTL subptl, ParamBinding binding, PTL dest, Unit ret) {

        for (VarToRef l : subptl.getL()) {
            String origen = l.getVar().getName();
            if (binding.getOutputBinding().containsKey(origen)){
                dest.genVarToRef(binding.getOutputBinding().get(origen), l.getRef());
            }
        }

        subptl.getE().copy(dest.getE());

        if (!ret.getUseBoxes().isEmpty()){
            return subptl.getRefs(ret.getUseBoxes().get(0).getValue());
        }

        return new ArrayList<>(); // no refs found
    }



    @Override
    protected List<Ref> handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();
        List<Ref> refs = new ArrayList<>();

        for (Ref ref: dest.getRefs(invokeExpr.getBaseBox().getValue())) {
            SootClass refClass = Scene.v().getSootClass(ref.getClassName());
            SootMethod implMethod = refClass.getMethod(method.getSubSignature());
            callGraphBuilder.addEdge(this.rootMethod,implMethod);
            Body implMethodBody = implMethod.retrieveActiveBody();
            refs.addAll(processBody(dest,invokeExpr,implMethodBody));
        }

        return refs;
    }

    @Override
    protected List<Ref> handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
        SootMethod method = invokeExpr.getMethod();
        Body body = method.retrieveActiveBody();
        callGraphBuilder.addEdge(this.rootMethod,method);
        return processBody(dest, invokeExpr, body);
    }



}
