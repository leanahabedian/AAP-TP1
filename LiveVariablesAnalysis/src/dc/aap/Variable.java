package dc.aap;

public class Variable {

	@Override
	public String toString() {
		return nombre;
	}

	private String nombre;

	public Variable(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (nombre != null ? !nombre.equals(variable.nombre) : variable.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
}
