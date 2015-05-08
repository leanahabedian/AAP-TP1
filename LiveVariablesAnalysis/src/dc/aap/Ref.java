package dc.aap;

public class Ref {

	private String name;
    private int lineNumber;

	public Ref(String valor, int lineNumber) {
		this.name = valor;
        this.lineNumber = lineNumber;
	}
	
	public String getClassName() {
		return name;
	}

	@Override
	public String toString() {
		return name + "_" + lineNumber;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ref ref = (Ref) o;

        if (lineNumber != ref.lineNumber) return false;
        if (!name.equals(ref.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lineNumber;
        return result;
    }
}

