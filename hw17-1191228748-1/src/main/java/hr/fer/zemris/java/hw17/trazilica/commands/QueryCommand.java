package hr.fer.zemris.java.hw17.trazilica.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw17.trazilica.SearchCommand;
import hr.fer.zemris.java.hw17.trazilica.SearchData;
import hr.fer.zemris.java.hw17.trazilica.SearchResult;
import hr.fer.zemris.java.hw17.trazilica.math.VectorN;

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

		List<String> vocabulary = data.getVocabulary();
		VectorN tfSearch = new VectorN(vocabulary.size());

		for (String word : query) {
			if (!vocabulary.contains(word))
				continue;

			tfSearch.increment(vocabulary.indexOf(word));
		}

		VectorN idf = data.getIdfValues();
		VectorN tfidfSearch = new VectorN(vocabulary.size());
		for (int i = 0; i < tfidfSearch.size(); i++) {
			tfidfSearch.set(i, tfSearch.get(i) * idf.get(i));
		}

		for (Map.Entry<Path, VectorN> entry : data.getTfidfValues().entrySet()) {
			if (results.size() == 10)
				break;

			Double sim = entry.getValue().cosAngle(tfidfSearch);
			if (sim > 0) {
				results.add(new SearchResult(entry.getKey(), sim));
			}
		}

		if(results.size() == 0) {
			System.out.println("No results.");
			return true;
		}
		
		results.sort(Comparator.reverseOrder());
		for (int i = 0; i < results.size(); i++) {
			SearchResult res = results.get(i);
			System.out.printf("[ %d](%.4f) %s %n", i, res.getTfidf(), res.getPath());
		}

		return true;
	}

}
