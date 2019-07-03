package hr.fer.zemris.java.hw17.trazilica.commands;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;
import hr.fer.zemris.java.hw17.trazilica.SearchData;
import hr.fer.zemris.java.hw17.trazilica.SearchResult;

/**
 * Command that prints the file of the result with the given index (index given
 * as an argument) if the result is available.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TypeCommand implements SearchCommand {

	@Override
	public boolean execute(String arguments, SearchData data) {
		if (arguments == null || arguments.isBlank()) {
			System.out.println("Command takes one argument - index of the result.");
			return true;
		}

		if (data == null || data.getResults() == null || data.getResults().isEmpty()) {
			System.out.println("No results to show.");
			return true;
		}
		int index;
		try {
			index = Integer.parseInt(arguments);
		} catch (Exception e) {
			System.out.println("Invalid argument.");
			return true;
		}

		SearchResult res = data.getResults().get(index);

		Path path = Paths.get(res.getPath());

		if (Files.isDirectory(path) || !Files.isReadable(path)) {
			System.out.println("Given type is not a file or not readable!");
			return true;
		}

		System.out.println("---------------------------------------------------------------------------");
		System.out.println("Dokument: " + res.getPath());
		System.out.println("---------------------------------------------------------------------------");

		// Open the file input and write the file
		try (InputStream is = Files.newInputStream(path)) {
			byte[] buff = new byte[1024];

			while (true) {
				// Read from file
				int r = is.read(buff);

				if (r < 1)
					break;

				System.out.print(new String(buff, 0, r, StandardCharsets.UTF_8));

			}

		} catch (Exception e) {
			System.out.println("Problem with reading the file: " + e.getMessage());
			return true;

		}

		System.out.println("");
		System.out.println("---------------------------------------------------------------------------");
		return true;
	}

}
