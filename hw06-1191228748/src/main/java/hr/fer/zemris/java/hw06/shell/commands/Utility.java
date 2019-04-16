/**
 * 
 */
package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
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
	
	
	// TODO paziti da mogu oba argumenta bit path sa navodnicima
	
	/**
	 * Checks if there is one argument.
	 * 
	 * @param arguments the arguments.
	 * @return {@code true} if there is one argument, {@code false} otherwise.
	 */
	public static boolean hasOneArgument(String arguments) {
		if(!arguments.contains("\"")) {
			return arguments.trim().contains(" ");
		}
		
		for (int i = 0; i < arguments.length(); i++) {
			
		}
		return arguments.trim().contains(" "); // TODO hasOneArgument 
	}
	
	/**
	 * Checks if there is two arguments.
	 * 
	 * @param arguments the arguments.
	 * @return {@code true} if there is two arguments, {@code false} otherwise.
	 */
	public static boolean hasTwoArguments(String arguments) {
		return arguments.split("\\s+").length == 2; // TODO hasTwoArguments
	}
	
	
	/**
	 * Parses a given file path in to one readable by {@link Path} object.
	 * 
	 * @param path file path.
	 * @return path readable by {@link Path} object.
	 */
	public static String parseFile(String path) {
		return path;
		// TODO parseFile
	}

	/**
	 * Parses a given directory path in to one readable by {@link Path} object.
	 * 
	 * @param path directory path.
	 * @return path readable by {@link Path} object.
	 */
	public static String parseDirectory(String path) {
		return path;
		// TODO parseDirectory
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
	
	

}
