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

        if (args.length == 0) {
            throw new RuntimeException("args[0] can't be null. Must be the class to analyse ej: dc.aap.SomeClass (this should be at the soot class path");
        }
        if (args.length == 1) {
            throw new RuntimeException("args[1] can't be null. Must be the method to analyse");
        }
        if (args.length == 2) {
            throw new RuntimeException("args[2] must be the soot class path. The /src folder of the classes to analyse and the rt.jar is enough");
        }
        if (args.length == 3) {
            throw new RuntimeException("args[3] must be 1,2,4,5");
        }

        String classToAnalyse = args[0];
        String methodToAnalyse = args[1];
        String cp = args[2];
        String ej = args[3];

    //        String cp = "/usr/lib/jvm/jdk1.7.0_75/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunjce_provider.jar:/home/ivan/Documents/cetis/AAP-TP1/CodeToAnalyze/src:/usr/lib/jvm/jdk1.7.0_75/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/localedata.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jsse.jar:/home/lnahabedian/Desktop/workspace/CodeToAnalyze/bin/:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rhino.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jsse.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rhino.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/localedata.jar";

        SootEnvironment sootEnvironment = new SootEnvironment(classToAnalyse,cp);
        UnitGraph unitGraph = sootEnvironment.getUnitGraph(methodToAnalyse);
        PointsToGraphAnalysis an;
        switch (ej){
            case "1":
                an = new Ej1PointsToAnalysis(unitGraph);
                break;
            case "2":
                an = new Ej2PointsToAnalysis(unitGraph);
                break;
            case "4":
                an = new Ej4PointsToAnalysis(unitGraph);
                break;
            case "5":
                an = new Ej5PointsToAnalysis(unitGraph);
                break;
            default:
                throw new RuntimeException("args[3] must be 1,2,4 or 5. Cannot be: " + ej);
        }
		for (Unit unit : unitGraph) {
		  PTL in = an.getFlowBefore(unit);
		  PTL out = an.getFlowAfter(unit);
          System.out.println("in: " + in.toString());
          System.out.println("code: " + unit.toString());
          System.out.println("out: " + out.toString());
          System.out.println("-----------------");
		}
	}	
}
