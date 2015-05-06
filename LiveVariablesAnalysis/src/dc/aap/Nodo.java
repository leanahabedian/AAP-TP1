package dc.aap;

public class Nodo {

	private String nombre;
    private int lineNumber;

	public Nodo(String valor,int lineNumber) {
		this.nombre = valor;
        this.lineNumber = lineNumber;
	}
	
	public String getClassName() {
		return nombre;
	}

	@Override
	public String toString() {
		return nombre + "_" + lineNumber;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nodo nodo = (Nodo) o;

        if (lineNumber != nodo.lineNumber) return false;
        if (!nombre.equals(nodo.nombre)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombre.hashCode();
        result = 31 * result + lineNumber;
        return result;
    }
}

