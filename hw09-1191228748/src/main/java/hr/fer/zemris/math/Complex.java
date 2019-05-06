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
	/**
	 * Real part of a complex number
	 */
	private final double real;
	/**
	 * Imaginary part of a complex number
	 */
	private final double imaginary;

	/**
	 * Precision of the equals method
	 */
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
	 * Constructs a new {@code Complex} object from the given {@code real} and
	 * {@code imaginary} values
	 * 
	 * @param real      real part of a complex number
	 * @param imaginary imaginary part of a complex number
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
	 * Creates a new {@link Complex} that is {@code this} + {@code c}.
	 * 
	 * @param c complex number that is to be added to {@code this}
	 * @return new {@link Complex} that is {@code this} + {@code c}
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Creates a new {@link Complex} that is {@code this} - {@code c}.
	 * 
	 * @param c complex number that is to be subtracted from {@code this}
	 * @return new {@link Complex} that is {@code this} - {@code c}
	 * 
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Creates a new {@link Complex} that is {@code this} * {@code c}.
	 * 
	 * @param c complex number that is to be multiplied with {@code this}
	 * @return new {@link Complex} that is {@code this} * {@code c}
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Creates a new {@link Complex} that is {@code this} / {@code c}.
	 * 
	 * @param c complex number that is the divisor, where {@code this} is the
	 *          dividend
	 * @return new {@link Complex} that is {@code this} / {@code c}
	 */
	public Complex divide(Complex c) {
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		return new Complex((this.real * c.real + this.imaginary * c.imaginary) / denominator,
				(this.imaginary * c.real - this.real * c.imaginary) / denominator);
	}

	/**
	 * Creates a new {@link Complex} that is {@code this} negated, i.e.
	 * -{@code this}.
	 * 
	 * 
	 * @return new {@link Complex} that is {@code this} negated, i.e. -{@code this}.
	 */
	public Complex negate() {
		return (new Complex((-1) * this.real, (-1) * this.imaginary));
	}

	/**
	 * Creates a new {@link Complex} that is {@code this} to the power {@code n}.
	 * 
	 * @param n the exponent (n>=0)
	 * @return new {@link Complex} that is {@code this} to the power {@code n}
	 * @throws IllegalArgumentException if {@code n} is not a valid input
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		if (n == 0) {
			return new Complex(1, 0);
		}

		if (n == 1) {
			return new Complex(this.real, this.imaginary);
		}
		double rToN = Math.pow(module(), n);
		double angle = this.getAngle();
		return new Complex(rToN * Math.cos(angle * n), rToN * Math.sin(angle * n));

	}

	/**
	 * Returns the nth root of the complex number {@code this}.
	 * 
	 * @param n the root number (n > 0)
	 * @return the nth root of the complex number {@code this} as a list.
	 * @throws IllegalArgumentException if {@code n} is not a valid input
	 */
	public List<Complex> root(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		List<Complex> result = new ArrayList<Complex>();

		double angle = this.getAngle();
		double nRootR = Math.pow(this.module(), 1.0 / n);
		for (int i = 0; i < n; i++) {
			result.add(new Complex(nRootR * Math.cos((angle + 2 * i * Math.PI) / n),
					nRootR * Math.sin((angle + 2 * i * Math.PI) / n)));
		}
		return result;
	}

	/**
	 * Returns the angle in radians
	 * 
	 * @return angle in radians
	 */
	private double getAngle() {
		double angle = Math.atan2(imaginary, real);
		if (angle < 0) {
			return angle + 2 * Math.PI;
		}

		return angle;
	}

	/**
	 * Method that parses a string representation of a complex number to a
	 * {@link ComplexNumber} object. Accepts strings such as "3.51", "-3.17",
	 * "-2.71i", "i", "1","-2.71-3.15i"
	 * 
	 * @param s string representation of a complex number
	 * @return {@link ComplexNumber} object parsed from the input string {@code s}
	 * @throws IllegalArgumentException if {@code s} is not a valid complex number
	 */
	public static Complex parse(String s) {
		if (s == null || s.isBlank())
			throw new IllegalArgumentException("No input!");

		s = s.trim();

		Double real = null, imaginary = null;
		String number = "";
		int stringLen = s.length();
		for (int i = 0; i < stringLen; i++) {
			char currentChar = s.charAt(i);

			switch (currentChar) {

			case '-':
				if (!number.isBlank()) { // If it's not blank then a number has been inputed
					real = parseNumber(number);
				}
				if(number.isBlank() && real != null) 
					throw new IllegalArgumentException(s + " is not a complex number!");
				
				number = "-";
				break;

			case '+':
				// '+' can be only after the real part of the number
				if (number.isBlank())
					throw new IllegalArgumentException(s + " is not a complex number!");

				real = parseNumber(number);

				number = "";
				break;

			case 'i':
				if (!number.isBlank() && !number.equals("-"))
					throw new IllegalArgumentException(s + " is not a complex number!");

				if (real == null) { // real part was not inputed
					real = 0.0;
				}

				// If this is the end then imaginary part is zero
				if (i == stringLen - 1) {
					imaginary = (number.equals("-")) ? -1.0 : 1.0;
				}
				break;

			default:
				if (Character.isWhitespace(currentChar)) {
					continue;
				}
				// Can only be a digit or a point. If it's not then its an invalid character
				if (Character.isDigit(currentChar) || currentChar == '.') {
					number += currentChar;
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
	 * Method that validates that string is a double and returns it
	 * 
	 * @param number number to be parsed and turned to string
	 * @return double representation of a number
	 * @throws IllegalArgumentException if the string is not a valid representation
	 *                                  of an double
	 */
	private static double parseNumber(String number) {
		try {
			return Double.parseDouble(number);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(number + " is not a valid character of a complex number!");
		}
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Complex)) {
			return false;
		}

		Complex other = (Complex) obj;
		if (Math.abs(imaginary - other.imaginary) > precision) {
			return false;
		}
		if (Math.abs(real - other.real) > precision) {
			return false;
		}
		return true;
	}

}
