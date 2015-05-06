package dc.aap.tests;

import dc.aap.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import soot.toolkits.graph.UnitGraph;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej3PointsToAnalysisTests extends TestCase {

    public SootEnvironment env = new SootEnvironment("dc.aap.SomeClass");

    @Test
    public void testCall(){
        verify("call","");
    }


    private void verify(String methodName, String expectedResult){
        UnitGraph easyMethod = env.getUnitGraph(methodName);
        PointsToGraphAnalysis pointsTo = new Ej3PointsToAnalysis(easyMethod);
        assertEquals(expectedResult,getResult(easyMethod,pointsTo));
    }

    private String getResult(UnitGraph easyMethod, PointsToGraphAnalysis pointsTo) {
        Assert.assertEquals("must be only one return statement ", 1, easyMethod.getTails().size());
        PTL after = pointsTo.getFlowAfter(easyMethod.getTails().get(0));
        return "L:"+after.getL().toString() + "E:"+ after.getE().toString()
                + "R:" + after.getR().toString() + "W:" + after.getW().toString();
    }

}
