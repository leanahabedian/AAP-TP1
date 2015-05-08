package dc.aap;

public class VarToRef {
	
	private Var var;
	private Ref ref;
	
	public VarToRef(Var var, Ref ref){
		
		this.var = var;
		this.ref = ref;
		
	}
	
	public Ref getRef() {
		return ref;
	}
	
	public void setRef(Ref ref) {
		this.ref = ref;
	}
	
	public Var getVar() {
		return var;
	}
	
	public void setVar(Var var) {
		this.var = var;
	}
	
	@Override
	public String toString() {
		return "("+ var + "," + ref + ")";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VarToRef that = (VarToRef) o;

        if (!ref.equals(that.ref)) return false;
        if (!var.equals(that.var)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = var.hashCode();
        result = 31 * result + ref.hashCode();
        return result;
    }
}


