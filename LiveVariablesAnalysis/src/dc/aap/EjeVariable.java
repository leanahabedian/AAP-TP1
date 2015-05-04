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

}


