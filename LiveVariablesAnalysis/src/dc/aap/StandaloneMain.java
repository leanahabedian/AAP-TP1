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
		// Cambiar este classpath por el correspondiente a su entorno.
		// la manera más fácil es correr un análisis de Soot que ya existe, y copiar el classpath de la consola de soot
		// Además hay que agregar a mano los paths del proyecto que quieren analizar ("CodeToAnalyze" del classpath de ejemplo)  
		String cp = "/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/sunjce_provider.jar:/CodeToAnalyze/src:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/jsse.jar:/home/lnahabedian/Desktop/workspace/CodeToAnalyze/bin/:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rt.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rhino.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/icedtea-sound.jar::/home/lnahabedian/Desktop/workspace/CodeToAnalyze/src:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rt.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/jsse.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/rhino.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-7-openjdk-amd64/jre/lib/ext/localedata.jar"; 				
     	Scene.v().setSootClassPath(cp);
     	Options.v().keep_line_number();
     	Options.v().setPhaseOption("jb" , "use-original-names:true");
		SootClass c = Scene.v().loadClassAndSupport("dc.aap.SomeClass");		
		c.setApplicationClass();
		SootMethod m = c.getMethodByName("entryPoint");
		Scene.v().loadNecessaryClasses();
		Body b = m.retrieveActiveBody();
		UnitGraph g = new ExceptionalUnitGraph(b);
//		LiveVariablesAnalysis an = new LiveVariablesAnalysis(g);
//		for (Unit unit : g) {
//		  FlowSet in = (FlowSet) an.getFlowBefore(unit);
//		  FlowSet out = (FlowSet) an.getFlowAfter(unit);
//		  System.out.println("in: " + in.toString());
//		  System.out.println("out: "+ out.toString());
//		}
		PointsToGraphAnalysis an = new PointsToGraphAnalysis(g);
		for (Unit unit : g) {
		  FlowSet in = (FlowSet) an.getFlowBefore(unit);
		  FlowSet out = (FlowSet) an.getFlowAfter(unit);
		  if (unit instanceof JAssignStmt){
			  System.out.println("in: " + in.toString());
			  System.out.println("code: " + unit.toString());
			  System.out.println("out: "+ out.toString());
		  }
		  
		}
	}	
}
