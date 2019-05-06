package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Represents a polynomial (in a rooted form) that has complex numbers for
 * factors.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Roots of the polynomial.
	 */
	Complex[] roots;

	/**
	 * Constant of the polynomial.
	 */
	Complex constant;

	/**
	 * Constructs a new {@link ComplexRootedPolynomial} object with the given roots
	 * and constant.
	 * 
	 * @param constant Constant of the polynomial.
	 * @param roots    Roots of the polynomial.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = constant;
		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point {@code z}.
	 * 
	 * @param z given point that we compute in.
	 * @return polynomial value at the given point.
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (Complex complex : roots) {
			result.multiply(z.sub(complex));
		}

		return result;
	}

	/**
	 * Converts {@code this} representation to {@link ComplexPolynomial} type.
	 * 
	 * @return {@code this} as {@link ComplexPolynomial} object.
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length + 1];
		
		Arrays.fill(factors, Complex.ZERO);

		factors[factors.length - 1] = constant;

		for (int i = 0; i <= roots.length; i++) {
			Complex root = (i == 0) ? constant : roots[i - 1];
			for (int j = factors.length - i - 1; j < roots.length; j++) {
				factors[j] = factors[j].add(root.negate().multiply(factors[j + 1]));
			}
		}

		return new ComplexPolynomial(factors);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("(" + constant.toString() + ")");
		for (Complex complex : roots) {
			result.append("*(z-(" + complex.toString() + "))");
		}

		return result.toString();
	}

	/**
	 * Finds index of closest root for given complex number z that is within
	 * {@code treshold}.
	 * 
	 * @param z        given complex number we find the closest root to.
	 * @param treshold treshold for the distance between the given complex number
	 *                 and roots.
	 * @return index of closest root for given complex number z that is within
	 *         {@code treshold} or -1 if there is no such root.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int minIndex = -1;
		double minDistance = 0;

		for (int i = 0; i < roots.length; i++) {
			double dist = roots[i].sub(z).module();
			if (dist <= treshold && dist < minDistance) {
				minIndex = i;
				minDistance = dist;
			}
		}

		return minIndex;
	}
}
