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
		
		for(Class<?> cls : classObjects.values()){
			getClassInfo(cls);
		}
	}

	private void getClassInfo(Class<?> cls) {
		
		System.out.println("Class: " + cls.getName());
		System.out.println("Superclass: " + cls.getSuperclass().getName());
		
	}
}
