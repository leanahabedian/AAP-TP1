package dc.aap;

import soot.SootMethod;
import soot.Value;
import soot.jimple.InterfaceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.VirtualInvokeExpr;

import java.util.List;

/**
 * Created by ivan on 08/05/15.
 */
public abstract class InvokeHandler {
    protected SootMethod rootMethod;

    protected abstract List<Ref> handleMethodCall(PTL dest, VirtualInvokeExpr invokeExpr);

    protected abstract List<Ref> handleMethodCall(PTL dest, InterfaceInvokeExpr invokeExpr);

    protected abstract List<Ref> handleMethodCall(PTL dest, StaticInvokeExpr invokeExpr);

    public void dispatchMethodCallAndAssign(SootMethod rootMethod,PTL dest, InvokeExpr rightValue, Value leftValue){
        this.rootMethod = rootMethod;
        dispatchMethodCallAndAssign(dest,rightValue,leftValue);
    }


    protected abstract void dispatchMethodCallAndAssign(PTL dest, InvokeExpr rightValue, Value leftValue);

    public List<Ref> dispatchMethodCall(SootMethod rootMethod,PTL dest, InvokeExpr invokeExpr){
        this.rootMethod = rootMethod;
        return dispatchMethodCall(dest,invokeExpr);
    }

    protected List<Ref> dispatchMethodCall(PTL dest, InvokeExpr invokeExpr) {
        if (invokeExpr instanceof VirtualInvokeExpr) {
            return handleMethodCall(dest,(VirtualInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof InterfaceInvokeExpr) {
            return handleMethodCall(dest,(InterfaceInvokeExpr) invokeExpr);
        }

        if (invokeExpr instanceof StaticInvokeExpr) {
            return handleMethodCall(dest,(StaticInvokeExpr) invokeExpr);
        }
        return null;
    }
}
