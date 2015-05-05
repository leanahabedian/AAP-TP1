package dc.aap;

public class EjeNodo{
	
	private Nodo origen;
	private String etiqueta;
	private Nodo destino;
	
	public EjeNodo(Nodo origen, String etiqueta, Nodo destino){
		
		this.origen = origen;
		this.etiqueta = etiqueta;
		this.destino = destino;
		
	}
	
	public String getEtiqueta() {
		return etiqueta;
	}
	
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	
	public Nodo getOrigen() {
		return origen;
	}
	
	public void setOrigen(Nodo origen) {
		this.origen = origen;
	}
	
	@Override
	public String toString() {
		return "("+ origen + "," + etiqueta + "," + destino + ")";
	}

	public Nodo getDestino() {
		return destino;
	}

	public void setDestino(Nodo destino) {
		this.destino = destino;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EjeNodo ejeNodo = (EjeNodo) o;

        if (!destino.equals(ejeNodo.destino)) return false;
        if (!etiqueta.equals(ejeNodo.etiqueta)) return false;
        if (!origen.equals(ejeNodo.origen)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = origen.hashCode();
        result = 31 * result + etiqueta.hashCode();
        result = 31 * result + destino.hashCode();
        return result;
    }
}


