package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;

/**
 * Represents one {@link SearchCommand} result of a query.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SearchResult implements Comparable<SearchResult> {
	/** Path of the file that is searched. */
	private Path path;

	/** Calculated similarity. */
	private Double sim;

	/**
	 * Constructor
	 * 
	 * @param path Path of the file that is searched.
	 * @param sim  Calculated tfidf.
	 */
	public SearchResult(Path path, Double sim) {
		this.path = path;
		this.sim = sim;
	}

	/**
	 * @return the path of the file that is searched
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @return the calculated tfidf
	 */
	public double getTfidf() {
		return sim;
	}

	@Override
	public int compareTo(SearchResult o) {
		return Double.valueOf(sim).compareTo(o.getTfidf());
	}
}
