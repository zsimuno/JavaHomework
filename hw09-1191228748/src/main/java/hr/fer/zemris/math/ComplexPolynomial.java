package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Represents a polynomial that has complex numbers for factors.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ComplexPolynomial {

	/**
	 * Factors of the polynomial.
	 */
	Complex[] factors;

	/**
	 * Constructs a new {@link ComplexPolynomial} object with the given factors.
	 * 
	 * @param factors factors of the new polynomial.
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * returns the order of this polynomial. (eg. For (7+2i)z^3+2z^2+5z+1 returns 3)
	 * 
	 * @return order of this polynomial.
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Computes a new polynomial that is {@code this} * {@code p}.
	 * 
	 * @param p other polynomial to be multiplied with this one.
	 * @return new polynomial that is {@code this} * {@code p}.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		Arrays.fill(newFactors, Complex.ZERO);

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				newFactors[i + j] = newFactors[i + j]
								.add(this.factors[i]
								.multiply(p.factors[j]));
			}
		}

		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Computes first derivative of this polynomial.
	 * 
	 * @return first derivative of this polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.length - 1];
		for (int i = 0; i < factors.length - 1; i++) {
			newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}
		return new ComplexPolynomial(newFactors);

	}

	/**
	 * Computes polynomial value at given point {@code z}.
	 * 
	 * @param z given point that we compute in.
	 * @return polynomial value at the given point.
	 */
	public Complex apply(Complex z) {
		Complex result = factors[0];

		for (int i = 1; i < factors.length; i++) {
			result = result.add(factors[i].multiply(z.power(i)));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("(" + factors[0].toString() + ")");
		for (int i = 1; i < factors.length; i++) {
			result.insert(0, "(" + factors[i].toString() + ")*z^" + (i) + "+");
		}

		return result.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(factors);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ComplexPolynomial))
			return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		return Arrays.equals(factors, other.factors);
	}

}
