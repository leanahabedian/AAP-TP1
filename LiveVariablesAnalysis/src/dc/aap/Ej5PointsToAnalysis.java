package dc.aap;

import soot.toolkits.graph.DirectedGraph;


public class Ej5PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej5PointsToAnalysis(DirectedGraph g) {
        super(g,new PTL(),new Ej5InvokeHandler());
    }
}
