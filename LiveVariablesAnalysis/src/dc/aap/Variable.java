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

}
