/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.Tester;

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
	 * @throws SmartScriptLexerException if the {@code text} is {@code null} or
	 *                                   there's an error.
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new SmartScriptLexerException("No text was given!");
		}
		data = text.toCharArray();
		currentIndex = 0;
		nextSmartScriptToken();
	}

	/**
	 * Generates and returns the next SmartScriptToken
	 * 
	 * @return the next SmartScriptToken
	 * @throws LexerException if there is an error
	 */
	public SmartScriptToken nextSmartScriptToken() {
		if (currentToken != null && currentToken.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("No more tokens!");
		}

		// Ignore all spaces TODO check if all or only spaces
		while (true) {
			if (currentIndex == data.length) {
				currentToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
				return currentToken;
			}

			if (data[currentIndex] == ' ') {
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

		if (Character.isLetter(currentChar)) { // Variable name or keyword
			String tokenString = getTokenString(this::isValidVariableName);

			switch (tokenString.toUpperCase()) {
			case "FOR":
				currentToken = new SmartScriptToken(SmartScriptTokenType.FOR, tokenString);
				break;
			case "END":
				currentToken = new SmartScriptToken(SmartScriptTokenType.END, tokenString);
				break;
			default:
				currentToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, tokenString);
				break;
			}

		} else if (isNumber(currentChar)) { // Number
			String tokenString = "";
			// TODO if it's not digit-dot-digit value then return needed token

			// Negative number
			if (currentChar == '-') {
				currentIndex++;
				tokenString = "-";
			}
			tokenString += getTokenString(this::isDigit);

			try {
				// Is it a decimal point and a digit-dot-digit notation (i.e. is next char
				// digit)
				if (data[currentIndex] == '.' && currentIndex < data.length - 1
						&& Character.isDigit(data[currentIndex + 1])) {

					tokenString += '.';
					currentIndex++;
					tokenString += getTokenString(this::isDigit);
					currentToken = new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(tokenString));

				} else { // Integer
					currentToken = new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(tokenString));
				}

			} catch (NumberFormatException e) {
				throw new SmartScriptLexerException("Invalid input " + tokenString + "!");
			}

		} else if (isFunctionName(currentChar)) { // Function
			// Move from @ character
			currentIndex++;
			// Variable and function names can contain same characters
			currentToken = new SmartScriptToken(SmartScriptTokenType.FUNCTION,
					"@" + getTokenString(this::isValidVariableName));

		} else if (currentChar == '\"') { // String
			int startingIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				if (isValidEscapeChar(data[currentIndex])) {
					currentIndex += 2;

				} else if (data[currentIndex] == '$') {
					break;
				} else {
					currentIndex++;
				}

			}
			String tokenString = new String(data, startingIndex, currentIndex + 1 - startingIndex);
			// If the loop ended but current character is not the quotation mark then string
			// is not valid
			if (data[currentIndex] != '\"') {
				throw new SmartScriptLexerException("Invalid string input " + data[currentIndex] + "!");
			}
			// Move from quotation marks
			currentIndex++;

			currentToken = new SmartScriptToken(SmartScriptTokenType.STRING, tokenString);

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
				throw new SmartScriptLexerException("Invalid input!");
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
		if (data[currentIndex] == '{') {
			currentToken = new SmartScriptToken(SmartScriptTokenType.OPENBRACES, Character.valueOf('{'));
			currentIndex++;
		} else {
			currentToken = new SmartScriptToken(SmartScriptTokenType.TEXT, getTokenString(this::isValidText));
		}

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

		return c != '{';

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
	 * Checks if the character is a letter, digit or underscore.
	 * 
	 * @param obj object that is casted to {@link Character} and checked if it's a
	 *            letter, digit or underscore
	 * @return {@code true} if the {@code obj} is a {@code Character} and a letter,
	 *         digit or underscore, {@code false} if it's not any of those
	 */
	private boolean isValidVariableName(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		return Character.isLetter(c) || Character.isDigit(c) || c == '_';

	}

	/**
	 * Checks if the character is a '@' followed by a letter. (function name)
	 * 
	 * @param c character that is checked if it is a '@' followed by a letter
	 * @return {@code true} if the character is a '@' followed by a letter,
	 *         {@code false} if it's not any of those
	 */
	private boolean isFunctionName(char c) {
		return c == '@' && currentIndex < data.length - 1 && Character.isLetter(data[currentIndex + 1]);
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
			if (currentIndex < data.length - 1 && (c == '\\' || c == '\"' || c == 'n' || c == 'r' || c == 't')) {
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

	/**
	 * Sets the state of the lexer
	 * 
	 * @param state state that the lexers state will be put in
	 */
	public void setState(SmartScriptLexerState state) {
		currentState = Objects.requireNonNull(state);
	}

}
