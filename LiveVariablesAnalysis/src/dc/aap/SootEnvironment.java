package dc.aap;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.options.Options;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.FlowAnalysis;
import soot.toolkits.scalar.ForwardFlowAnalysis;


public class SootEnvironment {

    private SootClass c;

    public SootEnvironment(String clazz) {
        String cp = "/usr/lib/jvm/jdk1.7.0_75/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunjce_provider.jar:/home/ivan/Documents/cetis/AAP-TP1/CodeToAnalyze/src:/usr/lib/jvm/jdk1.7.0_75/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/localedata.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jsse.jar:/home/lnahabedian/Desktop/workspace/CodeToAnalyze/bin/:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rhino.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/resources.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rt.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jsse.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/jce.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/charsets.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/rhino.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/dnsns.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/java-atk-wrapper.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/zipfs.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/jdk1.7.0_75/jre/lib/ext/localedata.jar";
        Scene.v().setSootClassPath(cp);
        Options.v().set_keep_line_number(true);
        Options.v().setPhaseOption("jb" , "use-original-names:true");
        c = Scene.v().loadClassAndSupport(clazz);
        c.setApplicationClass();
        Scene.v().loadNecessaryClasses();
    }

    public UnitGraph getUnitGraph(String methodName) {
        SootMethod m = c.getMethodByName(methodName);
        Body b = m.retrieveActiveBody();
        return new ExceptionalUnitGraph(b);
    }

}
