/**
 * 
 */
package hr.fer.zemris.java.hw05.db.querylexer;

/**
 * Enumeration that contains possible token types.
 * 
 * @author Zvonimir Šimunović
 *
 */
public enum QueryTokenType {
	/**
	 * End of file token type
	 */
	EOF, 
	/**
	 * String literal token type
	 */
	STRING,
	/**
	 * String literal token type
	 */
	AND,
	/**
	 * Variable token type
	 */
	ATTRIBUTE, 
	/**
	 * Operator token type
	 */
	OPERATOR;
}
