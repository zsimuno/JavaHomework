/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class that contains all supported comparison operators.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ComparisonOperators {
	/**
	 * Represents less (<) operator 
	 *
	 */
	public static IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	/**
	 * Represents less or equals (<=) operator
	 */
	public static IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	/**
	 * Represents greater (>) operator
	 */
	public static IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	/**
	 * Represents greater or equals (>=) operator
	 */
	public static IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	/**
	 * Represents equals (=) operator
	 */
	public static IComparisonOperator EQUALS = Objects::equals;
	/**
	 * Represents not equals (!=) operator
	 */
	public static IComparisonOperator NOT_EQUALS = (v1, v2) -> !Objects.equals(v1, v2);
	/**
	 * Represents like operator
	 */
	public static IComparisonOperator LIKE = (value1, value2) -> {
		// TODO fix this implementation
		if(value1.length() != value2.length()) 
			return false;
		
		for (int i = 0; i < value1.length(); i++) {
			
			// Can be any char at value1
			if(value2.charAt(i) == '*')
				continue;
			
			if(value1.charAt(i) != value2.charAt(i)) 
				return false;
		}
		
		return true;
	};
}
