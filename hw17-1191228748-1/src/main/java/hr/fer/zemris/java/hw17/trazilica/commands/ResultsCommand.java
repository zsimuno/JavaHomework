package hr.fer.zemris.java.hw17.trazilica.commands;

import java.util.List;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;
import hr.fer.zemris.java.hw17.trazilica.SearchData;
import hr.fer.zemris.java.hw17.trazilica.SearchResult;

/**
 * Print the results of the last query command if it exists.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ResultsCommand implements SearchCommand {

	@Override
	public boolean execute(String arguments, SearchData data) {
		if (arguments != null) {
			System.out.println("Command takes no arguments!");
			return true;
		}

		if (data == null || data.getResults() == null || data.getResults().isEmpty()) {
			System.out.println("No results to show!");
			return true;
		}

		List<SearchResult> results = data.getResults();

		for (int i = 0; i < results.size(); i++) {
			SearchResult res = results.get(i);
			System.out.printf("[ %d](%.4f) %s %n", i, res.getTfidf(), res.getPath());
		}

		return true;
	}

}
