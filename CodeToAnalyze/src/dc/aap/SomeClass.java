package dc.aap;

public class SomeClass {
	
	public Integer h;

	public static void entryPoint() {
//		SomeClass ble = new SomeClass();
		Integer i = new Integer(2);
		Object j = i;
		SomeClass k = new SomeClass();
		j = k;
		
//		ble.h = j;
//		i = ble.h;
		
	}
	
}