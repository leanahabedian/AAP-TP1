package dc.aap;

import soot.toolkits.graph.DirectedGraph;


public class Ej5PointsToAnalysis extends PointsToGraphAnalysis {
    public Ej5PointsToAnalysis(DirectedGraph g) {
        super(g);
    }

    public Ej5PointsToAnalysis(DirectedGraph g, PTL initial) {
        super(g, initial, new Ej5InvokeHandler());
    }
}
