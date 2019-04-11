/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

/**
 * Class that contains one conditional expression used to filter student records.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ConditionalExpression {

	/**
	 * Used for retrieving the desired field for comparison
	 */
	private IFieldValueGetter fieldGetter;

	/**
	 * String to which the student record field is compared to
	 */
	private String stringLiteral;

	/**
	 * Operator which we use to compare the string literal and student record field
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Constructs a {@link ConditionalExpression} object
	 * 
	 * @param fieldGetter        Used for retrieving the desired field for
	 *                           comparison
	 * @param stringLiteral      String to which the student record field is
	 *                           compared to
	 * @param comparisonOperator Operator which we use to compare
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns field getter used for retrieving the desired field for comparison
	 * 
	 * @return the fieldGetter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns string to which the student record field is compared to
	 * 
	 * @return the stringLiteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Returns the operator which we use to compare the string literal and student
	 * record field
	 * 
	 * @return the comparisonOperator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
