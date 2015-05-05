package dc.aap;

import polyglot.ast.Local;
import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.internal.JAssignStmt;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.FlowSet;

public class StandaloneMain {

	public static void main(String[] args) {
        SootEnvironment sootEnvironment = new SootEnvironment("dc.aap.SomeClass");
        UnitGraph weakUpdate = sootEnvironment.getUnitGraph("parameterLoad");
        PointsToGraphAnalysis an = new Ej2PointsToAnalysis(weakUpdate);
		for (Unit unit : weakUpdate) {
		  FlowSet in = (FlowSet) an.getFlowBefore(unit);
		  FlowSet out = (FlowSet) an.getFlowAfter(unit);
		  if (unit instanceof JAssignStmt) {
              System.out.println("in: " + in.toString());
              System.out.println("code: " + unit.toString());
              System.out.println("out: " + out.toString());
          }
		}
	}	
}
