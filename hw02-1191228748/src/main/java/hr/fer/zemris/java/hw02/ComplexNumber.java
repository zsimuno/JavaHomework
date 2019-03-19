/**
 * 
 */
package hr.fer.zemris.java.hw02;

/**
 * @author Zvonimir Šimunović
 *
 */
public class ComplexNumber {
	/**
	 * Real part of a complex number
	 */
	private double real;
	/**
	 * Imaginary part of a complex number
	 */
	private double imaginary;

	/**
	 * @param real      real part of a complex number
	 * @param imaginary imaginary part of a complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * @param real
	 * @return
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * @param imaginary
	 * @return
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * @param magnitude
	 * @param angle
	 * @return
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {

	}

	// (accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1","-2.71-3.15i"),
	/**
	 * @param s
	 * @return
	 */
	public static ComplexNumber parse(String s) {

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
	 * @return
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Returns the angle in radians (from 0 to 2 Pi)
	 * 
	 * @return angle in radians, from 0 to 2 Pi
	 */
	public double getAngle() {

	}

	/**
	 * @param c
	 * @return
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}

	/**
	 * @param c
	 * @return
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}

	/**
	 * @param c
	 * @return
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
				this.real * c.imaginary + this.imaginary * c.real);
	}

	/**
	 * @param c
	 * @return
	 */
	public ComplexNumber div(ComplexNumber c) {
		// TODO Provjerit dijeljenje sa nulom
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		return new ComplexNumber((this.real * c.real + this.imaginary * c.imaginary) / denominator,
				 (this.imaginary * c.real - this.real * c.imaginary) / denominator);
	}

	// n>=0,
	/**
	 * @param n
	 * @return
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();
			// TODO Provjerit valja li
		}
		
		if(n == 0) {
			return new ComplexNumber(1, 0); // TODO ima li ovo smisla?
		}
		
		if (n == 1 ) {
			return this; // TODO Provjerit jel ovo ili duboku kopiju
		}
		
		return this.mul(this.power(n - 1)); // TODO mozda i da nije rekurzivna
		
	}

//n > 0,
	/**
	 * @param n
	 * @return
	 */
	public ComplexNumber[] root(int n) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ((this.real == 0) ? "" : Double.toString(this.real) + ((this.imaginary < 0) ? "" : "+"))
				+ Double.toString(this.imaginary) + "i";
	}

}
