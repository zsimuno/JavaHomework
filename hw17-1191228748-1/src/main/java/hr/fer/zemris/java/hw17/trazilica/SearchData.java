package hr.fer.zemris.java.hw17.trazilica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw17.trazilica.math.VectorN;

/**
 * Represents one {@link SearchCommand} result of a query.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SearchData {

	/** Vocabulary of all words (except stop words). */
	List<String> vocabulary = new ArrayList<>();

	/** Results of the last query. */
	List<SearchResult> results = new ArrayList<>();

	/** IDF values of words from vocabulary. */
	VectorN IdfValues = new VectorN();

	/** TF vectors for given files (paths). */
	Map<String, VectorN> tfValues = new HashMap<>();

	/**
	 * @return the vocabulary
	 */
	public List<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * @param vocabulary the vocabulary to set
	 */
	public void setVocabulary(List<String> vocabulary) {
		this.vocabulary = vocabulary;
	}

	/**
	 * @return the results
	 */
	public List<SearchResult> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

	/**
	 * @return the idfValues
	 */
	public VectorN getIdfValues() {
		return IdfValues;
	}

	/**
	 * @param idfValues the idfValues to set
	 */
	public void setIdfValues(VectorN idfValues) {
		IdfValues = idfValues;
	}

	/**
	 * @return the tfValues
	 */
	public Map<String, VectorN> getTfValues() {
		return tfValues;
	}

	/**
	 * @param tfValues the tfValues to set
	 */
	public void setTfValues(Map<String, VectorN> tfValues) {
		this.tfValues = tfValues;
	}

}
