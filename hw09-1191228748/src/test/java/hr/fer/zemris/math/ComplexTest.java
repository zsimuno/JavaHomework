package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexTest {

	@Test
	void testAddition() {
		Complex c1 = new Complex(15, 5);
		Complex c2 = new Complex(5, 1);
		assertEquals(new Complex(20, 6), c1.add(c2));
	}

	@Test
	void testSubctraction() {
		Complex c1 = new Complex(15, 5);
		Complex c2 = new Complex(5, 1);
		assertEquals(new Complex(10, 4), c1.sub(c2));
	}

	@Test
	void testMultiplication() {
		Complex c1 = new Complex(15, 5);
		Complex c2 = new Complex(5, 1);
		assertEquals(new Complex(70, 40), c1.multiply(c2));
	}

	@Test
	void testDivision() {
		Complex c1 = new Complex(15, 5);
		Complex c2 = new Complex(5, 1);
		assertEquals(new Complex(40.0 / 13.0, 5.0 / 13.0), c1.divide(c2));

	}

	@Test
	void testPower() {
		assertEquals(new Complex(200, 150), new Complex(15, 5).power(2));

	}

	@Test
	void testRoots() {
		assertEquals(new Complex(2.77163859753386, 1.14805029709527), new Complex(0, 81).root(4).get(0));
	}

	@Test
	public void testParseValidInputs() {
		String[] actualInputs = new String[] { "3.51", "-3.17", "-i2.71", "i", "1", "-2.71-i3.15", "i351", "-i317",
				"i3.51", "-i3.17", "i", "-i", "2.71", "2.71+i3.15", "i", "0", "i0", "0+i0", "0-i0" };

		Complex[] expectedOutputs = new Complex[] { new Complex(3.51, 0), new Complex(-3.17, 0), new Complex(0, -2.71),
				Complex.IM, Complex.ONE, new Complex(-2.71, -3.15), new Complex(0, 351), new Complex(0, -317),
				new Complex(0, 3.51), new Complex(0, -3.17), Complex.IM, Complex.IM_NEG, new Complex(2.71, 0),
				new Complex(2.71, 3.15), Complex.IM, Complex.ZERO, Complex.ZERO, Complex.ZERO, Complex.ZERO };

		for (int i = 0; i < expectedOutputs.length; i++) {
			Complex actual = Complex.parse(actualInputs[i]);
			Complex expected = expectedOutputs[i];
			assertEquals(expected, actual);
		}
	}

	@Test
	public void testParseInvalidInputs() {
		String[] inputs = new String[] { "351i", "-317i", "3.51i", "-3.17i", "-+2.71", "--2.71", "-2.71+-i3.15",
				"+2.71-+i3.15", "-+2.71", "3.15E-2" };

		for (String input : inputs) {
			assertThrows(IllegalArgumentException.class, () -> {
				Complex.parse(input);
			});
		}
	}

	@Test
	void testEquals() {
		Complex c1 = new Complex(15, 15);
		Complex c2 = new Complex(15, 15);

		assertTrue(c1.equals(c2));

	}

	@Test
	void testEquals2() {
		Complex c1 = new Complex(15.0002, 15);
		Complex c2 = new Complex(15.0002, 15);

		assertTrue(c1.equals(c2));

	}

	@Test
	void testEquals3() {
		Complex c1 = new Complex(1, 1);
		Complex c2 = new Complex(5, 5);

		assertFalse(c1.equals(c2));
	}

	@Test
	void testNegate() {
		assertEquals(new Complex(-1, 2), new Complex(1, -2).negate());
	}

}
