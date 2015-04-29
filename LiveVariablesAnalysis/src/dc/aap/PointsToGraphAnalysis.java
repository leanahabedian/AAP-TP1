package dc.aap;

import soot.*;
import soot.util.*;

import java.util.*;

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
            	else if (rightValue instanceof JInstanceFieldRef) { // x = y.f
            		
            	}
        		
        	} 
        	else if (leftValue instanceof JInstanceFieldRef) { // x.f = 
            	if (rightValue instanceof JimpleLocal) {//x.f = y
            		
            		genRelation(dest, leftValue, rightValue);
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
			if (leftValue.equals(first)) {
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
