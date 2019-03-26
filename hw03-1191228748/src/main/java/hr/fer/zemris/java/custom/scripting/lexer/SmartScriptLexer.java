/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.Tester;
import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptLexer {

	/**
	 * Input text
	 */
	private char[] data;
	/**
	 * Current SmartScriptToken
	 */
	private SmartScriptToken SmartScriptToken;
	/**
	 * Index of the first char that is not processed
	 */
	private int currentIndex;

	/**
	 * Constructor that accepts the input string that will be SmartScriptTokenized
	 * 
	 * @param text input string that will be SmartScriptTokenized
	 */
	public SmartScriptLexer(String text) {
		data = Objects.requireNonNull(text).toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates and returns the next SmartScriptToken
	 * 
	 * @return the next SmartScriptToken
	 * @throws LexerException if there is an error
	 */
	public SmartScriptToken nextSmartScriptToken() {
		if (SmartScriptToken != null && SmartScriptToken.getType() == SmartScriptTokenType.EOF) {
			throw new LexerException("You can't read next SmartScriptToken after the end of file (EOF)!");
		}

		// Ignore all whitespace
		while (true) {
			if (currentIndex == data.length) {
				SmartScriptToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
				return SmartScriptToken;
			}

			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}

			break;
		}

		char currentChar = data[currentIndex];

		if (isLetter(currentChar)) {
			SmartScriptToken = new SmartScriptToken(SmartScriptTokenType.WORD,
					getSmartScriptTokenString(this::isLetter));

		} else if (Character.isDigit(currentChar)) {
			try {
				SmartScriptToken = new SmartScriptToken(SmartScriptTokenType.NUMBER,
						Long.valueOf(getSmartScriptTokenString(this::isDigit)));
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid input!");
			}

		} else if (currentChar != '\\') {
			// Symbol, meaning that it is not a whitespace, number, letter or escape
			// character
			SmartScriptToken = new SmartScriptToken(SmartScriptTokenType.SYMBOL, Character.valueOf(currentChar));
			currentIndex++;
		} else {
			throw new LexerException("Invalid input!");
		}

		return SmartScriptToken;

	}

	// TODO Javadoc

	/**
	 * @param t
	 * @return
	 */
	private String getSmartScriptTokenString(Tester t) {
		String SmartScriptTokenValue = "";
		while (currentIndex < data.length && t.test(data[currentIndex])) {
			// If it's an escape character then the next char is the part of the
			// SmartScriptToken
			// Check will only pass in words
			if (isEscapeChar(data[currentIndex])) {
				currentIndex++;
			}

			SmartScriptTokenValue += data[currentIndex];
			currentIndex++;
		}
		return SmartScriptTokenValue;
	}

	/**
	 * @param obj
	 * @return
	 */
	private boolean isLetter(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		if (Character.isLetter(c))
			return true;

		// Checks if it's an escaped characters (valid ones are numbers and a backslash)
		return (isEscapeChar(c) && (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\'));
	}

	/**
	 * @param obj
	 * @return
	 */
	private boolean isDigit(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		return Character.isDigit(c);
	}

	/**
	 * @param c
	 * @return
	 */
	private boolean isEscapeChar(char c) {
		// Checks currentIndex because backslash can be at the end of the input
		return c == '\\' && currentIndex < data.length - 1;
	}

	/**
	 * Returns the last generated SmartScriptToken. Can be called multiple times.
	 * Doesn't start the generation of the next SmartScriptToken.
	 * 
	 * @return last generated SmartScriptToken
	 */
	public SmartScriptToken getSmartScriptToken() {
		return SmartScriptToken;
	}

}
