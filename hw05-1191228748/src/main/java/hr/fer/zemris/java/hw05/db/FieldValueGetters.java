/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

/**
 * Contains field value getters.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FieldValueGetters {

	/**
	 * Getter for the first name of the student
	 */
	public static IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	/**
	 * Getter for the last name of the student
	 */
	public static IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	/**
	 * Getter for the JMBAG of the student
	 */
	public static IFieldValueGetter JMBAG = StudentRecord::getJmbag;

}
