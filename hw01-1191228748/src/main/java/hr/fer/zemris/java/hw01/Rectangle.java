/**
 * 
 */
package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author Zvonimir Šimunović
 *
 */
public class Rectangle {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 2 && args.length != 0) {
			System.out.println("Neispravan broj argumenata komandne linije!");
			return;
		}

		double width, height;
		Scanner sc = new Scanner(System.in);

		if (args.length == 0) {

			width = readFromUser("širinu", sc);
			height = readFromUser("visinu", sc);

		} else {
			width = readCommandLineArgument(args[0], "širinu", sc);
			height = readCommandLineArgument(args[1], "visinu", sc);
		}

		System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.", Double.toString(width),
				Double.toString(height), Double.toString(width * height), Double.toString(2 * (width + height)));

		sc.close();
	}

	/**
	 * @param value
	 * @param sc
	 * @return
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
						System.out.println("Unijeli ste nulu");
					} else {
						break;
					}

				} catch (NumberFormatException ex) {
					System.out.printf("'%s' se ne može protumačiti kao broj..%n", input);
				}
			}
		}

		return inputNumber;
	}

	/**
	 * @param argument
	 * @param value
	 * @param sc
	 * @return
	 */
	public static double readCommandLineArgument(String argument, String value, Scanner sc) {
		double inputNumber = 0;

		try {
			inputNumber = Double.parseDouble(argument);

			if (inputNumber < 0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				inputNumber = readFromUser(value, sc);
			} else if (inputNumber == 0) {
				System.out.println("Unijeli ste nulu");
				inputNumber = readFromUser(value, sc);
			}
		} catch (NumberFormatException ex) {
			inputNumber = readFromUser(value, sc);
		}

		return inputNumber;
	}

}
