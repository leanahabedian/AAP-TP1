package dc.aap;

import soot.SootMethod;


public class PrintCallGraphBuilder implements CallGraphBuilder {

    @Override
    public void addEdge(SootMethod rootMethod, SootMethod method) {
        System.out.println("\"" + rootMethod.toString() + "\"" + " -> " +"\"" + method.toString()+"\"");
    }
}
