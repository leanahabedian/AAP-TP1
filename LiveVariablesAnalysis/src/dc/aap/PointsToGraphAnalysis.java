package dc.aap;

import soot.*;
import soot.util.*;

import java.util.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import soot.jimple.*;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JNewExpr;
import soot.jimple.internal.JimpleLocal;
import soot.tagkit.Tag;
import soot.toolkits.graph.*;
import soot.toolkits.scalar.*;

class PointsToGraphAnalysis extends ForwardFlowAnalysis
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
        if (s.getDefBoxes().size() > 0 && s.getUseBoxes().size() > 0){ // solo soportamos 1 solo uso por enunciado de TP
        	ValueBox left = s.getDefBoxes().get(0);
        	Value leftValue = left.getValue();
        	ValueBox right = s.getUseBoxes().get(0);
        	Value rightValue = right.getValue();
        	
        	if (leftValue instanceof JimpleLocal) { // x = ..
        		if (rightValue instanceof JNewExpr) { //x = new A()
        			if (alreadyDefined(dest,leftValue,rightValue, lineNumber)) return;
        			killRelation(dest, leftValue);
            		genRelation(dest, leftValue, rightValue.getType() + "_" + lineNumber);
    			}
            	else if (rightValue instanceof JimpleLocal) {//x = y
            		
        			if (alreadyDefined(dest,leftValue,rightValue, lineNumber)) return;

            		killRelation(dest, leftValue);
            		
            		for (Object relation : dest) {
						if (relation instanceof EjeVariable){
							String origen = ((EjeVariable)relation).getOrigen().getNombre();
							if (rightValue.toString().equals(origen)) {
								genRelation(dest, leftValue, ((EjeVariable)relation).getDestino());
							}
						}
					}
    			}
            	else if (rightValue instanceof JInstanceFieldRef) { // x = y.f
            		
            		killRelation(dest, leftValue);
            		
            		String label = ((JInstanceFieldRef) rightValue).getFieldRef().name();
            		right = s.getUseBoxes().get(1);
                	Value owner = right.getValue();
                	
            		// GEN
                	for (Object relation : dest) {
                		
                		if (relation instanceof EjeVariable){
                    		EjeVariable ejeVar = (EjeVariable)relation;
                    		if (ejeVar.getOrigen().getNombre().equals(owner.toString())){
                    			for (Object ejeNodo : dest) {
									if (ejeNodo instanceof EjeNodo 
											&& ((EjeNodo) ejeNodo).getOrigen().equals(ejeVar.getDestino())
											&& ((EjeNodo) ejeNodo).getEtiqueta().equals(label)){
										
										genRelation(dest, leftValue, ((EjeNodo)ejeNodo).getDestino());
									}
								}
                    		}
                		}
					}
            	}
        	} else if (leftValue instanceof JInstanceFieldRef) { // x.f = 
            	if (rightValue instanceof JimpleLocal) {//x.f = y
            		
                	right = s.getUseBoxes().get(1);
                	rightValue = right.getValue();
            		String label = ((JInstanceFieldRef) leftValue).getFieldRef().name();
            		Value owner = ((JInstanceFieldRef) leftValue).getBaseBox().getValue();
            		
//            		// KILLER Diego dijo no kill
            		
            		// GEN
            		List<Nodo> ownerReferences = getReferencias(dest, owner);
            		
            		List<Nodo> rightReferences = getReferencias(dest, rightValue);
            		
            		for (Nodo origen : ownerReferences) {
						for (Nodo destino : rightReferences) {
							dest.add(new EjeNodo(origen, label, destino));
						}
					}
    			}
        	}
        }
    
    }

	private List<Nodo> getReferencias(FlowSet dest, Value owner) {
		List<Nodo> references = new ArrayList<Nodo>();
		for (Object relation : dest) {
			if (relation instanceof EjeVariable && ((EjeVariable)relation).getOrigen().getNombre().equals(owner.toString())) {
				references.add(((EjeVariable)relation).getDestino());
			}
		}
		return references;
	}

	private boolean alreadyDefined(FlowSet dest, Value leftValue, Value rightValue, int lineNumber) {
		
		for (Object relation : dest) {
			if (relation instanceof EjeVariable){
				EjeVariable eje = (EjeVariable) relation;
				String nombreNodo = rightValue.getType().toString()+ "_"+ lineNumber;
				if (eje.getOrigen().getNombre().equals(leftValue.toString()) && eje.getDestino().getNombre().equals(nombreNodo)){
					return true;
				}
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
        	lineNumber = tagLine.getValue()[1];
        }
		return lineNumber;
	}

	private void genRelation(FlowSet dest, Value leftValue, String rightValue) {
		EjeVariable ejeVariable = new EjeVariable(new Variable(leftValue.toString()),new Nodo(rightValue.toString()));
		dest.add(ejeVariable);
	}
	
	private void genRelation(FlowSet dest, Value leftValue, Nodo destino) {
		EjeVariable ejeVariable = new EjeVariable(new Variable(leftValue.toString()),destino);
		dest.add(ejeVariable);
	}
	
	private void killRelation(FlowSet dest, Value leftValue) {
		for (Object relation : dest) {
			if (relation instanceof EjeVariable){
				String origen = ((EjeVariable)relation).getOrigen().getNombre();
				if (leftValue.toString().equals(origen)) {
					dest.remove(relation);
				}
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

    PointsToGraphAnalysis(DirectedGraph g)
    {
        super(g);

        doAnalysis();
    }
}
