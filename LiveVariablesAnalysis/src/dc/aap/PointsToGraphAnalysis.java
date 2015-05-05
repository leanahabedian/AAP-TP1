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

public class PointsToGraphAnalysis extends ForwardFlowAnalysis
{
    protected void copy(Object src, Object dest)
    {
        FlowSet srcSet  = (FlowSet) src;
        FlowSet destSet = (FlowSet) dest;

        srcSet.copy(destSet);
    }

    protected void merge(Object src1, Object src2, Object dest)
    {
        FlowSet srcSet1 = (FlowSet) src1;
        FlowSet srcSet2 = (FlowSet) src2;
        FlowSet destSet = (FlowSet) dest;

        srcSet1.union(srcSet2, destSet);
    }

    protected void flowThrough(Object srcValue, Object unit,
                               Object destValue)
    {
        FlowSet dest = (FlowSet) destValue;
        FlowSet src  = (FlowSet) srcValue;
        Unit    s    = (Unit)    unit;
        src.copy (dest);

        int lineNumber = getLineNumber(s);

        // GEN
        // Add gen set
        if (isAssign(s)){ // solo soportamos 1 solo uso por enunciado de TP
            ValueBox left = s.getDefBoxes().get(0);
            Value leftValue = left.getValue();
            ValueBox right = s.getUseBoxes().get(0);
            Value rightValue = right.getValue();

            if (isLocal(leftValue)) { // x = ..
                handleLocal(dest, s, lineNumber, leftValue, rightValue);
            } else if (isFieldRef(leftValue)) { // x.f =
                handleFieldRef(dest, s, (JInstanceFieldRef) leftValue, rightValue);
            }
        }

    }

    private void handleFieldRef(FlowSet dest, Unit s, JInstanceFieldRef leftValue, Value rightValue) {
        ValueBox right;
        if (isLocal(rightValue)) {//x.f = y

            right = s.getUseBoxes().get(1);
            rightValue = right.getValue();
            String label = leftValue.getFieldRef().name();
            Value owner = leftValue.getBaseBox().getValue();
            // KILL es opcional (Diego)

            // GEN
            List<Nodo> ownerReferences = getReferencias(dest, owner,getL(dest));

            List<Nodo> rightReferences = getReferencias(dest, rightValue,getL(dest));

            for (Nodo origen : ownerReferences) {
                for (Nodo destino : rightReferences) {
                    dest.add(new EjeNodo(origen, label, destino));
                }
            }
        }
    }

    private boolean isFieldRef(Value leftValue) {
        return leftValue instanceof JInstanceFieldRef;
    }

    private void handleLocal(FlowSet dest, Unit s, int lineNumber, Value leftValue, Value rightValue) {
        ValueBox right;
        if (isNewExpression(rightValue)) { //x = new A()
            if (alreadyDefined(dest,leftValue,rightValue, lineNumber,getL(dest))) return;
            killRelation(dest, leftValue,getL(dest));
            genRelation(dest, leftValue, rightValue.getType() + "_" + lineNumber);
        }
        else if (isLocal(rightValue)) {//x = y

            if (alreadyDefined(dest,leftValue,rightValue, lineNumber,getL(dest))) return;

            killRelation(dest, leftValue,getL(dest));

            for (EjeVariable l: getL(dest)) {
                String origen = l.getOrigen().getNombre();
                if (rightValue.toString().equals(origen)) {
                    genRelation(dest, leftValue, l.getDestino());
                }
            }
        }
        else if (isFieldRef(rightValue)) { // x = y.f

            killRelation(dest, leftValue,getL(dest));

            String label = ((JInstanceFieldRef) rightValue).getFieldRef().name();
            right = s.getUseBoxes().get(1);
            Value owner = right.getValue();

            // GEN
            for (EjeVariable l: getL(dest)) {
                if (l.getOrigen().getNombre().equals(owner.toString())) {
                    for (EjeNodo e : getE(dest)) {
                        if (e.getOrigen().equals(l.getDestino())
                                && e.getEtiqueta().equals(label)) {
                            genRelation(dest, leftValue, e.getDestino());
                        }
                    }
                    if (l.getDestino().getNombre().startsWith("param-")) { // load from parameter, new node needed for the label
                        Nodo fresh = new Nodo(l.getDestino().getNombre() + "." + label);
                        genRelation(dest, leftValue, fresh);
                        dest.add(new EjeNodo(l.getDestino(), label, fresh));
                    }
                }
            }
        }
        else if (isParameterDefinition(rightValue)){
            ParameterRef param = (ParameterRef) rightValue;
            genRelation(dest, leftValue, new Nodo("param-" + param.getType().toString() + param.getIndex()));
        }
    }

    private boolean isParameterDefinition(Value v) {
        return v instanceof ParameterRef;
    }

    private boolean isNewExpression(Value rightValue) {
        return rightValue instanceof JNewExpr;
    }

    private List<EjeNodo> getE(FlowSet dest) {
        List<EjeNodo> l = new ArrayList<>();
        for (Object vertex : dest) {
            if (vertex instanceof EjeNodo) {
                l.add((EjeNodo) vertex);
            }
        }
        return l;

    }

    private List<EjeVariable> getL(FlowSet dest) {
        List<EjeVariable> l = new ArrayList<>();
        for (Object vertex : dest) {
            if (vertex instanceof EjeVariable) {
                l.add((EjeVariable) vertex);
            }
        }
        return l;
    }

    private boolean isLocal(Value leftValue) {
        return leftValue instanceof JimpleLocal;
    }

    private boolean isAssign(Unit s) {
        return s.getDefBoxes().size() > 0 && s.getUseBoxes().size() > 0;
    }

    private List<Nodo> getReferencias(FlowSet dest, Value owner,List<EjeVariable>L) {
        List<Nodo> references = new ArrayList<Nodo>();
        for (EjeVariable l: L) {
            if (l.getOrigen().getNombre().equals(owner.toString())) {
                references.add(l.getDestino());
            }
        }
        return references;
    }

    private boolean alreadyDefined(FlowSet dest, Value leftValue, Value rightValue, int lineNumber,List<EjeVariable> L) {

        for (EjeVariable l: L) {
            String nombreNodo = rightValue.getType().toString()+ "_"+ lineNumber;
            if (l.getOrigen().getNombre().equals(leftValue.toString()) && l.getDestino().getNombre().equals(nombreNodo)){
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

    private void genRelation(FlowSet dest, Value leftValue, String rightValue) {
        EjeVariable ejeVariable = new EjeVariable(new Variable(leftValue.toString()),new Nodo(rightValue));
        dest.add(ejeVariable);
    }

    private void genRelation(FlowSet dest, Value leftValue, Nodo destino) {
        EjeVariable ejeVariable = new EjeVariable(new Variable(leftValue.toString()),destino);
        dest.add(ejeVariable);
    }

    private void killRelation(FlowSet dest, Value leftValue,List<EjeVariable> L) {
        for (EjeVariable l : L){
            String origen = l.getOrigen().getNombre();
            if (leftValue.toString().equals(origen)) {
                dest.remove(l);
            }
        }
    }

    protected Object entryInitialFlow()
    {
        return new ArraySparseSet();
    }

    protected Object newInitialFlow()
    {
        return new ArraySparseSet();
    }

    public PointsToGraphAnalysis(DirectedGraph g)
    {
        super(g);

        doAnalysis();
    }
}
