package introspection;

import java.util.*;
import java.lang.reflect.*;

public class Inspector {
	private Map<Integer, Class<?>> classObjects;

	private boolean recursive;
	public final String IDIVIDER = "-------------------------------------------";
	public final String ODIVIDER = "*******************************************";



	/**
	 * Method Initiates the inspection process
	 * 
	 * @param obj
	 *            the object to be inspected
	 * @param recursive
	 *            the boolean value which determines if recursive inspection is
	 *            to be performed or not
	 */
	public void inspect(Object obj, boolean recursive) {

		this.recursive = recursive;
		if (obj == null) {			
			return;
		}
		

		classObjects = new HashMap<Integer, Class<?>>();
		Class<?> cl = obj.getClass();

		// get all classes (non recursive)
		while (cl != null) {
			if (!(classObjects.containsKey(cl.hashCode()))) {
				classObjects.put(cl.hashCode(), cl);
			}
			cl = cl.getSuperclass();
		}

		for (Class<?> cls : classObjects.values()) {
			// get Class information
			getClassInfo(cls, obj);

		}
	}


	/**
	 * 
	 * @param cls
	 */
	private void getClassInfo(Class<?> cls, Object obj) {

		displayClassInterfaces(cls);
		displayFields(cls, obj);
		displayConstructors(cls);
		displayMethods(cls);

	}

	/**
	 * 
	 * @param cls
	 */
	private void displayMethods(Class<?> cls) {
		Method[] methods = cls.getDeclaredMethods();
		int methodTotal = methods.length;
		System.out.println(ODIVIDER);
		System.out.println(cls.getName() + " declares " + methodTotal + " Methods");
		int methodCount = 1;
		System.out.println(ODIVIDER);
		for (Method m : methods) {
			System.out.println("Method " + (methodCount++) + " is " + m.getName());
			System.out.println(IDIVIDER);
			System.out.println("\t" + m.getName() + " return type: " + m.getReturnType().getName());
			System.out.println("\t" + m.getName() + " throws: " + m.getExceptionTypes().length + " exceptions");
			listExceptions(m);
			System.out.println("\t" + m.getName() + " requires: " + m.getParameterCount() + " parameters");
			listMethodParameterTypes(m);
			System.out.print("\t" + m.getName() + " has the following modifiers: ");
			listMethodModifiers(m);
			System.out.println(IDIVIDER);
		}
	}

	/**
	 * 
	 * @param cls
	 */
	private void displayConstructors(Class<?> cls) {
		Constructor<?>[] constructors = cls.getDeclaredConstructors();
		int constructorTotal = constructors.length;
		int constructorCount = 1;
		System.out.println(cls.getName() + " has " + constructorTotal + " constructors ");
		System.out.println(ODIVIDER);
		for (Constructor<?> c : constructors) {
			System.out.println(IDIVIDER);
			System.out.println("Constructor " + (constructorCount++) + " is " + c.getName());
			System.out.println(IDIVIDER);
			System.out.println("\t" + c.getName() + " requires: " + c.getParameterCount() + " parameters");
			listConstructorParameterTypes(c);
			System.out.print("\t" + c.getName() + " has the following modifiers: ");
			listConstructorModifiers(c);
			System.out.println(IDIVIDER);
		}
	}

	/**
	 * 
	 * @param cls
	 */
	private void displayClassInterfaces(Class<?> cls) {
		System.out.println("Class Name: " + cls.getName());
		System.out.println("Class Simple Name: " + cls.getSimpleName());
		if (cls.getSuperclass() == null) {
			System.out.println("Immediate Superclass: null");
		} else {
			System.out.println("Immediate Superclass: " + cls.getSuperclass().getName());
		}

		Class<?>[] iFaces = cls.getInterfaces();
		int total = iFaces.length;
		int count = 1;
		System.out.println(ODIVIDER);
		System.out.println(cls.getName() + " implements " + total + " interfaces");
		System.out.println(ODIVIDER);
		System.out.println(IDIVIDER);
		for (Class<?> c : iFaces) {
			System.out.println("\tInterface  " + (count++) + " " + c.getName());
		}
		System.out.println(IDIVIDER);
		System.out.println(ODIVIDER);
	}

	/**
	 * 
	 * @param cls
	 */
	private void displayFields(Class<?> cls, Object obj) {
		Field[] fields = cls.getDeclaredFields();
		int fieldTotal = fields.length;
		int fieldCount = 1;
		System.out.println(ODIVIDER);
		System.out.println(cls.getName() + " declares " + fieldTotal + " Fields");
		System.out.println(IDIVIDER);
		for (Field f : fields) {

			f.setAccessible(true);
			System.out.println("Field " + (fieldCount));
			System.out.println("\tName: " + f.getName());

			try {
				Class<?> type = f.getType();
				if (type.isPrimitive()) {
					Object c = f.get(obj);
					System.out.println("\tType: " + c.getClass().getTypeName());
					System.out.println("\tValue: " + c.toString());
					System.out.println("\tModifiers: " + Modifier.toString(c.getClass().getModifiers()));
				} else if (type.isArray()) {
					int indexLength = Array.getLength(f.get(obj));
					Object[] arr = new Object[indexLength];
					if (!(f.getType().toString().contains("[L"))) {
						for (int i = 0; i < indexLength; i++) {
							arr[i] = Array.get(f.get(obj), i);
						}
					}
					System.out.println("\tType: " + type.getComponentType());
					System.out.println("\tLength: " + arr.length);
					for (int i = 0; i < arr.length; i++) {
						if (arr[i] == null) {
							System.out.println("\t\tValue: null");
						} else {
							System.out.println("\tValue: " + arr[i].toString());
						}
					}
					System.out.println("\tModifiers: " + Modifier.toString(arr.getClass().getModifiers()));
				} else {
					Object c = f.get(obj);
					if (recursive) {
						inspect(c, recursive);
					}
					System.out.print("\tType: " + type);
					if (c != null) {
						System.out.println("\tValue: " + c.toString());
					} else {
						System.out.println("\tValue: null");
					}
				}
				fieldCount++;
				System.out.println(IDIVIDER);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @param c
	 */
	private void listConstructorModifiers(Constructor<?> c) {
		System.out.println(Modifier.toString(c.getModifiers()));

	}

	/**
	 * 
	 * @param c
	 */
	private void listConstructorParameterTypes(Constructor<?> c) {
		Class<?>[] parameters = c.getParameterTypes();
		Parameter[] params = c.getParameters();
		int count = 1;
		for (Class<?> p : parameters) {
			System.out.println("\t\t" + params[(count++) - 1].getName() + "\t" + p.getName());
		}
	}

	/**
	 * 
	 * @param m
	 */
	private void listExceptions(Method m) {
		Class<?>[] exceptions = m.getExceptionTypes();
		for (Class<?> e : exceptions) {
			System.out.println("\t\t" + e.getName());
		}
	}

	/**
	 * 
	 * @param m
	 */
	private void listMethodParameterTypes(Method m) {
		Class<?>[] parameters = m.getParameterTypes();
		Parameter[] params = m.getParameters();
		int count = 1;
		for (Class<?> p : parameters) {
			System.out.println("\t\t" + params[(count++) - 1].getName() + "\t" + p.getName());
		}
	}

	/**
	 * 
	 * @param m
	 */
	private void listMethodModifiers(Method m) {
		System.out.println(Modifier.toString(m.getModifiers()));

	}

}
