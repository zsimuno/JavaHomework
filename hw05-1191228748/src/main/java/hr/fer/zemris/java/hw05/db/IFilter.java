package hr.fer.zemris.java.hw05.db;

/**
 * 
 * Interface for filters.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IFilter {
	/**
	 * Checks if this filter accepts the given student record.
	 * 
	 * @param record record that is to be checked
	 * @return {@code true} if the filter accepts the record, {@code false}
	 *         otherwise
	 */
	public boolean accepts(StudentRecord record);
}