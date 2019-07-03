package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;
import hr.fer.zemris.java.hw17.trazilica.SearchData;
import hr.fer.zemris.java.hw17.trazilica.SearchResult;

/**
 * User provides search input via arguments and then the search input i queried
 * aganst files in the provided folder nad TFIDF is calculated.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class QueryCommand implements SearchCommand {

	@Override
	public boolean execute(String arguments, SearchData data) {
		if (arguments == null || arguments.isBlank()) {
			System.out.println("No arguments provided.");
			return true;
		}

		List<SearchResult> results = data.getResults();

		if (results == null) {
			results = new ArrayList<>();
		}

		results.clear();

		String[] query = arguments.split("\\s+");

		if (query.length < 1) {
			System.out.println("Invalid argument.");
			return true;
		}

		System.out.print("Query is: [" + query[0]);
		for (int i = 1; i < query.length; i++) {
			System.out.print(", " + query[i]);
		}
		System.out.print("]\n");

		// TODO Query command

		for (int i = 0; i < results.size(); i++) {
			SearchResult res = results.get(i);
			System.out.printf("[ %d](%.4f) %s %n", i, res.getTfidf(), res.getPath());
		}

		return true;
	}

}
