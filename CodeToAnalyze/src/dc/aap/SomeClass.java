package dc.aap;

public class SomeClass {
	
	public Integer h;

	public static void entryPoint() {
		SomeClass ble = new SomeClass();
		Object i = new String("2");
		ble.h = new Integer(1);
		
//		Object j = i;
//		SomeClass k = new SomeClass();
//		j = k;
		i = ble.h;
//		i = ble.h;
		
	}
	
}
