/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.Tester;
import hr.fer.zemris.java.hw03.prob1.LexerException;
import hr.fer.zemris.java.hw03.prob1.LexerState;

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
	private SmartScriptToken currentToken;
	/**
	 * Index of the first char that is not processed
	 */
	private int currentIndex;
	/**
	 * Current state of the lexer
	 */
	private SmartScriptLexerState currentState = SmartScriptLexerState.TEXT;

	/**
	 * Constructor that accepts the input string that will be SmartScriptTokenized
	 * 
	 * @param text input string that will be SmartScriptTokenized
	 * @throws NullPointerException if the {@code text} is {@code null}
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
		if (currentToken != null && currentToken.getType() == SmartScriptTokenType.EOF) {
			throw new LexerException("No more tokens!");
		}

		// Ignore all whitespace
		while (true) {
			if (currentIndex == data.length) {
				currentToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
				return currentToken;
			}

			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}

			break;
		}

		if (currentState == SmartScriptLexerState.TAG) {
			return tagStateToken();
		}

		return textStateToken();

	}

	/**
	 * Calculates the token in the {@code TAG} lexer state and returns it.
	 * 
	 * @return next token in data
	 */
	private SmartScriptToken tagStateToken() {
		char currentChar = data[currentIndex];

		if (Character.isLetter(currentChar)) { // Variable name
			currentToken = new SmartScriptToken(SmartScriptTokenType.TEXT, getTokenString(this::isLetter));

		} else if (isNumber(currentChar)) {
			try {
				String tokenString = "";
				// TODO if it's not digit-dot-digit value then return needed token

				// Negative number
				if (currentChar == '-') {
					currentIndex++;
					tokenString = "-";
				}
				tokenString += getTokenString(this::isDigit);

				// Decimal point
				if (data[currentIndex] == '.') {
					tokenString += '.';
					tokenString += getTokenString(this::isDigit);
				}

				currentToken = new SmartScriptToken(SmartScriptTokenType.NUMBER, Long.valueOf(tokenString));

			} catch (NumberFormatException e) {
				throw new LexerException("Invalid input!");
			}
		} else if (currentChar == '@') { // Function

		} else if (currentChar == '\"') { // String
			while (currentIndex < data.length && data[currentIndex] != '\"') {

			}
		} else {
			SmartScriptTokenType type;
			switch (currentChar) {
			case '{':
				type = SmartScriptTokenType.OPENBRACES;
				break;
			case '}':
				type = SmartScriptTokenType.CLOSEDBRACES;
				break;
			case '$':
				type = SmartScriptTokenType.DOLLARSIGN;
				break;
			case '=':
				type = SmartScriptTokenType.EQUALSSIGN;
				break;
			case '+':
			case '-':
			case '*':
			case '/':
			case '^':
				type = SmartScriptTokenType.OPERATOR;
				break;
			default:
				throw new LexerException("Invalid input!");
			}

			currentToken = new SmartScriptToken(type, Character.valueOf(currentChar));
			currentIndex++;

		}

		return currentToken;

	}

	/**
	 * Calculates the token in the {@code TEXT} lexer state and returns it.
	 * 
	 * @return next token in data
	 */
	private SmartScriptToken textStateToken() {
		// TODO check for open braces? 
		currentToken = new SmartScriptToken(SmartScriptTokenType.TEXT, getTokenString(this::isValidText));
		return currentToken;

	}

	/**
	 * Checks if a character is a valid text character. That means if it's any
	 * symbol other than whitespace or a backslash and there's either '\' or '{'
	 * after it.
	 * 
	 * @param c character that is checked if it's a valid text character
	 * @return {@code true} if character is a valid text character, {@code false} if
	 *         it's not.
	 */
	private boolean isValidText(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		if (c == '\\') {
			if (currentIndex < data.length - 1 && (c == '\\' || c == '{')) {
				currentIndex++;
				return true;
			} else {
				throw new SmartScriptLexerException("Not a valid escape character!");
			}
		}

		return !Character.isWhitespace(c);

	}

	/**
	 * Gets a string to make the token of. Tests every character with the
	 * {@code Tester} t
	 * 
	 * @param t Tester which we test the characters
	 * @return String that will be turned into a token.
	 */
	private String getTokenString(Tester t) {
		int startingIndex = currentIndex;

		while (currentIndex < data.length && t.test(data[currentIndex])) {
			currentIndex++;
		}

		return new String(data, startingIndex, currentIndex - startingIndex);
	}

	/**
	 * Checks if the current characters is a number or a start of a negative number
	 * 
	 * @param currentChar char to be checked
	 * @return {@code true} if the current characters is a number or a start of a
	 *         negative number, {@code false} if it's neither of those
	 */
	private boolean isNumber(char currentChar) {
		if (Character.isDigit(currentChar))
			return true;

		// If this char is minus sign and next one is a digit
		if (currentChar == '-' && (currentIndex < data.length && Character.isDigit(data[currentIndex + 1])))
			return true;

		return false;
	}

	/**
	 * Checks if the character is a letter.
	 * 
	 * @param obj object that is casted to {@link Character} and checked if it's a
	 *            letter
	 * @return {@code true} if the {@code obj} is a {@code Character} and a letter,
	 *         {@code false} if it's not both of those
	 */
	private boolean isLetter(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		return Character.isLetter(c) || Character.isDigit(c) || c == '_';

	}

	/**
	 * Checks if the character is a digit.
	 * 
	 * @param obj object that is casted to {@link Character} and checked if it's a
	 *            digit
	 * @return {@code true} if the {@code obj} is a {@code Character} and a digit,
	 *         {@code false} if it's not both of those
	 */
	private boolean isDigit(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		return Character.isDigit(c);
	}

	/**
	 * Checks if a character is a valid escape character. That means if it's a
	 * backslash and there's either '\', '"', 'n', 'r' or 't' after it.
	 * 
	 * @param c character that is checked if it's a valid escape character
	 * @return {@code true} if character is a valid escape character, {@code false}
	 *         if it's not.
	 */
	private boolean isValidEscapeChar(char c) {
		if (c == '\\') {
			// Checks currentIndex because backslash can be at the end of the input
			if (currentIndex < data.length - 1 && (c == '\\' || c == '\"' || c == 'n' || c == 'r' || c == 'y')) {
				currentIndex++;
				return true;
			} else {
				throw new SmartScriptLexerException("Not a valid escape character!");
			}
		}

		return false;
	}

	/**
	 * Returns the last generated SmartScriptToken. Can be called multiple times.
	 * Doesn't start the generation of the next SmartScriptToken.
	 * 
	 * @return last generated SmartScriptToken
	 */
	public SmartScriptToken getToken() {
		return currentToken;
	}

}
