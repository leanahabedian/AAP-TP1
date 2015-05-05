package dc.aap;

public class EjeVariable {
	
	private Variable origen;
	private Nodo destino;
	
	public EjeVariable(Variable origen, Nodo destino){
		
		this.origen = origen;
		this.destino = destino;
		
	}
	
	public Nodo getDestino() {
		return destino;
	}
	
	public void setDestino(Nodo destino) {
		this.destino = destino;
	}
	
	public Variable getOrigen() {
		return origen;
	}
	
	public void setOrigen(Variable origen) {
		this.origen = origen;
	}
	
	@Override
	public String toString() {
		return "("+ origen + "," + destino + ")";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EjeVariable that = (EjeVariable) o;

        if (!destino.equals(that.destino)) return false;
        if (!origen.equals(that.origen)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = origen.hashCode();
        result = 31 * result + destino.hashCode();
        return result;
    }
}


