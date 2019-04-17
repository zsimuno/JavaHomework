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

				while (i < argsLenght && args.charAt(i) != '\"') {
					char currentChar = args.charAt(i);

					if (currentChar == '\\') {
						i++; // Skip the backslash
						
						// Quoted path was not closed
						if (i == argsLenght) {
							throw new IllegalArgumentException("Invalid quoted path!");
						}

						switch (args.charAt(i)) {
						case '\\':
							argument.append('\\');
							break;
						case '\"':
							argument.append('\"');
							break;

						default:
							argument.append("\\\\");
							i--; // Don't skip current unescaped character
							break;
						}
						
					} else {
						argument.append(currentChar);
					}

					i++;
				}

				if (i >= argsLenght) { // Quoted path was not closed
					throw new IllegalArgumentException("Invalid quoted path!");
				}
				

				i++; // Skip quote
				
				if(i < argsLenght && !Character.isWhitespace(args.charAt(i))) {
					throw new IllegalArgumentException("Invalid argument!");
				}

				arguments.add(argument.toString());

			} else { // Not quoted path or some argument
				StringBuilder argument = new StringBuilder();

				while (i < argsLenght && !Character.isWhitespace(args.charAt(i))) {
					argument.append(args.charAt(i));
					
					i++;
				}

				arguments.add(argument.toString());
			}

		}

		return arguments.toArray(new String[arguments.size()]);
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

	/**
	 * Converts a byte array in to a string array representation of a hexadecimal
	 * number. Turns every byte array member in one String array member in hex
	 * representation.
	 * 
	 * @param byteArray byte array of numbers
	 * @param length    index of last element in the byte array
	 * @return string array representation of the byte array
	 */
	public static String[] bytetohex(byte[] byteArray, int length) {
		if (byteArray.length == 0)
			return new String[0];

		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < length; i++) {
			int current = (byteArray[i] < 0) ? 256 + byteArray[i] : byteArray[i];

			int second = current % 16;
			current /= 16;

			result.add(toHex(current % 16) + toHex(second));

		}

		return result.toArray(new String[result.size()]);

	}

	/**
	 * Converts integer to hexadecimal string
	 * 
	 * @param number integer to be converted
	 * @return hexadecimal string
	 */
	private static String toHex(int number) {
		if (number > 9) {
			return Character.toString('A' + number - 10);
		}

		return Integer.toString(number);

	}

}
