package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexRootedPolynomialTest {

	@Test
	void testApply() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex(2, 0));

		assertEquals(Complex.ZERO, crp.apply(new Complex(2, 0)));
	}
	
	@Test
	void testApply2() {
		Complex tmp = new Complex(2, 0);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, tmp);
		
		assertEquals(Complex.IM.sub(tmp), crp.apply(Complex.IM));
	}

	@Test
	void testToComplexPolynom() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial actual = crp.toComplexPolynom();
		ComplexPolynomial expected = new ComplexPolynomial(
				new Complex[] { new Complex(-2, 0), Complex.ZERO, Complex.ZERO, Complex.ZERO, new Complex(2, 0) });
		assertEquals(expected, actual);

	}

	@Test
	void testIndexOfClosestRootFor() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);

		assertEquals(0, crp.indexOfClosestRootFor(new Complex(3, 0), 10));
	}

	@Test
	void testIndexOfClosestRootFor2() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);

		assertEquals(-1, crp.indexOfClosestRootFor(new Complex(5, 0), 1));
	}

	@Test
	void testIndexOfClosestRootFor3() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);

		assertEquals(1, crp.indexOfClosestRootFor(new Complex(1, 0), 10));
	}

}
