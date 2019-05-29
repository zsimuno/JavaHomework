/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration that represents different states that lexer can be in
 * 
 * @author Zvonimir Šimunović
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Text lexer state where we tokenize text
	 */
	TEXT,
	/**
	 * Tag lexer state where we tokenize tags
	 */
	TAG;
}
