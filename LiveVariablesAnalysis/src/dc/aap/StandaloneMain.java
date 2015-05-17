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

        String classToAnalyse = "dc.aap.SomeClass";
        String methodToAnalyse = "dualinterfaceInvoke";
        String cp = "/usr/lib/jvm/jdk1.7.0_75/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunjce_provider.jar:/home/ivan/Documents/cetis/AAP-TP1/CodeToAnalyze/src:/usr/lib/jvm/jdk1.7.0_75/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/localedata.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jsse.jar:/home/lnahabedian/Desktop/workspace/CodeToAnalyze/bin/:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rhino.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jsse.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rhino.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/localedata.jar";

        SootEnvironment sootEnvironment = new SootEnvironment(classToAnalyse,cp);
        UnitGraph weakUpdate = sootEnvironment.getUnitGraph(methodToAnalyse);
        PointsToGraphAnalysis an = new Ej1PointsToAnalysis(weakUpdate);
		for (Unit unit : weakUpdate) {
		  PTL in = an.getFlowBefore(unit);
		  PTL out = an.getFlowAfter(unit);
          System.out.println("in: " + in.toString());
          System.out.println("code: " + unit.toString());
          System.out.println("out: " + out.toString());
          System.out.println("-----------------");
		}
	}	
}
