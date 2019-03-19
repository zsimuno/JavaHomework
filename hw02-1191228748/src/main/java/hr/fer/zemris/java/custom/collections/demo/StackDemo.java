/**
 * 
 */
package hr.fer.zemris.java.custom.collections.demo;

import java.util.Scanner;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * 
 * Accepts a single command-line argument: expression which should be evaluated.
 * Expression must be in postfix representation. When starting program from
 * console, enclose whole expression into quotation marks, so that your program
 * always gets just one argument
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StackDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			// Maybe print something for user?
			// TODO Just syso and terminate or?
			System.out.println("Wrong number of command line arguments!");
			return;
		}

		// TODO Validate the expression (eg. when there's an operation but only one number on the stack)
		// TODO Is using a scanner a good method for this?
		Scanner sc = new Scanner(args[0]);

		ObjectStack stack = new ObjectStack();
		while (sc.hasNext()) {
			if (sc.hasNextInt()) {
				stack.push(sc.nextInt());
			} else {
				String input = sc.next();
				
				if(input == "\"") {
					continue;
				}
				
				if(stack.size() < 2) {
					// TODO Just syso and terminate or?
					System.out.println("ERROR! Expression is not written correctly");
					sc.close();
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
					if(first == 0) {
						System.out.println("ERROR! Division by zero is forbidden!");
						sc.close();
						return;
						// TODO Just syso and terminate or?
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
		
		if(stack.size() != 1) {
			// TODO Maybe exception?
			System.out.println("ERROR!");
		} else {
			System.out.println("Result is: " + stack.pop()); // Print result
		}

	}

}
