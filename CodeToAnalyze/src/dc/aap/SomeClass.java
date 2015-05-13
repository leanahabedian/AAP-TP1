package dc.aap;

public class SomeClass {
	
	public Integer h;

	public static void easyMethod() {
		
		SomeClass someClass = new SomeClass();
		Object obj = new String("2");
		someClass.h = new Integer(1);
		obj = someClass.h;
		
	} // Resultado esperado: {(someClass,dc.app.SomeClass_9),(obj,java.lang.Integer_11),(someclass,h,java.lang.Integer_11)}
	
	public static void ifMethodPisandoValor(boolean b) {
		
		Object x = new Integer(1);
		if (b){
			x = new SomeClass();
		} else {
			x = new Integer(1);
		}
		x = new String("a");
		// Resultado esperado: {(x,java.lang.String_24)}, ya que  la última asignación pisa las dos asignaciones previas resultado del if.

	}

	public static void ifMethodAsignandoValor(boolean b) {
		
		Object x = new Integer(1);
		if (b){
			x = new SomeClass();
		} else {
			x = new Integer(1);
		}
		Object y = x;
		// Resultado esperado: {(y,java.lang.Integer_35),(y,dc.aap.SomeClass_33),(x,java.lang.Integer_35),(x,dc.aap.SomeClass_33)}.

	}
	
	public static void ifMethodAsignandoValor2(boolean b) {
		
		Integer x = new Integer(1);
		if (b){
			x = new Integer(0);
		} else {
			x = new Integer(1);
		}
		SomeClass y = new SomeClass();
		y.h = x;
		// Resultado esperado: {(dc.aap.SomeClass_50,h,java.lang.Integer_46),(dc.aap.SomeClass_50,h,java.lang.Integer_48),(x,java.lang.Integer_46),(x,java.lang.Integer_48),(y,dc.aap.SomeClass_50)}.

	}
	
public static void ifMethodAsignandoValor3(boolean b) {
		
		Integer x = new Integer(1);
		if (b){
			x = new Integer(0);
		} else {
			x = new Integer(1);
		}
		SomeClass y = new SomeClass();
		y.h = new Integer(1);
		x = y.h;
		// Resultado esperado: {(x,java.lang.Integer_65),(dc.aap.SomeClass_64,h,java.lang.Integer_65),(y,dc.aap.SomeClass_64)}.

	}
	
	public static void ifMethodAsignandoValor4(boolean b) {
		
		Integer x = new Integer(1);
		if (b){
			x = new Integer(0);
		} else {
			x = new Integer(1);
		}
		
		SomeClass y;
		if (b) {
			y = new SomeClass();	
		} else {
			y = new SomeClass();
		}
		y.h = x;
		// Resultado esperado: {(x,java.lang.Integer_75),(x,java.lang.Integer_77),(y,dc.aap.SomeClass_82),(y,dc.aap.SomeClass_84),(dc.aap.SomeClass_82,h,java.lang.Integer_75), (dc.aap.SomeClass_82,h,java.lang.Integer_77), (dc.aap.SomeClass_84,h,java.lang.Integer_75), (dc.aap.SomeClass_84,h,java.lang.Integer_77)}.

	}
	
	public static void loopMethod() {
		int i=0;
		Object a = new SomeClass();
		while(i<10) {
			a = new Integer(1);
			i++;
		}
	} // Resultado esperado: {(a,dc.app.SomeClass_93),(a,java.lang.Integer_95)}

	public static void weakUpdate() {
		
		SomeClass a = new SomeClass();
		Integer b = new Integer(1);
		a.h = new Integer(2);
		SomeClass c = new SomeClass();
		Integer d = new Integer(3);
		c.h = d;
		a = c;
		Integer e = a.h;
		
	} // Resultado esperado: {(dc.aap.SomeClass_105,h,java.lang.Integer_106), (b,java.lang.Integer_103), (dc.aap.SomeClass_102,h,java.lang.Integer_104), (c,dc.aap.SomeClass_105), (d,java.lang.Integer_106), (a,dc.aap.SomeClass_105), (e,java.lang.Integer_106)} 

    public static void doubleNew() {
        SomeClass a = new SomeClass();
        a = new SomeClass();
    }

    public static void doubleRead() {
        SomeClass a = new SomeClass();
        a.h = new Integer(0);
        SomeClass b = new SomeClass();
        b.h = new Integer(1);
        Integer x = a.h;
        x = b.h;
    }

    public void parameter(boolean b,Integer i) {
        i = new Integer(10);
    }

    public void parameterRead(SomeClass a) {
        Integer i = a.h;
    }

    public void parameterLoad(SomeClass a) {
        Integer i = new Integer(0);
        a.h = i;
    }

    public void parameterRef(SomeClass a) {
        a = new SomeClass();
    }

    public void virtualInvoke(){
        SomeClass a = new SomeClass();
        Integer i = new Integer(7);
        a.parameter(true,i); //L:{(a,dc.aap.SomeClass_145),(i,java.lang.Integer_128)}E:{}
    }

    public void virtualInvokeAssign(){
        SomeClass a = new SomeClass();
        Integer i = a.integerCall();
    } // L:{(a,dc.aap.SomeClass_151), (i,java.lang.Integer_166)}E:{}

    public void virtualInvokeDestroy(){
        SomeClass a = new SomeClass();
        SomeClass b = new SomeClass();
        a.parameterRef(b); //(a, SomeClass_156) (b, SomeClass_141)
    }

    public void thisVirtualInvoke(){
        Integer i = new Integer(10);
        this.parameter(false,i);
    }

    public Integer integerCall() {
        return new Integer(10);
    }

    public void interfaceInvoke() {
        Pet p = new Cat();
        SomeClass a = new SomeClass();
        p.r(a); //L:{(p,dc.aap.Cat_171), (a,dc.aap.SomeClass_9)}E:{}
    }

    public void dualinterfaceInvoke(boolean b) {
        Pet p = new Duck();
        SomeClass a = new SomeClass();
        a.h = new Integer(0);
        if (b) {
            p = new Dog();
        }
        p.r(a);
    }

    public void assign(SomeClass a, SomeClass b) {
        a = b;
    }

    public void virtualassign(){
        SomeClass a = new SomeClass();
        SomeClass b = new SomeClass();
        a.assign(a, b);
    }

    public void staticCall(){
        Integer i = Cat.staticCatCall();
    }

}
