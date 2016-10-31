package unitTests;

import static org.junit.Assert.*;
import introspection.Inspector;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OutputTests {

	// Set up the stream to capture the program's output to the console.
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}

	@Test
	public void testListConstructorModifiers() {

		class TestClass {
			public TestClass() {
			}
		}

		TestClass tc = new TestClass();

		Inspector insp = new Inspector();
		boolean rec = true;

		insp.inspect(tc, rec);
		String testString = "has the following modifiers: public";
		assertEquals(true, outContent.toString().contains(testString));
	}

	@Test
	public void testListConstructorParameterTypes() {
		class TestClass {
			public TestClass(int param1, String param2, double param3) {
			}
		}

		TestClass tc = new TestClass(5, "parameter2", 6.1);

		Inspector insp = new Inspector();
		boolean rec = true;

		insp.inspect(tc, rec);
		String one = "		arg0	unitTests.OutputTests";
		String two = "		arg1	in";
		String three = "		arg2	java.lang.String";
		String four = "		arg3	double";
		boolean testBool = outContent.toString().contains(one)
				&& outContent.toString().contains(two)
				&& outContent.toString().contains(three)
				&& outContent.toString().contains(four);
		assertEquals(true, testBool);
	}
}
