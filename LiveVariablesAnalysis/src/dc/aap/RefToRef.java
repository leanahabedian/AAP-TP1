package dc.aap;

public class RefToRef {
	
	private Ref origin;
	private String field;
	private Ref destiny;
	
	public RefToRef(Ref origin, String field, Ref destiny){
		
		this.origin = origin;
		this.field = field;
		this.destiny = destiny;
		
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public Ref getOrigin() {
		return origin;
	}
	
	public void setOrigin(Ref origin) {
		this.origin = origin;
	}
	
	@Override
	public String toString() {
		return "("+ origin + "," + field + "," + destiny + ")";
	}

	public Ref getDestiny() {
		return destiny;
	}

	public void setDestiny(Ref destiny) {
		this.destiny = destiny;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefToRef refToRef = (RefToRef) o;

        if (!destiny.equals(refToRef.destiny)) return false;
        if (!field.equals(refToRef.field)) return false;
        if (!origin.equals(refToRef.origin)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = origin.hashCode();
        result = 31 * result + field.hashCode();
        result = 31 * result + destiny.hashCode();
        return result;
    }
}


