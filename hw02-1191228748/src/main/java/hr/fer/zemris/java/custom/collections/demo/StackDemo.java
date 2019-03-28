/**
 * 
 */
package hr.fer.zemris.java.custom.collections.demo;

import java.util.Scanner;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that is used to demo the {@link ObjectStack} class. Accepts a single
 * command-line argument: expression which should be evaluated. Expression must
 * be in postfix representation. When starting program from console, enclose
 * whole expression into quotation marks, so that your program always gets just
 * one argument
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StackDemo {

	/**
	 * Method that starts the program and evaluates the postfix expression given
	 * trought a command line argument
	 * 
	 * @param args command line arguments. In this case it must be one and it must
	 *             be an expression in postfix representation.
	 * 
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Wrong number of command line arguments!");
			return;
		}

		Scanner sc = new Scanner(args[0]);

		ObjectStack stack = new ObjectStack();
		while (sc.hasNext()) {
			if (sc.hasNextInt()) {
				stack.push(sc.nextInt());
			} else {
				String input = sc.next();

				if (input == "\"") {
					continue;
				}

				if (stack.size() < 2) {
					sc.close();
					System.out.println("Expression is not written correctly!");
					return;
				}

				int first = (int) stack.pop();
				int second = (int) stack.pop();

				// support only +, -, /, * and %
				switch (input) {
				case "+":
					stack.push(second + first);
					break;
				case "-":
					stack.push(second - first);
					break;
				case "/":
					if (first == 0) {
						sc.close();
						System.out.println("Division by zero is forbidden!");
						return;
					}
					stack.push(second / first);
					break;
				case "*":
					stack.push(second * first);
					break;
				case "%":
					if (first == 0) {
						sc.close();
						System.out.println("Division by zero is forbidden!");
						return;
					}
					stack.push(second % first);
					break;
				default: // Not any of the legal characters
					sc.close();
					System.out.println("Expression is not written correctly!");
					return;
				}

			}
		}

		sc.close();

		if (stack.size() != 1) {
			System.out.println("Expression is not written correctly!");
			return;
		} else {
			System.out.println("Expression evaluates to " + stack.pop()); // Print result
		}

	}

}
