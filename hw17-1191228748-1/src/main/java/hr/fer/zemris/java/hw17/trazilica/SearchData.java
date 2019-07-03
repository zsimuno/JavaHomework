package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
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
	private List<String> vocabulary = new ArrayList<>();

	/** Results of the last query. */
	private List<SearchResult> results = new ArrayList<>();

	/** IDF values of words from vocabulary. */
	private VectorN IdfValues = new VectorN();

	/** TF vectors for given files (paths). */
	private Map<Path, VectorN> tfValues = new HashMap<>();

	/** TF vectors for given files (paths). */
	private Map<Path, VectorN> tfidfValues = new HashMap<>();

	/** NUmber of documents we search in. */
	int numberOfDocuments;

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
	public Map<Path, VectorN> getTfValues() {
		return tfValues;
	}

	/**
	 * @param tfValues the tfValues to set
	 */
	public void setTfValues(Map<Path, VectorN> tfValues) {
		this.tfValues = tfValues;
	}

	/**
	 * @return the tfidfValues
	 */
	public Map<Path, VectorN> getTfidfValues() {
		return tfidfValues;
	}

	/**
	 * @param tfidfValues the tfidfValues to set
	 */
	public void setTfidfValues(Map<Path, VectorN> tfidfValues) {
		this.tfidfValues = tfidfValues;
	}

	/**
	 * @return the number Of Documents
	 */
	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}

	/**
	 * @param numberOfDocuments the number Of Documents to set
	 */
	public void setNumberOfDocuments(int numberOfDocuments) {
		this.numberOfDocuments = numberOfDocuments;
	}

}
