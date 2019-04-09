/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

/**
 * Interface for classes that represend comparison operators.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface IComparisonOperator {
	/** 
	 * Checks if the given values satisfy current comparison operator.
	 * 
	 * @param value1 first value
	 * @param value2 second value
	 * @return {@code true} if current comparison is satisfied, {@code false} otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
