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
    public void testvirtualInvokeAssign(){
        verify("virtualInvokeAssign","L:{(temp$0,dc.aap.SomeClass_151), (a,dc.aap.SomeClass_151), (temp$1,java.lang.Integer_166), (i,java.lang.Integer_166)}E:{}");
    }

    @Test
    public void testvirtualInvokeDestroy(){
        verify("virtualInvokeDestroy","L:{(temp$0,dc.aap.SomeClass_156), (a,dc.aap.SomeClass_156), (temp$1,dc.aap.SomeClass_157), (b,dc.aap.SomeClass_141)}E:{}");
    }

    @Test
    public void testvirtualassign(){
        verify("virtualassign","L:{(temp$0,dc.aap.SomeClass_188), (temp$1,dc.aap.SomeClass_189), (b,dc.aap.SomeClass_189), (a,dc.aap.SomeClass_189)}E:{}");
    }


    @Test
    public void testIntefaceInvoke() {
        verify("interfaceInvoke","L:{(temp$0,dc.aap.Cat_171), (p,dc.aap.Cat_171), (temp$1,dc.aap.SomeClass_172), (a,dc.aap.SomeClass_9)}E:{}");}

    @Test
    public void testdualinterfaceInvoke() {
        verify("dualinterfaceInvoke","");
    }

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
