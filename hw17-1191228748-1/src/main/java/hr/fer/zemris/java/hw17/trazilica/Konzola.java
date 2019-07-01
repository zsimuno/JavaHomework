package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hr.fer.zemris.java.hw17.trazilica.commands.ExitCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.ResultsCommand;
import hr.fer.zemris.java.hw17.trazilica.commands.TypeCommand;


/**
 * Search engine application. Does the searching using the TF-IDF criteria.
 * Needs a path to the folder that contains the files to search trough to search
 * in as a command line argument.
 * <p>
 * Supports four commands:
 * </p>
 * <ol>
 * <li>query - does the search on the given input.</li>
 * <li>type - accepts the number of the result and prints the document.</li>
 * <li>results - reprints the last results on the screen.</li>
 * <li>exit - closes the application.</li>
 * </ol>
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Konzola {
	
	/** Map of all commands of the shell. */
	private static Map<String, SearchCommand> commands = new HashMap<>();

	// Put all commands in the map of commands.
	{
		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());
		commands.put("results", new ResultsCommand());
		commands.put("exit", new ExitCommand());
	}
	
	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. One needed - path to the folder that
	 *             contains the files to search trough.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length != 1) {
			System.out.println("Invalid number of arguments! Provide path to folder with files to search.");
			return;
		}
		
		Path directoryPath = Paths.get(args[0]);
		
		if(Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
			System.out.println("Provided path must be to an existing directory.");
			return;
		}
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {

			for (Path file : stream) {
				// Skip invalid files
				if (Files.isDirectory(file) || !Files.isReadable(file)) { 
					continue;
				}
				
				// TODO mozda input stream
				List<String> lines = Files.readAllLines(file);

				
				// TODO implement vectors and such
				
			}
		} catch (IOException e) {
			System.out.println("Error while reading files from the directory.");
			System.out.println(e.getMessage());
			return;
		}
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.printf("%nEnter command > ");
			String userInput = sc.nextLine().trim();
			
			if(userInput.isBlank()) {
				System.out.println("Invalid input!");
				continue;
			}
			
			String[] input = userInput.split("\\s+", 2);
			String command = input[0];
			
			if(!commands.containsKey(command)) {
				System.out.println("Invalid command!");
				continue;
			}
			
			String argument = input.length > 1 ? input[1] : null; // TODO mozda kasnije ovo radit
			
			boolean continueApp = commands.get(command).execute(argument);
			if(!continueApp ) 
				break;
			
		}

		
		sc.close();
	}

}
