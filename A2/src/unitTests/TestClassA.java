package unitTests;

import introspection.ClassA;

public class TestClassA {

	private char[] arr = new char[2];
	private TestClassB tcb = new TestClassB(55);

	public TestClassA() {
		this.arr[0] = 'k';
		this.arr[1] = 'l';
	}

}
