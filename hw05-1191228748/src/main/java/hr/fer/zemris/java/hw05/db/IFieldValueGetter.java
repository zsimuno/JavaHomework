/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

/**
 * Interface for field value getters.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IFieldValueGetter {
	/**
	 * Gets the required field from the student record.
	 * 
	 * @param record record from which we get the field value from
	 * @return the required field from the student record
	 */
	public String get(StudentRecord record);
}
