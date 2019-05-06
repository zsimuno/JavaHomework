package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Complex} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
class ComplexTest {

	@Test
	void testParse1() {
		Complex z = new Complex(-2.71, -3.15);
		assertEquals(z, Complex.parse("-2.71-i3.15"));
	}

	@Test
	void testParse2() {
		Complex z = new Complex(3.51, 0);
		assertEquals(z, Complex.parse("3.51"));
	}

	@Test
	void testParse3() {
		Complex z = new Complex(0, -2.71);
		assertEquals(z, Complex.parse("-i2.71"));
	}

	@Test
	void testParseNotComplex1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Complex.parse("351i");
		});

	}

	@Test
	void testParseNotComplex2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Complex.parse("-+2.71");
		});

	}

	@Test
	void testParseNotComplex3() {
		assertThrows(IllegalArgumentException.class, () -> {
			Complex.parse("+2.71-+3.15i");
		});

	}

	@Test
	void testAdd() {
		Complex z1 = new Complex(10, 6);
		Complex z2 = new Complex(3, 5);

		assertEquals(new Complex(13, 11), z1.add(z2));
	}

	@Test
	void testSub() {
		Complex z1 = new Complex(10, 6);
		Complex z2 = new Complex(3, 5);

		assertEquals(new Complex(7, 1), z1.sub(z2));
	}

	@Test
	void testMul() {
		Complex z1 = new Complex(10, 6);
		Complex z2 = new Complex(3, 5);

		assertEquals(new Complex(0, 68), z1.multiply(z2));
	}

	@Test
	void testDiv() {
		Complex z1 = new Complex(10, 6);
		Complex z2 = new Complex(3, 5);

		assertEquals(new Complex(30.0 / 17.0, -16.0 / 17.0), z1.divide(z2));

	}

	@Test
	void testPower() {
		Complex z = new Complex(3, 5);

		assertEquals(new Complex(-16, 30), z.power(2));

	}

	@Test
	void testRoot() {
		Complex z = new Complex(0, 81);
		assertEquals(new Complex(2.77163859753386, 1.14805029709527), z.root(4).get(0));
	}

	@Test
	public void parserTest() {
		String[] validInput = new String[] { "3.51", "-3.17", "-i2.71", "i", "1", "-2.71-i3.15", "i351", "-i317",
				"i3.51", "-i3.17", "i", "-i", "2.71", "2.71+i3.15", "i" };
		String[] invalidInput = new String[] { "351i", "-317i", "3.51i", "-3.17i", "-+2.71", "--2.71", "-2.71+-i3.15",
				"+2.71-+i3.15", "-+2.71", "3.15E-2" };

		Complex[] expected = new Complex[] { new Complex(3.51, 0), new Complex(-3.17, 0), new Complex(0, -2.71),
				new Complex(0, 1), new Complex(1, 0), new Complex(-2.71, -3.15), new Complex(0, 351),
				new Complex(0, -317), new Complex(0, 3.51), new Complex(0, -3.17), new Complex(0, 1),
				new Complex(0, -1), new Complex(2.71, 0), new Complex(2.71, 3.15), new Complex(0, 1) };

		for (int i = 0; i < expected.length; i++) {
			Complex actual = Complex.parse(validInput[i]);
			assertEquals(expected[i], actual);
		}

		for (String string : invalidInput) {
			assertThrows(IllegalArgumentException.class, () -> {
				Complex.parse(string);
			});
		}
	}

	@Test
	void testEquals() {
		Complex z1 = new Complex(3, 5);
		Complex z2 = new Complex(3, 5);

		assertTrue(z1.equals(z2));

	}

	@Test
	void testEquals2() {
		Complex z1 = new Complex(2.0, 3.0);
		Complex z3 = new Complex(4.001, 4.0001);

		assertFalse(z1.equals(z3));
	}

	@Test
	void testParseNotValid() {
		assertThrows(IllegalArgumentException.class, () -> {
			Complex.parse("+2.71+-i3.15");
		});

		assertThrows(IllegalArgumentException.class, () -> {
			Complex.parse("+2.71-+i3.15");
		});

		assertThrows(IllegalArgumentException.class, () -> {
			Complex.parse("++2.71+i3.15");
		});

		Complex actual = Complex.parse("-i");
		assertEquals(new Complex(0, -1), actual);
	}

}
