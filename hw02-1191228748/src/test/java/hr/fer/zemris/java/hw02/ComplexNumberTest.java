/**
 * 
 */
package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ComplexNumber} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
class ComplexNumberTest {

	@Test
	void testConstructor() {
		ComplexNumber z = new ComplexNumber(3, 5);
		assertEquals(3, z.getReal());
		assertEquals(5, z.getImaginary());

	}

	@Test
	void testFromReal() {
		ComplexNumber z = new ComplexNumber(3, 0);
		assertEquals(z, ComplexNumber.fromReal(3));

	}

	@Test
	void testFromImaginary() {
		ComplexNumber z = new ComplexNumber(0, 3);
		assertEquals(z, ComplexNumber.fromImaginary(3));

	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber z = new ComplexNumber(3, 5);
		assertEquals(z, ComplexNumber.fromMagnitudeAndAngle(z.getMagnitude(), z.getAngle()));
	}

	@Test
	void testParse1() {
		ComplexNumber z = new ComplexNumber(-2.71, -3.15);
		assertEquals(z, ComplexNumber.parse("-2.71-3.15i"));
	}

	@Test
	void testParse2() {
		ComplexNumber z = new ComplexNumber(3.51, 0);
		assertEquals(z, ComplexNumber.parse("3.51"));
	}

	@Test
	void testParse3() {
		ComplexNumber z = new ComplexNumber(0, -2.71);
		assertEquals(z, ComplexNumber.parse("-2.71i"));
	}

	@Test
	void testParseNotComplex1() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.parse("i351");
		});

	}

	@Test
	void testParseNotComplex2() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.parse("-+2.71");
		});

	}

	@Test
	void testParseNotComplex3() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.parse("+2.71-+3.15i");
		});

	}

	@Test
	void testGetReal() {
		ComplexNumber z = new ComplexNumber(3, 5);
		assertEquals(3, z.getReal());
	}

	@Test
	void testGetImaginary() {
		ComplexNumber z = new ComplexNumber(3, 5);
		assertEquals(5, z.getImaginary());
	}

	@Test
	void testGetMagnitude() {
		ComplexNumber z = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI);
		assertEquals(2, z.getMagnitude());
	}

	@Test
	void testGetAngle() {
		ComplexNumber z = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI);
		assertEquals(Math.PI, z.getAngle());
	}
	
	@Test
	void testGetAngle2() {
		ComplexNumber z = ComplexNumber.fromMagnitudeAndAngle(2, 3 * Math.PI/2);
		assertEquals(3 * Math.PI/2, z.getAngle());
	}

	@Test
	void testAdd() {
		ComplexNumber z1 = new ComplexNumber(10, 6);
		ComplexNumber z2 = new ComplexNumber(3, 5);

		assertEquals(new ComplexNumber(13, 11), z1.add(z2));
	}

	@Test
	void testSub() {
		ComplexNumber z1 = new ComplexNumber(10, 6);
		ComplexNumber z2 = new ComplexNumber(3, 5);

		assertEquals(new ComplexNumber(7, 1), z1.sub(z2));
	}

	@Test
	void testMul() {
		ComplexNumber z1 = new ComplexNumber(10, 6);
		ComplexNumber z2 = new ComplexNumber(3, 5);

		assertEquals(new ComplexNumber(0, 68), z1.mul(z2));
	}

	@Test
	void testDiv() {
		ComplexNumber z1 = new ComplexNumber(10, 6);
		ComplexNumber z2 = new ComplexNumber(3, 5);

		assertEquals(new ComplexNumber(30.0 / 17.0, -16.0 / 17.0), z1.div(z2));

	}

	@Test
	void testPower() {
		ComplexNumber z = new ComplexNumber(3, 5);

		assertEquals(new ComplexNumber(-16, 30), z.power(2));

	}

	@Test
	void testRoot() {
		ComplexNumber z = new ComplexNumber(0, 81);
		assertEquals(new ComplexNumber(2.77163859753386, 1.14805029709527), z.root(4)[0]);
	}

	@Test
	void testToString() {
		ComplexNumber z = new ComplexNumber(3, 5);

		assertEquals("3.0+5.0i", z.toString());

	}

	@Test
	void testEquals() {
		ComplexNumber z1 = new ComplexNumber(3, 5);
		ComplexNumber z2 = new ComplexNumber(3, 5);

		assertTrue(z1.equals(z2));

	}
	
	@Test
	void testEquals2() {
		ComplexNumber z1 = new ComplexNumber(2.0, 3.0);
		ComplexNumber z3 = new ComplexNumber(4.001, 4.0001);

		assertFalse(z1.equals(z3));
	}
}
