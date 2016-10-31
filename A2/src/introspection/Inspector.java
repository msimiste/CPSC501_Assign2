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
		if(!(classObjects.containsKey(obj.hashCode()))){
			classObjects.put(obj.hashCode(), obj.getClass());
		}
		

		for (Class<?> cls : classObjects.values()) {
			// get Class information
			getClassInfo(cls);

		}
	}

	private void getClassInfo(Class<?> cls) {
		System.out.println("Class Name: " + cls.getName());
		System.out.println("Class Simple Name: " + cls.getSimpleName());
		System.out.println("Immediate Superclass: "
				+ cls.getSuperclass().getName());
		Class<?>[] iFaces = cls.getInterfaces();
		int total = iFaces.length;
		int count = 1;
		System.out.println(ODIVIDER);
		System.out.println(cls.getName() + " implements " + total
				+ " interfaces");
		System.out.println(ODIVIDER);
		System.out.println(IDIVIDER);
		for (Class<?> c : iFaces) {
			System.out.println("\tInterface  " + (count++) + " " + c.getName());
		}
		System.out.println(IDIVIDER);
		System.out.println(ODIVIDER);

		Field[] fields = cls.getDeclaredFields();
		int fieldTotal = fields.length;
		int fieldCount = 1;
		System.out.println(ODIVIDER);
		System.out.println(cls.getName() + " declares " + fieldTotal
				+ " Fields");
		System.out.println(IDIVIDER);
		for (Field f : fields) {

			f.setAccessible(true);
			System.out.println("Field " + (fieldCount));
			System.out.println("\tName: " + f.getName());
			

			try {
				Object c = f.get(cls.newInstance());
				Class<?> type = f.getType();
				if (type.isPrimitive()) {

					
					System.out.println("\tType: " + c.getClass().getTypeName());
					System.out.println("\tValue: " + c.toString());
					System.out.println("\tModifiers: "
							+ Modifier.toString(c.getClass().getModifiers()));
				} else if (type.isArray()) {
					System.out.println("\tValue: an arrray ");
				} else {
					System.out.print("\tType: " + f.getType());
					if(c != null){
						System.out.println("\tValue: " + c.toString());
					}
					else{
						System.out.println("\tValue: null");
					}
					//System.out
						//	.println("\tValue is not primitive or an array: ");
				}

				fieldCount++;
				System.out.println(IDIVIDER);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Constructor[] constructors = cls.getDeclaredConstructors();
		int constructorTotal = constructors.length;
		int constructorCount = 1;
		System.out.println(cls.getName() + " has " + constructorTotal
				+ " constructors ");
		System.out.println(ODIVIDER);
		for (Constructor c : constructors) {
			System.out.println(IDIVIDER);
			System.out.println("Constructor " + (constructorCount++) + " is "
					+ c.getName());
			System.out.println(IDIVIDER);
			System.out.println("\t" + c.getName() + " requires: "
					+ c.getParameterCount() + " parameters");
			listConstructorParameterTypes(c);
			System.out.print("\t" + c.getName()
					+ " has the following modifiers: ");
			listConstructorModifiers(c);
			System.out.println(IDIVIDER);
		}

		Method[] methods = cls.getDeclaredMethods();
		int methodTotal = methods.length;
		System.out.println(ODIVIDER);
		System.out.println(cls.getName() + " declares " + methodTotal
				+ " Methods");
		int methodCount = 1;
		System.out.println(ODIVIDER);
		for (Method m : methods) {
			System.out.println("Method " + (methodCount++) + " is "
					+ m.getName());
			System.out.println(IDIVIDER);
			System.out.println("\t" + m.getName() + " return type: "
					+ m.getReturnType().getName());
			System.out.println("\t" + m.getName() + " throws: "
					+ m.getExceptionTypes().length + " exceptions");
			listExceptions(m);
			System.out.println("\t" + m.getName() + " requires: "
					+ m.getParameterCount() + " parameters");
			listMethodParameterTypes(m);
			System.out.print("\t" + m.getName()
					+ " has the following modifiers: ");
			listMethodModifiers(m);
			System.out.println(IDIVIDER);
		}

	}

	private boolean isInheritable(Member member) {
		if (member == null) {
			return false;
		}
		int modifiers = member.getModifiers();
		if (Modifier.isPrivate(modifiers)) {
			return false;
		}
		return true;
	}

	private void listConstructorModifiers(Constructor c) {
		System.out.println(Modifier.toString(c.getModifiers()));

	}

	private void listConstructorParameterTypes(Constructor c) {
		Class<?>[] parameters = c.getParameterTypes();
		Parameter[] params = c.getParameters();
		int count = 1;
		for (Class<?> p : parameters) {
			System.out.println("\t\t" + params[(count++) - 1].getName() + "\t"
					+ p.getName());
		}
	}

	private void listExceptions(Method m) {
		Class<?>[] exceptions = m.getExceptionTypes();
		int count = 1;
		for (Class<?> e : exceptions) {
			System.out.println("\t\t" + e.getName());
		}
	}

	private void listMethodParameterTypes(Method m) {
		Class<?>[] parameters = m.getParameterTypes();
		Parameter[] params = m.getParameters();
		int count = 1;
		for (Class<?> p : parameters) {
			System.out.println("\t\t" + params[(count++) - 1].getName() + "\t"
					+ p.getName());
		}
	}

	private void listMethodModifiers(Method m) {
		System.out.println(Modifier.toString(m.getModifiers()));

	}

}
