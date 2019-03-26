/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

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
	 * Number (type Long) token type
	 */
	LONGNUMBER, 
	/**
	 * Number (type Integer) token type
	 */
	INTNUMBER,
	/** 
	 * Open braces '{' token type
	 */
	OPENBRACES,
	/**
	 * Closed braces '}' token type
	 */
	CLOSEDBRACES,
	/** 
	 * Open parenthesis '(' token type
	 */
	OPENPARENTHESIS,
	/**
	 * Closed parenthesis ')' token type
	 */
	CLOSEDPARENTHESIS,
	/**
	 * Dollar sign '$' token type
	 */
	DOLLARSIGN,
	/**
	 * Equals sign '=' token type
	 */
	EQUALSSIGN,
	/**
	 * Tag "for"
	 */
	FOR,
	/**
	 * Tag "end"
	 */
	END,
	/**
	 * Operator token type
	 */
	OPERATOR;
}
