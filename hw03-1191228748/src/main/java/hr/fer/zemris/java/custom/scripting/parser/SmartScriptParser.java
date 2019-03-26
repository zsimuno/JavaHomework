/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;

/**
 * TODO javadoc
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer that will be user to get tokens
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * 
	 */
	public SmartScriptParser(String text) {
		// TODO In this constructor, parser should create an instance of lexer and initialize
		// it with obtained text.
		lexer = new SmartScriptLexer(text);
	}

}
