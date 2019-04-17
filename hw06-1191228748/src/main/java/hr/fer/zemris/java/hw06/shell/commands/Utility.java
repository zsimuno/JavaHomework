/**
 * 
 */
package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Utility class we use to help us with parsing arguments of the commands.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Utility {

	/**
	 * Checks if there are no arguments.
	 * 
	 * @param arguments the arguments.
	 * @return {@code true} if there are no arguments, {@code false} otherwise.
	 */
	public static boolean hasNoArguments(String arguments) {
		return arguments.isBlank();
	}

	/**
	 * Turns the given string array in to an unmodifiable list.
	 * 
	 * @param array given array to convert.
	 * @return unmodifiable list of elements from the string array.
	 */
	public static List<String> turnToUnmodifiableList(String[] array) {
		return Collections.unmodifiableList(Arrays.asList(array));
	}

	/**
	 * Parses arguments that are file paths to array of arguments.
	 * 
	 * @param args the arguments that we parse to array.
	 * @return array of path arguments.
	 * @throws IllegalArgumentException if there was a parsing error.
	 */
	public static String[] parseMultipleArguments(String args) {
		ArrayList<String> arguments = new ArrayList<>();

		int argsLenght = args.length();

		for (int i = 0; i < argsLenght; i++) {

			char current = args.charAt(i);

			if (Character.isWhitespace(current)) {
				continue;

			} else if (current == '\"') { // Quoted path

				i++; // Skip quote

				StringBuilder argument = new StringBuilder();

				while (true) {
					argument.append(args.charAt(i));

					if (i >= argsLenght || args.charAt(i) == '\"') {
						break;
					}

					i++;
				}

				if (i >= argsLenght) { // Quoted path was not closed
					throw new IllegalArgumentException("Invalid quoted path!");
				}

				arguments.add(argument.toString());

			} else { // Not quoted path
				StringBuilder argument = new StringBuilder();

				while (true) {
					argument.append(args.charAt(i));

					if (i >= argsLenght || Character.isWhitespace(args.charAt(i))) {
						break;
					}

					i++;
				}

				arguments.add(argument.toString());
			}

		}

		return (String[]) arguments.toArray();
	}

	/**
	 * Parses arguments that are not paths. Result i string array of arguments.
	 * 
	 * @param argumentsString the arguments that we parse to array.
	 * @return array of non-path arguments.
	 */
	public static String[] parseNonPathArguments(String argumentsString) {
		return argumentsString.split("\\s+");
	}

}
