package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexPolynomialTest {

	@Test
	void testOrder() {
		ComplexPolynomial poly = new ComplexPolynomial(Complex.ZERO, Complex.ZERO, Complex.ZERO, new Complex(8, 0));

		assertEquals(3, poly.order());
	}

	@Test
	void testMultiply() {
		ComplexPolynomial poly1 = new ComplexPolynomial(new Complex[] { new Complex(5, 0), Complex.ONE });
		ComplexPolynomial poly2 = new ComplexPolynomial(new Complex[] { new Complex(-3, 0), Complex.ONE });
		ComplexPolynomial result = new ComplexPolynomial(
				new Complex[] { new Complex(-15, 0), new Complex(2, 0), Complex.ONE });

		assertEquals(result, poly1.multiply(poly2));
	}

	@Test
	void testDerive() {
		ComplexPolynomial actual = new ComplexPolynomial(new Complex(-2, 0), Complex.ZERO, Complex.ZERO, Complex.ZERO,
				new Complex(2, 0)).derive();

		ComplexPolynomial expected = new ComplexPolynomial(Complex.ZERO, Complex.ZERO, Complex.ZERO, new Complex(8, 0));

		assertEquals(expected, actual);
	}

	@Test
	void testDerive2() {
		ComplexPolynomial actual = new ComplexPolynomial(new Complex(1, 0), new Complex(5, 0), new Complex(2, 0),
				new Complex(7, 2)).derive();

		ComplexPolynomial expected = new ComplexPolynomial(new Complex(5, 0), new Complex(4, 0), new Complex(21, 6));
		assertEquals(expected, actual);
	}

	@Test
	void testApply() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex(2, 0));
		ComplexPolynomial cp = crp.toComplexPolynom();

		assertEquals(Complex.ZERO, cp.apply(new Complex(2, 0)));
	}

	@Test
	void testApply2() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE);

		assertEquals(Complex.ONE, cp.apply(Complex.IM));
	}

	@Test
	void testApply3() {
		ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE, Complex.ONE, Complex.ONE);

		assertEquals(Complex.ONE.add(Complex.ONE).add(Complex.ONE), cp.apply(Complex.ONE));
	}

}
