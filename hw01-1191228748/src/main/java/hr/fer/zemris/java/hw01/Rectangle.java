/**
 * 
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that calculates circumference and area of a rectangle
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Rectangle {

	/**
	 * Method that starts the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2 && args.length != 0) {
			System.out.println("Neispravan broj argumenata komandne linije!");
			return;
		}

		double width, height;

		if (args.length == 0) {
			Scanner sc = new Scanner(System.in);

			width = readFromUser("širinu", sc);
			height = readFromUser("visinu", sc);

			sc.close();
		} else {
			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch (NumberFormatException ex) {
				System.out.println("Neispravan argument komandne linije! Samo brojeve treba upisivati!");
				return;
			}

			if (width <= 0 || height <= 0) {
				System.out.println("Neispravan argument komandne linije! Jedan broj je manji ili jednak nuli!");
				return;
			}

		}

		System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.", Double.toString(width),
				Double.toString(height), Double.toString(width * height), Double.toString(2 * (width + height)));

	}

	/**
	 * Method that reads user input from keyboard and validates it
	 * 
	 * @param value value that we are looking from user to input
	 * @param sc    scanner that reads the input
	 * @return number that was read from user
	 */
	public static double readFromUser(String value, Scanner sc) {
		double inputNumber = 0;

		while (true) {
			System.out.printf("Unesite %s > ", value);

			if (sc.hasNext()) {
				String input = sc.next();
				try {
					inputNumber = Double.parseDouble(input);
					if (inputNumber < 0) {
						System.out.println("Unijeli ste negativnu vrijednost.");
					} else if (inputNumber == 0) {
						System.out.println("Unijeli ste nulu.");
					} else {
						break;
					}

				} catch (NumberFormatException ex) {
					System.out.printf("'%s' se ne može protumačiti kao broj.%n", input);
				}
			}
		}

		return inputNumber;
	}

}
