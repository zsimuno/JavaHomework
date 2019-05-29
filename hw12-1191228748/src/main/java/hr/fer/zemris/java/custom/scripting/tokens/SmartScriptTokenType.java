/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Enumeration that contains possible token types.
 * 
 * @author Zvonimir Šimunović
 *
 */
public enum SmartScriptTokenType {
	/**
	 * End of file token type
	 */
	EOF, 
	/**
	 * Text token type
	 */
	TEXT,
	/**
	 * String token type
	 */
	STRING,
	/**
	 * Variable token type
	 */
	VARIABLE, 
	/**
	 * Function token type
	 */
	FUNCTION, 
	/**
	 * Number (type Double) token type
	 */
	DOUBLE, 
	/**
	 * Number (type Integer) token type
	 */
	INTEGER,
	/** 
	 * Opening tag "{$"
	 */
	OPENTAG,
	/**
	 * Closing tag "$}"
	 */
	CLOSETAG,
	/**
	 * Equals sign '=' token type
	 */
	EQUALSSIGN,
	/**
	 * Operator token type
	 */
	OPERATOR;
}
