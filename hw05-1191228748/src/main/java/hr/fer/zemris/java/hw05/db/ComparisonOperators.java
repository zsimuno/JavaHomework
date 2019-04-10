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

		// IF there is no wildcard characters then it's only checking if the strings are
		// equal
		if (!value2.contains("*"))
			return value2.equals(value1);

		int j = 0;
		int length2 = value2.length();
		int lenght1 = value1.length();

		for (int i = 0; i < length2; i++, j++) {

			// value2 has more characters that are not in value1
			if (j == lenght1) {
				return false;
			}

			if (value2.charAt(i) == '*') {
				
				// Skip the wildcard character
				i++;
				
				// if wildcard is at the end all characters i value1 from this position to the
				// end are valid
				if (i == length2) {
					return true;
				}

				while (true) {
					// value2 has more characters that are not in value1
					if (j == lenght1) {
						return false;
					}

					// If current char is the same as the one after the wildcard
					if (value2.charAt(i + 1) == value1.charAt(j)) {
						break;
					}

					// Skip the character
					j++;
				}

			} else if (value2.charAt(i) != value1.charAt(j)) {
				return false;
			}

		}

		// Second string has more characters that are not in the first one
		if (j != lenght1)
			return false;

		return true;
	};
}
