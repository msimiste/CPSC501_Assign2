package introspection;

import java.util.*;
import java.util.Map.Entry;
import java.lang.reflect.*;

public class Inspector {
	private Map<Integer, Class<?>> classObjects;
	private int idCounter = 0;

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

		int total = iFaces.length;
		int count = 1;
		System.out.println("This class implements " + total + " interfaces");
		for (Class<?> c : iFaces) {
			System.out.println("\tInterface  " + (count++) + " " + c.getName());
		}

	}

}
