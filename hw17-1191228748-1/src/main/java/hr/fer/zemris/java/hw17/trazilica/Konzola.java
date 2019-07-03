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
import hr.fer.zemris.java.hw17.trazilica.math.VectorN;

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
	static {
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
		if (args.length != 1) {
			System.out.println("Invalid number of arguments! Provide path to folder with files to search.");
			return;
		}

		Path directoryPath = Paths.get(args[0]);

		if (Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
			System.out.println("Provided path must be to an existing directory.");
			return;
		}

		SearchData data = new SearchData();

		List<String> stopWords;
		try {
			stopWords = Files.readAllLines(Paths.get("hrvatski_stoprijeci.txt"));
		} catch (IOException e1) {
			System.out.println("Unable to read the file with stop words.");
			return;
		}

		setVocabularyAndTf(data, directoryPath, stopWords);

		data.setNumberOfDocuments(data.getTfValues().size());

		setIdfValues(data);

		calculateTfidf(data);

		System.out.println("Vocabulary size is " + data.getVocabulary().size() + " words.\n");

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("%nEnter command > ");
			String userInput = sc.nextLine().trim();

			if (userInput.isBlank()) {
				System.out.println("Invalid input!");
				continue;
			}

			String[] input = userInput.split("\\s+", 2);
			String command = input[0];

			if (!commands.containsKey(command)) {
				System.out.println("Invalid command!");
				continue;
			}

			String arguments = input.length > 1 ? input[1] : null;

			boolean continueApp = commands.get(command).execute(arguments, data);
			if (!continueApp)
				break;

		}

		sc.close();
	}

	/**
	 * Sets the vocabulary and calculates TF values.
	 * 
	 * @param data          contains all important data for calculating
	 * @param directoryPath path to the parent directory of the files we search in
	 * @param stopWords     stop words that don't go into vocabulary
	 */
	private static void setVocabularyAndTf(SearchData data, Path directoryPath, List<String> stopWords) {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath)) {

			Map<Path, VectorN> tfValues = data.getTfValues();
			List<String> vocabulary = data.getVocabulary();

			for (Path file : stream) {
				// Skip invalid files
				if (Files.isDirectory(file) || !Files.isReadable(file)) {
					continue;
				}

				VectorN tf = new VectorN(vocabulary.size());

				Scanner input = new Scanner(file);

				while (input.hasNext()) {
					// Remove non alphabetic characters (split on them)
					String[] wordsArray = input.next().split("\\P{L}+");

					for (String word : wordsArray) {
						word = word.toLowerCase();
						if (stopWords.contains(word) || word.isBlank())
							continue;

						if (!vocabulary.contains(word)) {
							vocabulary.add(word);
							tf.add(1.);

							// Add 0 for this word to every counter
							for (VectorN v : tfValues.values()) {
								v.add(0.);
							}
						} else {
							int index = vocabulary.indexOf(word);
							tf.increment(index);
						}
					}

				}

				tfValues.put(file, tf);

				input.close();

			}
		} catch (IOException e) {
			System.out.println("Error while reading files from the directory.");
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}

	/**
	 * Calculate IDF values.
	 * 
	 * @param data contains all important data for calculating
	 */
	private static void setIdfValues(SearchData data) {
		VectorN idf = data.getIdfValues();

		List<String> voc = data.getVocabulary();
		for (int i = 0; i < voc.size(); i++) {
			double counter = 0;
			for (VectorN v : data.getTfValues().values()) {
				if (v.get(i) > 0) {
					counter++;
				}
			}
			idf.add(Math.log(data.getNumberOfDocuments() / counter));

		}

	}

	/**
	 * Calculate TF-IDF values.
	 * 
	 * @param data contains all important data for calculating
	 */
	private static void calculateTfidf(SearchData data) {
		Map<Path, VectorN> tfdif = data.getTfidfValues();
		VectorN idf = data.getIdfValues();

		for (Map.Entry<Path, VectorN> entry : data.getTfValues().entrySet()) {
			VectorN tfdifVector = new VectorN(entry.getValue().size());
			VectorN tfVal = entry.getValue();
			for (int i = 0; i < tfVal.size(); i++) {
				Double val = tfVal.get(i);

				tfdifVector.set(i, val * idf.get(i));
			}

			tfdif.put(entry.getKey(), tfdifVector);
		}
	}

}
