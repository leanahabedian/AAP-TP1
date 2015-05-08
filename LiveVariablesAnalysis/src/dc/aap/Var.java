package dc.aap;

public class Var {

	@Override
	public String toString() {
		return name;
	}

	private String name;

	public Var(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Var var = (Var) o;

        if (name != null ? !name.equals(var.name) : var.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
