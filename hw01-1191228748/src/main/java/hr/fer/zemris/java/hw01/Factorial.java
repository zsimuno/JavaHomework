/**
 * 
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that calculates a factorial of a number based on user input
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Factorial {

	/**
	 * Method that starts the program and reads the user input and validates it.
	 * Input needs to be number between 3 and 20.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("Unesite broj > ");
			if (sc.hasNextInt()) {
				int inputNumber = sc.nextInt();
				if (inputNumber < 3 || inputNumber > 20) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", inputNumber);
					continue;
				}
				System.out.printf("%d! = %d%n", inputNumber, factorial(inputNumber));

			} else {
				String input = sc.next();
				if (input.equals("kraj")) {
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", input);
			}
		}

		sc.close();

	}

	/**
	 * Method that calculates factorial of a number where factorial n! is equal to
	 * n*(n-1)*...*2*1
	 * 
	 * @param inputNumber number that we calculate factorial of
	 * @return inputNumber!, that is calculated factorial of inputNumber
	 * @throws IllegalArgumentException if argument is a negative number
	 * 
	 */
	public static int factorial(int inputNumber) {
		if (inputNumber < 0) {
			throw new IllegalArgumentException("Broj je manji od nula");
		}

		int factorial = 1;

		while (inputNumber > 0) {
			factorial *= inputNumber;
			inputNumber--;
		}

		return factorial;
	}

}
