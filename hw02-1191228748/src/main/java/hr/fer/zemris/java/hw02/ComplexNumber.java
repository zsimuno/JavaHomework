/**
 * 
 */
package hr.fer.zemris.java.hw02;

/**
 * Class that represents a complex number and offers methods that use complex
 * numbers.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ComplexNumber {
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
	private static final double precision = 10e-8;

	/**
	 * Constructs a new {@code ComplexNumber} from the given {@code real} and
	 * {@code imaginary} values
	 * 
	 * @param real      real part of a complex number
	 * @param imaginary imaginary part of a complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns a complex number with just the real part that has the given value.
	 * 
	 * @param real real part of a complex number
	 * @return complex number with just the real part that has the given value
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns a complex number with just the imaginary part that has the given
	 * value.
	 * 
	 * @param imaginary imaginary part of a complex number
	 * @return complex number with just the imaginary part that has the given value
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns a ComplexNumber calculated from its magnitude and angle
	 * 
	 * @param magnitude magnitude of a complex number
	 * @param angle     angle of a complex number
	 * @return complex number calculated from its magnitude and angle
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
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
	public static ComplexNumber parse(String s) {
		if (s == "i") {
			return new ComplexNumber(0, 1);
		}
		Double real = 0.0, imaginary = 0.0;
		String number = "";

		for (int i = 0; i < s.length(); i++) {
			char nextChar = s.charAt(i);

			switch (nextChar) {

			case '-':
				if (number.isBlank()) { // If it's not blank then a number has been inputed
					number += nextChar;
				} else {
					real = parseNumber(number);
					number = "-";
				}
				break;
			case '+':
				if (number.isBlank()) { // If it's not blank then a number has been inputed
					continue;
				} else {
					real = parseNumber(number);
					number = "";
				}
				break;

			case 'i':
				if (number.isBlank() || number == "+") { // If it's not blank then a number has been inputed
					imaginary = 1.0;
				} else {
					imaginary = (number == "-") ? -1.0 : parseNumber(number);
					number = "";
				}
				// This should be the end of the number so we check that
				if (i != s.length() - 1) {
					throw new IllegalArgumentException(s + " is not a complex number!");
				}
				break;

			default: // Can only be a digit or a point. If it's not then its an invalid character
				if (Character.isDigit(nextChar) || nextChar == '.') {
					number += nextChar;
					continue;
				} else {
					throw new IllegalArgumentException(s + " is not a complex number!");
				}
			}

		}

		// Number should only be not blank if there's no imaginary part
		if (!number.isBlank()) {
			real = parseNumber(number);
		}

		return new ComplexNumber(real, imaginary);

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

	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return real part of the complex number
	 */
	public double getReal() {
		return this.real;
	}

	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return imaginary part of the complex number
	 */
	public double getImaginary() {
		return this.imaginary;
	}

	/**
	 * Returns the magnitude of the complex number.
	 * 
	 * @return magnitude of the complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Returns the angle in radians
	 * 
	 * @return angle in radians
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Creates a new {@link ComplexNumber} that is {@code this} + {@code c}.
	 * 
	 * @param c complex number that is to be added to {@code this}
	 * @return new {@link ComplexNumber} that is {@code this} + {@code c}
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * Creates a new {@link ComplexNumber} that is {@code this} - {@code c}.
	 * 
	 * @param c complex number that is to be subtracted from {@code this}
	 * @return new {@link ComplexNumber} that is {@code this} - {@code c}
	 * 
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * Creates a new {@link ComplexNumber} that is {@code this} * {@code c}.
	 * 
	 * @param c complex number that is to be multiplied with {@code this}
	 * @return new {@link ComplexNumber} that is {@code this} * {@code c}
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * Creates a new {@link ComplexNumber} that is {@code this} / {@code c}.
	 * 
	 * @param c complex number that is the divisor, where {@code this} is the
	 *          dividend
	 * @return new {@link ComplexNumber} that is {@code this} / {@code c}
	 */
	public ComplexNumber div(ComplexNumber c) {
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		return new ComplexNumber((this.real * c.real + this.imaginary * c.imaginary) / denominator,
				(this.imaginary * c.real - this.real * c.imaginary) / denominator);
	}

	/**
	 * Creates a new {@link ComplexNumber} that is {@code this} to the power
	 * {@code n}.
	 * 
	 * @param n the exponent (n>=0)
	 * @return new {@link ComplexNumber} that is {@code this} to the power {@code n}
	 * @throws IllegalArgumentException if {@code n} is not a valid input
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		if (n == 0) {
			return new ComplexNumber(1, 0);
		}

		if (n == 1) {
			return new ComplexNumber(this.real, this.imaginary);
		}
		double rToN = Math.pow(getMagnitude(), n);
		double angle = this.getAngle();
		return new ComplexNumber(rToN * Math.cos(angle * n), rToN * Math.sin(angle * n));

	}

	/**
	 * Returns the nth root of the complex number {@code this}.
	 * 
	 * @param n the root number (n > 0)
	 * @return the nth root of the complex number {@code this}
	 * @throws IllegalArgumentException if {@code n} is not a valid input
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		double angle = this.getAngle();
		double nRootR = Math.pow(this.getMagnitude(), 1.0 / n);
		ComplexNumber[] result = new ComplexNumber[n];
		for (int i = 0; i < result.length; i++) {
			result[i] = new ComplexNumber(nRootR * Math.cos((angle + 2 * i * Math.PI) / n),
										  nRootR * Math.sin((angle + 2 * i * Math.PI) / n));
		}
		return result;
	}

	/**
	 * Returns a string representation of this {@code ComplexNumber} object.
	 * 
	 * @return a {@code String} representation of this object.
	 */
	public String toString() {
		String re = Double.toString(this.real);
		String im = Double.toString(this.imaginary) + "i";

		if (this.imaginary == 0) {
			return re;
		}

		if (this.real == 0) {
			return im;
		}

		if (this.imaginary > 0) {
			return re + "+" + im;
		}

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
		if (!(obj instanceof ComplexNumber)) {
			return false;
		}
		ComplexNumber other = (ComplexNumber) obj;
		if (imaginary - other.imaginary > precision) {
			return false;
		}
		if (real - other.real > precision) {
			return false;
		}
		return true;
	}

}
