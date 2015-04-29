package dc.aap;

public class Tupla<S1, S2> {
	
	private S1 fst;
	private S2 snd;
	
	public Tupla(S1 fst, S2 snd){
		
		this.fst = fst;
		this.snd = snd;
		
	}
	
	public S2 getSnd() {
		return snd;
	}
	
	public void setSnd(S2 snd) {
		this.snd = snd;
	}
	
	public S1 getFst() {
		return fst;
	}
	
	public void setFst(S1 fst) {
		this.fst = fst;
	}
	
	@Override
	public String toString() {
		return "("+ fst + "," + snd + ")";
	}
}


