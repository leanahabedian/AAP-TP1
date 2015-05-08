package dc.aap;

import soot.Value;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.VirtualInvokeExpr;

public class DummyInvokeHandler extends InvokeHandler {
    @Override
    public Ref dispatchMethodCall(PTL dest, InvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected Ref handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected Ref handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected Ref handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    public void dispatchMethodCallAndAssign(PTL dest, InvokeExpr rightValue, Value leftValue) {

    }
}
