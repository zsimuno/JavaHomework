package hr.fer.zemris.java.hw17.trazilica;

/**
 * Represents one {@link SearchCommand} result of a query.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SearchResult {
	/** Path of the file that is searched. */
	private String path;

	/** Calculated tfidf. */
	private double tfidf;

	/**
	 * Constructor
	 * 
	 * @param path  Path of the file that is searched.
	 * @param tfidf Calculated tfidf.
	 */
	public SearchResult(String path, double tfidf) {
		this.path = path;
		this.tfidf = tfidf;
	}

	/**
	 * @return the path of the file that is searched
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the calculated tfidf
	 */
	public double getTfidf() {
		return tfidf;
	}
}
