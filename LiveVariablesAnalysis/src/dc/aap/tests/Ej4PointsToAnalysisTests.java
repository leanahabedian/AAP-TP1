package dc.aap.tests;

import dc.aap.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import soot.toolkits.graph.UnitGraph;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej4PointsToAnalysisTests extends TestCase {

    public SootEnvironment env = new SootEnvironment("dc.aap.SomeClass");

    @Test
    public void testVirtualInvoke(){
        verify("virtualInvoke","L:{(temp$0,dc.aap.SomeClass_145), (a,dc.aap.SomeClass_145), (temp$1,java.lang.Integer_146), (i,java.lang.Integer_128)}E:{}");
    }

    @Test
    public void testIntefaceInvoke() { verify("interfaceInvoke","");}


    private void verify(String methodName, String expectedResult){
        UnitGraph method = env.getUnitGraph(methodName);
        PointsToGraphAnalysis pointsTo = new Ej4PointsToAnalysis(method);
        assertEquals(expectedResult,getResult(method,pointsTo));
    }

    private String getResult(UnitGraph easyMethod, PointsToGraphAnalysis pointsTo) {
        Assert.assertEquals("must be only one return statement ", 1, easyMethod.getTails().size());
        PTL after = pointsTo.getFlowAfter(easyMethod.getTails().get(0));
        return "L:"+after.getL().toString() + "E:"+ after.getE().toString();
    }

}
