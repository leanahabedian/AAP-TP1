package dc.aap;

import soot.Value;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.VirtualInvokeExpr;

/**
 * Created by ivan on 08/05/15.
 */
public abstract class InvokeHandler {
    public abstract Ref dispatchMethodCall(PTL dest, InvokeExpr invokeExpr);

    protected abstract Ref handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr);

    protected abstract Ref handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr);

    protected abstract Ref handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr);

    public abstract void dispatchMethodCallAndAssign(PTL dest, InvokeExpr rightValue, Value leftValue);
}
