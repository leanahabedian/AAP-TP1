package dc.aap;

import soot.*;

import java.util.*;

import soot.jimple.*;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JNewExpr;
import soot.jimple.internal.JimpleLocal;
import soot.tagkit.SourceLnPosTag;
import soot.tagkit.Tag;
import soot.toolkits.graph.*;
import soot.toolkits.scalar.*;

public abstract class PointsToGraphAnalysis extends ForwardFlowAnalysis<Unit,PTL>
{
    @Override
    protected void copy(PTL src, PTL dest)
    {
        src.getE().copy(dest.getE());
        src.getL().copy(dest.getL());
        src.getR().copy(dest.getR());
        src.getW().copy(dest.getW());
    }

    @Override
    protected void merge(PTL src1, PTL src2, PTL dest)
    {

        src1.getE().union(src2.getE(),dest.getE());
        src1.getL().union(src2.getL(),dest.getL());
    }

    @Override
    protected void flowThrough(PTL src, Unit unit,
                               PTL dest)
    {
        copy(src,dest);

        int lineNumber = getLineNumber(unit);

        // GEN
        // Add gen set
        if (isAssign(unit)){ // solo soportamos 1 solo uso por enunciado de TP
            ValueBox left = unit.getDefBoxes().get(0);
            Value leftValue = left.getValue();
            ValueBox right = unit.getUseBoxes().get(0);
            Value rightValue = right.getValue();

            if (isLocal(leftValue)) { // x = ..
                handleLocal(dest, unit, lineNumber, leftValue, rightValue);
            } else if (isFieldRef(leftValue)) { // x.f =
                handleFieldRef(dest, unit, (JInstanceFieldRef) leftValue, rightValue);
            }
        }
        if (isMethodCall(unit)){
            dispatchMethodCall(dest, ((InvokeStmt) unit).getInvokeExpr());
        }

    }

    private Nodo dispatchMethodCall(PTL dest, InvokeExpr invokeExpr) {
        if (invokeExpr instanceof VirtualInvokeExpr) {
            return handleMethodCall(dest,(VirtualInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof InterfaceInvokeExpr) {
            return handleMethodCall(dest,(InterfaceInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof StaticInvokeExpr) {
            return handleMethodCall(dest,(StaticInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof SpecialInvokeExpr) {
            return null; //TODO
        }

        throw new RuntimeException("InvokeExpr not supported " + invokeExpr.toString());
    }

    public boolean handleCalls() {
        return false;
    }

    protected Nodo handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        return null;
    }

    protected Nodo handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
        return null;
    }

    protected Nodo handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
        return null;
    }

    private boolean isMethodCall(Unit unit) {
        return unit instanceof InvokeStmt && handleCalls();
    }

    protected boolean isMethodCall(Value rightValue){
        return rightValue instanceof InvokeExpr && handleCalls();
    }


    private void handleFieldRef(PTL dest, Unit s, JInstanceFieldRef leftValue, Value rightValue) {
        ValueBox right;
        if (isLocal(rightValue)) {//x.f = y

            right = s.getUseBoxes().get(1);
            rightValue = right.getValue();
            String label = leftValue.getFieldRef().name();
            Value owner = leftValue.getBaseBox().getValue();
            // KILL es opcional (Diego)

            // GEN
            List<Nodo> ownerReferences = dest.getReferencias(owner);

            List<Nodo> rightReferences = dest.getReferencias(rightValue);

            for (Nodo origen : ownerReferences) {
                for (Nodo destino : rightReferences) {
                    dest.getE().add(new EjeNodo(origen, label, destino));
                }
            }
        }
    }

    private boolean isFieldRef(Value leftValue) {
        return leftValue instanceof JInstanceFieldRef;
    }

    private void handleLocal(PTL dest, Unit s, int lineNumber, Value leftValue, Value rightValue) {
        ValueBox right;
        if (isNewExpression(rightValue)) { //x = new A()
            if (alreadyDefined(leftValue,rightValue, lineNumber,dest.getL())) return;
            dest.killRelation(leftValue);
            dest.genRelation(leftValue, new Nodo(rightValue.getType().toString(),lineNumber));
        }
        else if (isLocal(rightValue)) {//x = y

            if (alreadyDefined(leftValue,rightValue, lineNumber,dest.getL())) return;

            dest.killRelation(leftValue);

            for (EjeVariable l: dest.getL()) {
                String origen = l.getOrigen().getNombre();
                if (rightValue.toString().equals(origen)) {
                    dest.genRelation(leftValue, l.getDestino());
                }
            }
        }
        else if (isFieldRef(rightValue)) { // x = y.f

            dest.killRelation(leftValue);

            String label = ((JInstanceFieldRef) rightValue).getFieldRef().name();
            right = s.getUseBoxes().get(1);
            Value owner = right.getValue();

            // GEN
            for (EjeVariable l: dest.getL()) {
                if (l.getOrigen().getNombre().equals(owner.toString())) {
                    for (EjeNodo e : dest.getE()) { // quizas haya que ver tambien dest.getR() aca.
                        if (e.getOrigen().equals(l.getDestino())
                                && e.getEtiqueta().equals(label)) {
                            dest.genRelation(leftValue, e.getDestino());
                        }
                    }
                    handleParameterFresh(dest, leftValue, label, l);
                }
            }
        }
        else if (isParameterDefinition(rightValue)){
            handleParameterDefinition(dest, leftValue, (ParameterRef) rightValue);
        }
        else if (isMethodCall(rightValue)){
            Nodo e = dispatchMethodCall(dest, (InvokeExpr) rightValue);
            dest.killRelation(leftValue);
            dest.genRelation(leftValue,e);
        }
    }


    protected abstract void handleParameterDefinition(PTL dest, Value leftValue, ParameterRef rightValue);

    protected abstract void handleParameterFresh(PTL dest, Value leftValue, String label, EjeVariable l);

    private boolean isParameterDefinition(Value v) {
        return v instanceof ParameterRef;
    }

    private boolean isNewExpression(Value rightValue) {
        return rightValue instanceof JNewExpr;
    }

    private boolean isLocal(Value leftValue) {
        return leftValue instanceof JimpleLocal;
    }

    private boolean isAssign(Unit s) {
        return s.getDefBoxes().size() > 0 && s.getUseBoxes().size() > 0;
    }



    private boolean alreadyDefined(Value leftValue, Value rightValue, int lineNumber,FlowSet<EjeVariable> L) {

        for (EjeVariable l: L) {
            String nombreNodo = rightValue.getType().toString()+ "_"+ lineNumber;
            if (l.getOrigen().getNombre().equals(leftValue.toString()) && l.getDestino().getClassName().equals(nombreNodo)){
                return true;
            }
        }
        return false;
    }

    private int getLineNumber(Unit s) {
        Stmt statement = (Stmt) s;
        Tag tagLine = null;
        int lineNumber = -1;
        if (statement.getTags().size() > 0) {
            tagLine = statement.getTags().get(0);
            lineNumber = ((SourceLnPosTag)tagLine).startLn();
        }
        return lineNumber;
    }





    private PTL initial = new PTL();

    @Override
    protected PTL entryInitialFlow()
    {
        return initial;
    }

    @Override
    protected PTL newInitialFlow()
    {
        return new PTL();
    }

    public PointsToGraphAnalysis(DirectedGraph g)
    {
        super(g);
        doAnalysis();
    }

    public PointsToGraphAnalysis(DirectedGraph g, PTL initial)
    {
        super(g);
        this.initial = initial;
        doAnalysis();
    }

}
