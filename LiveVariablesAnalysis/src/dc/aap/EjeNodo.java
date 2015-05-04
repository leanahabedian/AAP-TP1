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

}


