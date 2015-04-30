package dc.aap;

import soot.*;
import soot.util.*;

import java.util.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import soot.jimple.*;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JNewExpr;
import soot.jimple.internal.JimpleLocal;
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

//        // KILL
//        // Take out kill set
//        Iterator boxIt = s.getDefBoxes().iterator();
//        while (boxIt.hasNext()) {
//            ValueBox box = (ValueBox) boxIt.next();
//            Value value = box.getValue();
//            if (value instanceof Local)
//                dest.remove(value);
//        }

        // GEN
        // Add gen set
        int lineNumber = s.getJavaSourceStartLineNumber();
        if (s.getDefBoxes().size() > 0 && s.getUseBoxes().size() > 0){ // solo soportamos 1 solo uso por enunciado de TP
        	ValueBox left = s.getDefBoxes().get(0);
        	Value leftValue = left.getValue();
        	ValueBox right = s.getUseBoxes().get(0);
        	Value rightValue = right.getValue();
        	
        	if (leftValue instanceof JimpleLocal) { // x = ..
        		if (rightValue instanceof JNewExpr) { //x = new A()
        			
        			killRelation(dest, leftValue);
            		genRelation(dest, leftValue, rightValue);
    			}
            	else if (rightValue instanceof JimpleLocal) {//x = y
            		
            		killRelation(dest, leftValue);
            		
            		for (Object relation : dest) {
						Value first = ((Tupla<Value,Value>)relation).getFst(); 
						if (rightValue.equals(first)) {
							genRelation(dest, leftValue, ((Tupla<Value,Value>)relation).getSnd());
		                	break;
						}
					}
            		
    			}
            	else if (rightValue instanceof JInstanceFieldRef) { // leftValue = owner.label
            		
            		killRelation(dest, leftValue);
            		
            		String label = ((JInstanceFieldRef) rightValue).getFieldRef().name();
            		right = s.getUseBoxes().get(1);
                	Value owner = right.getValue();

            		// GEN
            		for (Object relation : dest) {
            			
            			Eje eje = (Eje)relation;
            			
            			if (eje instanceof Tripla){
            				
            				Value first = ((Tripla<Value, String, Value>) eje).getFst(); 
    						
            				if (owner.equals(first) && label.equals(((Tripla<Value, String, Value>) eje).getSnd())) {
    							
            					Value to = ((Tripla<Value, String, Value>) eje).getThr();
    							Tupla<Value,Value> tupla = new Tupla<Value, Value>(leftValue, to);
    		            		dest.add(tupla);
    		                	break;
    						}
            			}
						
					}
            		
            	}
        		
        	} 
        	else if (leftValue instanceof JInstanceFieldRef) { // x.f = 
            	if (rightValue instanceof JimpleLocal) {//x.f = y
            		
                	right = s.getUseBoxes().get(1);
                	rightValue = right.getValue();
            		String label = ((JInstanceFieldRef) leftValue).getFieldRef().name();
            		Value owner = ((JInstanceFieldRef) leftValue).getBaseBox().getValue();
            		
            		// KILLER
            		for (Object relation : dest) {
            			if(relation instanceof Tripla){
            				Value first = ((Tripla<Value,String,Value>)relation).getFst();
            				String second = ((Tripla<Value,String,Value>)relation).getSnd();
            				
                			if (owner.toString().equals(first.toString()) && label.equals(second)) {
                				dest.remove(relation);
                				break;
                			}	
            			}
            			
            		}
            		
            		// GEN
            		for (Object relation : dest) {
						Value first = ((Tupla<Value,Value>)relation).getFst(); 
						if (rightValue.equals(first)) {
							Tripla<Value,String,Value> tripla = new Tripla<Value, String, Value>(owner, label, ((Tupla<Value,Value>)relation).getSnd());
		            		dest.add(tripla);
		                	break;
						}
					}
            		
            		
    			}
        	}
        }
    }

	private void genRelation(FlowSet dest, Value leftValue, Value rightValue) {
		Tupla<Value,Value> pair = new Tupla<Value,Value>(leftValue,rightValue);
		dest.add(pair);
	}

	private void killRelation(FlowSet dest, Value leftValue) {
		for (Object relation : dest) {
			Value first = ((Tupla<Value,Value>)relation).getFst();
			if (leftValue.toString().equals(first.toString())) {
				dest.remove(relation);
				break;
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
