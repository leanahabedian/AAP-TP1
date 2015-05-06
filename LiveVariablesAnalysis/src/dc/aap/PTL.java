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

    FlowSet<EjeVariable> L;
    FlowSet<EjeNodo> E;
    FlowSet R;
    FlowSet W;

    public PTL(){
        L = new ArraySparseSet();
        E = new ArraySparseSet();
        R = new ArraySparseSet();
        W = new ArraySparseSet();
    }

    public List<Nodo> getReferencias(Value owner) {
        List<Nodo> references = new ArrayList<Nodo>();
        for (EjeVariable l: L) {
            if (l.getOrigen().getNombre().equals(owner.toString())) {
                references.add(l.getDestino());
            }
        }
        return references;
    }

    public void killRelation(Value leftValue) {
        for (EjeVariable l : L){
            String origen = l.getOrigen().getNombre();
            if (leftValue.toString().equals(origen)) {
                L.remove(l);
            }
        }
    }


    public void genRelation(Value leftValue, Nodo destino) {
        EjeVariable ejeVariable = new EjeVariable(new Variable(leftValue.toString()),destino);
        L.add(ejeVariable);
    }

    public void genParamRelation(Value leftValue, Nodo destino) {
        EjeVariable ejeVariable = new EjeVariable(new Variable(leftValue.toString()),destino);
        R.add(ejeVariable);
    }

    public FlowSet<EjeVariable> getL() {
        return L;
    }

    public void setL(FlowSet l) {
        L = l;
    }

    public FlowSet<EjeNodo> getE() {
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
}
