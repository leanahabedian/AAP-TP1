package dc.aap.tests;

import dc.aap.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ArraySparseSet;
import soot.toolkits.scalar.FlowSet;

/**
 * Created by ivan on 05/05/15.
 */
public class Ej2PointsToAnalysisTests extends TestCase {

    public SootEnvironment env = new SootEnvironment("dc.aap.SomeClass");

    @Test
    public void testeasyMethod(){
        verify("easyMethod","L:{(temp$0,dc.aap.SomeClass_9), (someClass,dc.aap.SomeClass_9), (temp$1,java.lang.String_10), (temp$3,java.lang.Integer_11), (temp$2,java.lang.Integer_11), (obj,java.lang.Integer_11)}E:{(dc.aap.SomeClass_9,h,java.lang.Integer_11)}R:{}");
    }

    @Test
    public void testifMethodPisandoValor(){
        verify("ifMethodPisandoValor","L:{(b,param_boolean_0), (temp$0,java.lang.Integer_18), (temp$1,dc.aap.SomeClass_20), (temp$3,java.lang.String_24), (temp$2,java.lang.Integer_22), (x,java.lang.String_24)}E:{}R:{}");
    }

    @Test
    public void testifMethodAsignandoValor(){
        verify("ifMethodAsignandoValor","L:{(b,param_boolean_0), (temp$0,java.lang.Integer_31), (temp$1,dc.aap.SomeClass_33), (x,dc.aap.SomeClass_33), (temp$2,java.lang.Integer_35), (x,java.lang.Integer_35), (y,dc.aap.SomeClass_33), (y,java.lang.Integer_35)}E:{}R:{}");
    }

    @Test
    public void testifMethodAsignandoValor2(){
        verify("ifMethodAsignandoValor2","L:{(b,param_boolean_0), (temp$0,java.lang.Integer_44), (temp$1,java.lang.Integer_46), (x,java.lang.Integer_46), (temp$2,java.lang.Integer_48), (x,java.lang.Integer_48), (temp$3,dc.aap.SomeClass_50), (y,dc.aap.SomeClass_50)}E:{(dc.aap.SomeClass_50,h,java.lang.Integer_46), (dc.aap.SomeClass_50,h,java.lang.Integer_48)}R:{}");
    }

    @Test
    public void testifMethodAsignandoValor3() {
        verify("ifMethodAsignandoValor3", "L:{(b,param_boolean_0), (temp$0,java.lang.Integer_58), (temp$1,java.lang.Integer_60), (temp$5,java.lang.Integer_65), (temp$2,java.lang.Integer_62), (temp$4,java.lang.Integer_65), (temp$3,dc.aap.SomeClass_64), (y,dc.aap.SomeClass_64), (x,java.lang.Integer_65)}E:{(dc.aap.SomeClass_64,h,java.lang.Integer_65)}R:{}");
    }

    @Test
    public void testifMethodAsignandoValor4() {
        verify("ifMethodAsignandoValor4", "L:{(b,param_boolean_0), (temp$0,java.lang.Integer_73), (temp$1,java.lang.Integer_75), (x,java.lang.Integer_75), (temp$2,java.lang.Integer_77), (x,java.lang.Integer_77), (temp$3,dc.aap.SomeClass_82), (y,dc.aap.SomeClass_82), (temp$4,dc.aap.SomeClass_84), (y,dc.aap.SomeClass_84)}E:{(dc.aap.SomeClass_82,h,java.lang.Integer_75), (dc.aap.SomeClass_82,h,java.lang.Integer_77), (dc.aap.SomeClass_84,h,java.lang.Integer_75), (dc.aap.SomeClass_84,h,java.lang.Integer_77)}R:{}");
    }

    @Test
    public void testloopmethod() {
        verify("loopMethod", "L:{(temp$0,dc.aap.SomeClass_93), (a,dc.aap.SomeClass_93), (temp$1,java.lang.Integer_95), (a,java.lang.Integer_95)}E:{}R:{}");
    }

    @Test
    public void testweakupdate() {
        verify("weakUpdate", "L:{(temp$0,dc.aap.SomeClass_102), (d,java.lang.Integer_106), (temp$1,java.lang.Integer_103), (b,java.lang.Integer_103), (temp$2,java.lang.Integer_104), (temp$3,dc.aap.SomeClass_105), (c,dc.aap.SomeClass_105), (temp$4,java.lang.Integer_106), (a,dc.aap.SomeClass_105), (e,java.lang.Integer_106)}E:{(dc.aap.SomeClass_102,h,java.lang.Integer_104), (dc.aap.SomeClass_105,h,java.lang.Integer_106)}R:{}");
    }

    @Test
    public void testdoubleNew(){
        verify("doubleNew","L:{(temp$0,dc.aap.SomeClass_114), (temp$1,dc.aap.SomeClass_115), (a,dc.aap.SomeClass_115)}E:{}R:{}");
    }

    @Test
    public void testdoubleRead(){
        verify("doubleRead","L:{(temp$0,dc.aap.SomeClass_119), (a,dc.aap.SomeClass_119), (temp$1,java.lang.Integer_120), (temp$2,dc.aap.SomeClass_121), (b,dc.aap.SomeClass_121), (temp$3,java.lang.Integer_122), (temp$4,java.lang.Integer_122), (x,java.lang.Integer_122)}E:{(dc.aap.SomeClass_119,h,java.lang.Integer_120), (dc.aap.SomeClass_121,h,java.lang.Integer_122)}R:{}");
    }

    @Test
    public void testparameter(){
        verify("parameter","L:{(b,param_boolean_0), (temp$0,java.lang.Integer_128), (i,java.lang.Integer_128)}E:{}R:{}");
    }

    @Test
    public void testparameterRead(){
        verify("parameterRead","L:{(a,param_dc.aap.SomeClass_0), (i,param_dc.aap.SomeClass_0.h_-1)}E:{}R:{(param_dc.aap.SomeClass_0,h,param_dc.aap.SomeClass_0.h_-1)}");
    }

    @Test
    public void testParameterLoad(){
        verify("parameterLoad","L:{(a,param_dc.aap.SomeClass_0), (temp$0,java.lang.Integer_136), (i,java.lang.Integer_136)}E:{(param_dc.aap.SomeClass_0,h,java.lang.Integer_136)}R:{}");
    }

    @Test
    public void testParameterRef(){
        verify("parameterRef","L:{(temp$0,dc.aap.SomeClass_141), (a,dc.aap.SomeClass_141)}E:{}R:{}");
    }


    private void verify(String methodName, String expectedResult){
        UnitGraph easyMethod = env.getUnitGraph(methodName);
        PointsToGraphAnalysis pointsTo = new Ej2PointsToAnalysis(easyMethod);
        assertEquals(expectedResult,getResult(easyMethod,pointsTo));
    }

    private String getResult(UnitGraph easyMethod, PointsToGraphAnalysis pointsTo) {
        Assert.assertEquals("must be only one return statement ", 1, easyMethod.getTails().size());
        PTL after = pointsTo.getFlowAfter(easyMethod.getTails().get(0));
        return "L:"+after.getL().toString() + "E:"+ after.getE().toString() + "R:" + after.getR().toString();
    }

}