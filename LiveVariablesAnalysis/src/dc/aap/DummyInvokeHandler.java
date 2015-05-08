package dc.aap;

import soot.Value;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.VirtualInvokeExpr;

import java.util.List;

public class DummyInvokeHandler extends InvokeHandler {
    @Override
    public List<Ref> dispatchMethodCall(PTL dest, InvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected List<Ref> handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected List<Ref> handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    protected List<Ref> handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr) {
        return null;
    }

    @Override
    public void dispatchMethodCallAndAssign(PTL dest, InvokeExpr rightValue, Value leftValue) {

    }
}
