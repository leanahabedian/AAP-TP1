package dc.aap;

import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ivan on 06/05/15.
 */
public class Ej4PointsToAnalysis extends PointsToGraphAnalysis {

    public Ej4PointsToAnalysis(DirectedGraph g){
        super(g,new PTL(),new Ej4InvokeHandler());
    }

    public Ej4PointsToAnalysis(DirectedGraph g,PTL initial) {
        super(g,initial,new Ej4InvokeHandler());
    }


}
