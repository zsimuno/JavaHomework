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
	 * @throws IllegalArgumentException if there is wrong number of command line
	 *                                  arguments or if the one argument is not
	 *                                  a valid postfix expression
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			throw new IllegalArgumentException("Wrong number of command line arguments!");
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
					throw new IllegalArgumentException("Expression is not written correctly!");
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
						throw new IllegalArgumentException("Division by zero is forbidden!");
					}
					stack.push(second / first);
					break;
				case "*":
					stack.push(second * first);
					break;
				case "%":
					stack.push(second % first);
					break;
				}

			}
		}

		sc.close();

		if (stack.size() != 1) {
			throw new IllegalArgumentException("Expression is not written correctly!");
		} else {
			System.out.println("Expression evaluates to " + stack.pop()); // Print result
		}

	}

}
