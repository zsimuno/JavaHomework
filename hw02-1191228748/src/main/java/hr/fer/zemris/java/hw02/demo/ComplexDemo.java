/**
 * 
 */
package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class that is used to demo the {@link ComplexNumber} class.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ComplexDemo {

	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
		ComplexNumber a1 = new ComplexNumber(1, 0);
		System.out.println(a1.getAngle());
		ComplexNumber a2 = new ComplexNumber(0, 1);
		System.out.println(a2.getAngle());
		ComplexNumber a3 = new ComplexNumber(-1, 0);
		System.out.println(a3.getAngle());
		ComplexNumber a4 = new ComplexNumber(0, -1);
		System.out.println(a4.getAngle());

	}

}
