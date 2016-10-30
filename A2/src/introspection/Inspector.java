package introspection;

import java.util.*;
import java.util.Map.Entry;
import java.lang.reflect.*;

public class Inspector {
	private Map<Integer, Class<?>> classObjects;
	private int idCounter = 0;

	public final String IDIVIDER = "-------------------------------------------";
	public final String ODIVIDER = "*******************************************";

	public void inspect(Object obj, boolean recursive) {

		if (obj == null) {
			throw new IllegalArgumentException("Can't inspect a null object");
		}

		classObjects = new HashMap<Integer, Class<?>>();
		classObjects.put(idCounter++, obj.getClass());

		for (Class<?> cls : classObjects.values()) {
			getClassInfo(cls);
		}
	}

	private void getClassInfo(Class<?> cls) {
		System.out.println("Class: " + cls.getName());
		System.out.println("Class simple: " + cls.getSimpleName());
		System.out.println("Class caonical: " + cls.getCanonicalName());
		System.out.println("Immediate Superclass: "
				+ cls.getSuperclass().getName());
		Class<?>[] iFaces = cls.getInterfaces();
		System.out.println(IDIVIDER);
		int total = iFaces.length;
		int count = 1;
		System.out.println("This class implements " + total + " interfaces");
		for (Class<?> c : iFaces) {
			System.out.println("\tInterface  " + (count++) + " " + c.getName());
		}
		System.out.println(IDIVIDER);
		Method[] methods = cls.getMethods();
		int methodTotal = methods.length;
		System.out.println("This class declares " + methodTotal + " Methods");
		int methodCount = 1;
		for (Method m : methods) {
			System.out.println(ODIVIDER);
			System.out.println("Method " + (methodCount++) + " is "
					+ m.getName());
			System.out.println(ODIVIDER);
			System.out.println("\t"+m.getName()+ " returns an object of type " + m.getReturnType().getName());
			System.out.println(IDIVIDER);
			System.out.println("\t" + m.getName() + " throws "
					+ m.getExceptionTypes().length + " excptions");
			listExceptions(m);
			System.out.println("\t" + m.getName() + " requires "
					+ m.getParameterCount() + " parameters");
			listParameterTypes(m);
			System.out.print("\t" + m.getName() + " has the following modifiers: ");
			listModifiers(m);

		}
	}

	private void listExceptions(Method m) {		
		Class<?>[] exceptions = m.getExceptionTypes();
		int count = 1;
		for (Class<?> e : exceptions) {
			System.out.println("\t\t" + e.getName());
		}
		System.out.println(IDIVIDER);
	}

	private void listParameterTypes(Method m) {
		Class<?>[] parameters = m.getParameterTypes();
		Parameter[] params = m.getParameters();
		int count = 1;
		for (Class<?> p : parameters) {
			System.out.println("\t" + params[(count++) - 1].getName() + "\t"
					+ p.getName());
		}
		System.out.println(IDIVIDER);
	}
	
	private void listModifiers(Method m ){
		System.out.println(Modifier.toString(m.getModifiers()));
		System.out.println(IDIVIDER);
		
	}

}
