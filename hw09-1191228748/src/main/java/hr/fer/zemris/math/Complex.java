/**
 * 
 */
package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a complex number and offers methods that use complex
 * numbers.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Complex {
	/** Real part of a complex number. */
	private double real;
	/** Imaginary part of a complex number. */
	private double imaginary;

	/** Precision used in the equals method. */
	private static final double precision = 1e-6;

	/**
	 * Complex number with both real and imaginary part being zero.
	 */
	public static final Complex ZERO = new Complex(0, 0);
	/**
	 * Complex number with real part being one and imaginary part being zero.
	 */
	public static final Complex ONE = new Complex(1, 0);
	/**
	 * Complex number with real part being minus one and imaginary part being zero.
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/**
	 * Complex number with real part being zero and imaginary part being one.
	 */
	public static final Complex IM = new Complex(0, 1);
	/**
	 * Complex number with real part being zero and imaginary part being minus one.
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Constructs a new {@code Complex} object with real and imaginary parts being
	 * zero.
	 */
	public Complex() {
		this.real = 0;
		this.imaginary = 0;
	}

	/**
	 * Constructs a new {@code Complex} object from the given values.
	 * 
	 * @param real      real part of a complex number.
	 * @param imaginary imaginary part of a complex number.
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;

	}

	/**
	 * Calculates the module of this complex number.
	 * 
	 * @return module of this complex number.
	 */
	public double module() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Creates and returns a new {@link Complex} object that is {@code this * c}.
	 * 
	 * @param c complex number that will be multiplied with {@code this}.
	 * @return {@link Complex} object that is {@code this * c}.
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Creates and returns a new {@link Complex} object that is {@code this / c}.
	 * 
	 * @param c complex number that will be divided from {@code this}.
	 * @return {@link Complex} object that is {@code this / c}.
	 */
	public Complex divide(Complex c) {
		double d = c.real * c.real + c.imaginary * c.imaginary;
		return new Complex((this.real * c.real + this.imaginary * c.imaginary) / d,
				(this.imaginary * c.real - this.real * c.imaginary) / d);
	}

	/**
	 * Creates and returns a new {@link Complex} object object that is
	 * {@code this + c}.
	 * 
	 * @param c complex number that will be added to {@code this}.
	 * @return {@link Complex} object that is {@code this + c}
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Creates and returns a new {@link Complex} object that is {@code this - c}.
	 * 
	 * @param c complex number that will be subtracted from {@code this}.
	 * @return {@link Complex} object that is {@code this - c}.
	 * 
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Creates and returns a new {@link Complex} object that is {@code this}
	 * negated, i.e. {@code -this}.
	 * 
	 * @return {@link Complex} object that is {@code -this}.
	 */
	public Complex negate() {
		return (new Complex((-1) * this.real, (-1) * this.imaginary));
	}

	/**
	 * Creates and returns a new {@link Complex} object that is {@code this^n} (n is
	 * non-negative integer).
	 * 
	 * @param n power (n is non-negative integer).
	 * @return {@link Complex} object that is {@code this^n}.
	 * @throws IllegalArgumentException if the given power is invalid.
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Invalid power number " + n + "!");

		if (n == 0)
			return Complex.ONE;

		if (n == 1)
			return new Complex(this.real, this.imaginary);

		double modulePowN = Math.pow(module(), n);
		double angle = angle();
		return new Complex(modulePowN * Math.cos(angle * n), modulePowN * Math.sin(angle * n));

	}

	/**
	 * Returns the n-th root of {@code this} (n is non-negative integer).
	 * 
	 * @param n the root number (n is non-negative integer).
	 * @return the n-th root of {@code this} as a list of complex numbers.
	 * @throws IllegalArgumentException if given root number is invalid.
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("Invalid root number " + n + "!");

		List<Complex> rootList = new ArrayList<>();

		double angle = angle();
		double nthRootModule = Math.pow(this.module(), 1.0 / n);
		for (int i = 0; i < n; i++) {
			rootList.add(new Complex(nthRootModule * Math.cos((angle + 2 * i * Math.PI) / n),
					nthRootModule * Math.sin((angle + 2 * i * Math.PI) / n)));
		}
		return rootList;
	}

	/**
	 * Parses a given string in to a complex number in a form of a {@link Complex}
	 * object. Form of input must be a+ib or a-ib where zero values can be dropped.
	 * 
	 * @param s input string that represents a complex number.
	 * @return {@link Complex} object parsed from the given input.
	 * @throws IllegalArgumentException if input is invalid (not a complex number in
	 *                                  a valid form).
	 */
	public static Complex parse(String s) {
		if (s == null || s.isBlank())
			throw new IllegalArgumentException("Input not provided!");

		s = s.trim();

		Double real = null, imaginary = null;
		String number = "";
		int stringLength = s.length();
		for (int i = 0; i < stringLength; i++) {
			char current = s.charAt(i);

			switch (current) {
			case '-':
				// If it's not blank then a number has been inputed
				if (!number.isBlank()) {
					real = parseNumber(number);
				}

				if (number.isBlank() && real != null)
					throw new IllegalArgumentException(s + " is not a valid complex number!");

				number = "-";
				break;

			case '+':
				// '+' can be only after the real part of the number
				if (number.isBlank())
					throw new IllegalArgumentException(s + " is not a valid complex number!");

				real = parseNumber(number);

				number = "";
				break;

			case 'i':
				if (!number.isBlank() && !number.equals("-"))
					throw new IllegalArgumentException(s + " is not a valid complex number!");

				if (real == null) { // real part was not inputed
					real = 0.0;
				}

				// If this is the end then imaginary part is zero
				if (i == stringLength - 1) {
					imaginary = (number.equals("-")) ? -1.0 : 1.0;
				}
				break;

			default:
				// Skip whitespace.
				if (Character.isWhitespace(current)) {
					continue;
				}
				// Can only be a digit or a point. If it's not then its an invalid character
				if (Character.isDigit(current) || current == '.') {
					number += current;
					continue;

				} else {
					throw new IllegalArgumentException(s + " is not a complex number!");
				}
			}
		}

		// Did it end with a real or imaginary part?
		if (real == null) {
			real = parseNumber(number);
			imaginary = 0.0;
		} else if (imaginary == null) {
			imaginary = parseNumber(number);
		}

		return new Complex(real, imaginary);

	}

	/**
	 * Method parses the given string in to a double. Used to check validity of user
	 * input.
	 * 
	 * @param input user input that is supposed to be a number.
	 * @return number that is parsed from the given input.
	 * @throws IllegalArgumentException if the given input is not a number.
	 */
	private static double parseNumber(String input) {
		try {
			return Double.parseDouble(input);
		} catch (Exception e) {
			throw new IllegalArgumentException(input + " is not a valid number! " + e.getMessage());
		}
	}

	/**
	 * Computes and returns angle of {@code this}.
	 * 
	 * @return angle of {@code this}.
	 */
	private double angle() {
		return Math.atan2(imaginary, real);
	}

	@Override
	public String toString() {
		String re = Double.toString(this.real);
		String im = ((this.imaginary < 0) ? "-" : "+") + "i" + Double.toString(Math.abs(this.imaginary));

		return re + im;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Complex))
			return false;
		Complex other = (Complex) obj;
		return Math.abs(imaginary - other.imaginary) < precision && Math.abs(real - other.real) < precision;

	}

}
