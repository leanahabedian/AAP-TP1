package dc.aap;

import soot.Value;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.FlowSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 05/05/15.
 */
public class PTL {

    FlowSet<VarToRef> L;
    FlowSet<RefToRef> E;
    FlowSet R;
    FlowSet W;

    public PTL(){
        L = new ArraySparseSet();
        E = new ArraySparseSet();
        R = new ArraySparseSet();
        W = new ArraySparseSet();
    }

    public List<Ref> getRefs(Value owner) {
        List<Ref> references = new ArrayList<Ref>();
        for (VarToRef l: L) {
            if (l.getVar().getName().equals(owner.toString())) {
                references.add(l.getRef());
            }
        }
        return references;
    }

    public List<Var> getVars(Ref ref) {
        List<Var> vars = new ArrayList<>();
        for (VarToRef l: L) {
            if (l.getRef().equals(ref)) {
                vars.add(l.getVar());
            }
        }
        return vars;
    }

    public void killVarToRef(Value leftValue) {
        for (VarToRef l : L){
            String origen = l.getVar().getName();
            if (leftValue.toString().equals(origen)) {
                L.remove(l);
            }
        }
    }

    public void genVarToRef(Value leftValue, Ref destino) {
        VarToRef varToRef = new VarToRef(new Var(leftValue.toString()),destino);
        L.add(varToRef);
    }



    public void genParamRelation(Value leftValue, Ref destino) {
        VarToRef varToRef = new VarToRef(new Var(leftValue.toString()),destino);
        L.add(varToRef); //Todo rename L to R. change tests
    }

    public FlowSet<VarToRef> getL() {
        return L;
    }

    public void setL(FlowSet l) {
        L = l;
    }

    public FlowSet<RefToRef> getE() {
        return E;
    }

    public void setE(FlowSet e) {
        E = e;
    }

    public FlowSet getR() {
        return R;
    }

    public void setR(FlowSet r) {
        R = r;
    }

    public FlowSet getW() {
        return W;
    }

    public void setW(FlowSet w) {
        W = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PTL ptl = (PTL) o;

        if (!E.equals(ptl.E)) return false;
        if (!L.equals(ptl.L)) return false;
        if (!R.equals(ptl.R)) return false;
        if (!W.equals(ptl.W)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = L.hashCode();
        result = 31 * result + E.hashCode();
        result = 31 * result + R.hashCode();
        result = 31 * result + W.hashCode();
        return result;
    }

    public boolean hasVarPointingTo(Ref ref) {
        return !getVars(ref).isEmpty();
    }

    public boolean hasRefToRef(RefToRef refToRef) {
        return E.contains(refToRef);
    }

    public void genRefToRef(Value a, String field, Value y) { //a.field = y
        // GEN
        List<Ref> ownerReferences = getRefs(a);

        List<Ref> rightReferences = getRefs(y);

        for (Ref from : ownerReferences) {
            for (Ref to : rightReferences) {
                E.add(new RefToRef(from, field, to));
            }
        }
    }

    public void copyRefs(Value y, Value x) { // x = y
        for (VarToRef l: L) {
            String from = l.getVar().getName();
            if (y.toString().equals(from)) {
                genVarToRef(x, l.getRef());
            }
        }
    }

    public List<Ref> getRefsToRef(Value y, String field) {
        List<Ref> refs = new ArrayList<>();
        for (Ref ref : getRefs(y)) {
            refs.addAll(getRefs(ref, field));
        }
        return refs;
    }

    private List<Ref> getRefs(Ref ref, String field) {
        List<Ref> refs = new ArrayList<>();
        for (RefToRef refToRef : E) {
            if (refToRef.getOrigin().equals(ref) && refToRef.getField().equals(field)){
                refs.add(refToRef.getDestiny());
            }
        }
        return refs;
    }

    public void genFresh(Value x, Value a, String field) {
        List<Ref> arefs = getRefs(a);
        if (!(arefs.size() == 1)) throw new RuntimeException("Case not supported");
        Ref ref = arefs.get(0);
        Ref fresh = new Ref(ref.toString() + "." + field,-1);
        genVarToRef(x, fresh);
        R.add(new RefToRef(ref, field, fresh));

    }

    public boolean hasRelation(Value leftValue, Value rightValue, int lineNumber) {
        for (VarToRef l: L) {
            String name = rightValue.getType().toString()+ "_"+ lineNumber;
            if (l.getVar().getName().equals(leftValue.toString()) && l.getRef().getClassName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void copy(PTL dest) {
        E.copy(dest.getE());
        L.copy(dest.getL());
        R.copy(dest.getR());
        W.copy(dest.getW());

    }
}
