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
	 * Word token type
	 */
	WORD, 
	/**
	 * Number token type
	 */
	NUMBER, 
	/**
	 * Symbol token type
	 */
	SYMBOL;
}
