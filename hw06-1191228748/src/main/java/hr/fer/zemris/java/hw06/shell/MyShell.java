/**
 * 
 */
package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Command-line program
 * 
 * @author Zvonimir Šimunović
 *
 */
public class MyShell {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {


		Scanner sc = new Scanner(System.in);

		Environment environment = new Environment() {
			/**
			 * Map that contains all the commands in the shell.
			 */
			private SortedMap<String, ShellCommand> commands;

			// Input all commands in the map
			{
				commands = new TreeMap<>();
				commands.put("cat", new LsShellCommand());
				commands.put("charsets", new CharsetsShellCommand());
				commands.put("copy", new CopyShellCommand());
				commands.put("exit", new ExitShellCommand());
				commands.put("help", new HelpShellCommand());
				commands.put("hexdump", new HexdumpShellCommand());
				commands.put("ls", new LsShellCommand());
				commands.put("mkdir", new MkdirShellCommand());
				commands.put("tree", new TreeShellCommand());
			}

			/**
			 * The prompt symbol of the shell.
			 */
			private Character promptSymbol = '>';
			/**
			 * The multiple lines symbol of the shell.
			 */
			private Character multiLinesSymbol = '|';
			/**
			 * The more lines symbol of the shell.
			 */
			private Character moreLinesSymbol = '\\';

			@Override
			public void writeln(String text) throws ShellIOException {
				// TODO kada exception ovdje i u upisu?
				System.out.println(text);

			}

			@Override
			public void write(String text) throws ShellIOException {
				System.out.printf(text);

			}

			@Override
			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;

			}

			@Override
			public void setMultilineSymbol(Character symbol) {
				multiLinesSymbol = symbol;

			}

			@Override
			public void setMorelinesSymbol(Character symbol) {
				moreLinesSymbol = symbol;

			}

			@Override
			public String readLine() throws ShellIOException {
				System.out.printf("%n%c ", promptSymbol);

				StringBuilder input = new StringBuilder();
				while (true) {

					// Input from user
					String nextLine = sc.nextLine().trim();

					// If more lines
					if (nextLine.endsWith(moreLinesSymbol.toString() + "\n")) {
						System.out.printf("%n%c ", multiLinesSymbol);

						// Deleting new line and multi lines symbol
						input.append(nextLine.substring(0, nextLine.length() - 2));

					} else {
						input.append(nextLine);
						break;
					}
				}
				return input.toString();
			}

			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}

			@Override
			public Character getMultilineSymbol() {
				return multiLinesSymbol;
			}

			@Override
			public Character getMorelinesSymbol() {
				return moreLinesSymbol;
			}

			@Override
			public SortedMap<String, ShellCommand> commands() {

				return Collections.unmodifiableSortedMap(commands);
			}
		};

		System.out.println("Welcome to MyShell v 1.0");

		SortedMap<String, ShellCommand> commands = environment.commands();

		ShellStatus status = ShellStatus.CONTINUE;
		do {
			String userInput;
			try {
				userInput = environment.readLine(); // TODO mozda ovo treba biti za jednu liniju samo 
				
			} catch (Exception e) {
				System.out.println("Error while reading user input!");
				break;
			}
			
			if(userInput.isBlank()) {
				System.out.println("A command must be inputted!");
				continue;
			}
			
			String commandName = extractCommand(userInput);
			String arguments = extractArguments(userInput);

			if(!commands.containsKey(commandName)) {
				System.out.println("No such command!");
				continue;
			}
			
			ShellCommand command = commands.get(commandName);
			status = command.executeCommand(environment, arguments);

		} while (status != ShellStatus.TERMINATE);

		sc.close();

	}

	/**
	 * Extracts the command from the given user input.
	 * 
	 * @param userInput user input
	 * @return the command from the input
	 */
	private static String extractCommand(String userInput) {
		
		String[] input = userInput.split("\\s+", 2);

		return input[0];
	}

	/**
	 * Extracts the arguments from the given user input.
	 * 
	 * @param userInput user input
	 * @return the arguments from the input
	 */
	private static String extractArguments(String userInput) {
		String[] input = userInput.split("\\s+", 2);
		
		// No arguments, only command
		if(input.length < 2) {
			return "";
		} 

		return input[0];
	}

}
