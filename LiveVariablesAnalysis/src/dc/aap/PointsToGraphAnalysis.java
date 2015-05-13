package dc.aap;

import soot.*;

import soot.jimple.*;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JNewExpr;
import soot.jimple.internal.JimpleLocal;
import soot.tagkit.SourceLnPosTag;
import soot.tagkit.Tag;
import soot.toolkits.graph.*;
import soot.toolkits.scalar.*;

public abstract class PointsToGraphAnalysis extends ForwardFlowAnalysis<Unit,PTL>
{
    private SootMethod method;
    private InvokeHandler invokeHandler = new DummyInvokeHandler();
    private ParameterHandler parameterHandler = new DummyParameterHandler();
    private PTL initial = new PTL();

    public PointsToGraphAnalysis(DirectedGraph g)
    {
        super(g);
        doAnalysis();
    }

    public PointsToGraphAnalysis(DirectedGraph g, PTL initial)
    {
        super(g);
        this.initial = initial;
        doAnalysis();
    }

    public PointsToGraphAnalysis(DirectedGraph g, PTL initial, InvokeHandler invokeHandler)
    {
        super(g);
        this.method = ((ExceptionalUnitGraph) graph).getBody().getMethod();
        this.initial = initial;
        this.invokeHandler = invokeHandler;
        doAnalysis();
    }

    public PointsToGraphAnalysis(DirectedGraph g, ParameterHandler parameterHandler)
    {
        super(g);
        this.parameterHandler = parameterHandler;
        doAnalysis();
    }


    @Override
    protected void copy(PTL src, PTL dest)
    {
        src.copy(dest);
    }

    @Override
    protected void merge(PTL src1, PTL src2, PTL dest)
    {

        src1.getE().union(src2.getE(),dest.getE());
        src1.getL().union(src2.getL(),dest.getL());
    }

    @Override
    protected void flowThrough(PTL src, Unit unit,
                               PTL dest)
    {
        copy(src,dest);

        int lineNumber = getLineNumber(unit);

        // GEN
        // Add gen set
        if (isAssign(unit)){ // solo soportamos 1 solo uso por enunciado de TP
            ValueBox left = unit.getDefBoxes().get(0);
            Value leftValue = left.getValue();
            ValueBox right = unit.getUseBoxes().get(0);
            Value rightValue = right.getValue();

            if (isLocal(leftValue)) { // x = ..
                handleLocal(dest, unit, lineNumber, leftValue, rightValue);
            } else if (isFieldRef(leftValue)) { // x.f =
                handleFieldRef(dest, unit, (JInstanceFieldRef) leftValue, rightValue);
            }
        }
        if (isMethodCall(unit)){
            invokeHandler.dispatchMethodCall(this.method,dest, ((InvokeStmt) unit).getInvokeExpr());
        }

    }


    private boolean isAssign(Unit s) {
        return s.getDefBoxes().size() > 0 && s.getUseBoxes().size() > 0;
    }

    private boolean isLocal(Value leftValue) {
        return leftValue instanceof JimpleLocal;
    }

    private void handleLocal(PTL dest, Unit s, int lineNumber, Value leftValue, Value rightValue) {
        ValueBox right;
        if (isNewExpression(rightValue)) { //x = new A()
            if (dest.hasRelation(leftValue, rightValue, lineNumber)) return;
            dest.killVarToRef(leftValue);
            dest.genVarToRef(leftValue, new Ref(rightValue.getType().toString(), lineNumber));
        }
        else if (isLocal(rightValue)) {//x = y

            if (dest.hasRelation(leftValue, rightValue, lineNumber)) return;
            dest.killVarToRef(leftValue);
            dest.copyRefs(rightValue,leftValue);
        }
        else if (isFieldRef(rightValue)) { // x = y.f

            dest.killVarToRef(leftValue);

            String field = ((JInstanceFieldRef) rightValue).getFieldRef().name();
            right = s.getUseBoxes().get(1);
            Value y = right.getValue();

            // GEN

            for (Ref ref : dest.getRefsToRef(y, field)) {
                dest.genVarToRef(leftValue, ref);
            }
            parameterHandler.handleParameterFresh(dest, leftValue, y, field);
        }
        else if (isParameterDefinition(rightValue)){
            parameterHandler.handleParameterDefinition(dest, leftValue, (ParameterRef) rightValue);
        }
        else if (isMethodCall(rightValue)){
            invokeHandler.dispatchMethodCallAndAssign(this.method,dest, (InvokeExpr) rightValue, leftValue);
        }
    }

    private boolean isNewExpression(Value rightValue) {return rightValue instanceof JNewExpr;}

    private boolean isFieldRef(Value leftValue) {return leftValue instanceof JInstanceFieldRef;}

    private boolean isParameterDefinition(Value v) {
        return v instanceof ParameterRef;
    }

    protected boolean isMethodCall(Value rightValue){return rightValue instanceof InvokeExpr;}

    private void handleFieldRef(PTL dest, Unit s, JInstanceFieldRef leftValue, Value rightValue) {
        ValueBox right;
        if (isLocal(rightValue)) {//x.f = y

            right = s.getUseBoxes().get(1);
            rightValue = right.getValue();
            String label = leftValue.getFieldRef().name();
            Value owner = leftValue.getBaseBox().getValue();
            // KILL es opcional (Diego)

            dest.genRefToRef(owner,label,rightValue);
        }
    }

    private boolean isMethodCall(Unit unit) {return unit instanceof InvokeStmt ;}


    private int getLineNumber(Unit s) {
        Stmt statement = (Stmt) s;
        Tag tagLine = null;
        int lineNumber = -1;
        if (statement.getTags().size() > 0) {
            tagLine = statement.getTags().get(0);
            lineNumber = ((SourceLnPosTag)tagLine).startLn();
        }
        return lineNumber;
    }

    @Override
    protected PTL entryInitialFlow()
    {
        return initial;
    }

    @Override
    protected PTL newInitialFlow()
    {
        return new PTL();
    }



}
