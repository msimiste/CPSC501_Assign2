package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import introspection.Inspector;

public class RecursiveTests {

	@Test
	public void testRecursive() {
		Inspector insp = new Inspector();
		boolean rec = true;
		TestClassA tc = new TestClassA();

		insp.inspect(tc, rec);
	}

}
