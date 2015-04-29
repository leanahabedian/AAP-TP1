package dc.aap;

public class Tripla<S1, S2, S3> extends Eje{
	
	private S1 fst;
	private S2 snd;
	private S3 thr;
	
	public Tripla(S1 fst, S2 snd, S3 thr){
		
		this.fst = fst;
		this.snd = snd;
		this.thr = thr;
		
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
		return "("+ fst + "," + snd + "," + thr + ")";
	}

	public S3 getThr() {
		return thr;
	}

	public void setThr(S3 thr) {
		this.thr = thr;
	}
}


