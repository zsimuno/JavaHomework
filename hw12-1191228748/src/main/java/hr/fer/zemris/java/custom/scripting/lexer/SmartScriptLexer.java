/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.function.Predicate;

import hr.fer.zemris.java.custom.scripting.tokens.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.tokens.SmartScriptTokenType;

/**
 * Lexer that tokenizes the given text written in SmartScript
 * 
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
	}

	/**
	 * Generates and returns the next SmartScriptToken
	 * 
	 * @return the next SmartScriptToken
	 * @throws SmartScriptLexerException if there is an error while tokenizing
	 */
	public SmartScriptToken nextSmartScriptToken() {
		if (currentToken != null && currentToken.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("No more tokens!");
		}

		if (currentState == SmartScriptLexerState.TAG) {
			
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
			return tagStateToken();
		}

		if (currentIndex == data.length) {
			currentToken = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return currentToken;
		}

		return textStateToken();

	}

	/**
	 * Calculates the token in the {@code TAG} lexer state and returns it.
	 * 
	 * @return next token in data
	 * @throws SmartScriptLexerException if there is an error while tokenizing
	 */
	private SmartScriptToken tagStateToken() {
		char currentChar = data[currentIndex];

		if (Character.isLetter(currentChar)) { // Variable name or keyword
			String tokenString = getTokenString(this::isValidVariableName);

			currentToken = new SmartScriptToken(SmartScriptTokenType.VARIABLE, tokenString);

		} else if (isNumber(currentChar)) { // Number
			StringBuilder tokenString = new StringBuilder();

			// Negative number
			if (currentChar == '-') {
				currentIndex++;
				tokenString.append("-");
			}
			
			// Number digits
			tokenString.append(getTokenString(this::isDigit));

			try {
				// Is it a decimal point and a digit-dot-digit notation (i.e. is next char
				// digit) or just an integer
				if (currentIndex < data.length && data[currentIndex] == '.' && currentIndex < data.length - 1
						&& Character.isDigit(data[currentIndex + 1])) {

					tokenString.append('.');
					currentIndex++;
					
					tokenString.append(getTokenString(this::isDigit)); // After digit
					
					currentToken = new SmartScriptToken(SmartScriptTokenType.DOUBLE,
							Double.valueOf(tokenString.toString()));

				} else { // Integer
					currentToken = new SmartScriptToken(SmartScriptTokenType.INTEGER,
							Integer.valueOf(tokenString.toString()));
				}
				

			} catch (NumberFormatException e) {
				throw new SmartScriptLexerException("Invalid input " + tokenString.toString() + "!");
			}

		} else if (isFunctionName(currentChar)) { // Function
			// Move from @ character
			currentIndex++;
			// Variable and function names can contain the same characters
			currentToken = new SmartScriptToken(SmartScriptTokenType.FUNCTION,
					getTokenString(this::isValidVariableName));

		} else if (currentChar == '\"') { // String
			StringBuilder tokenString = new StringBuilder();
			currentIndex++; // Move from quotation
			
			while (currentIndex < data.length && data[currentIndex] != '\"') {
				
				if (isValidEscapeChar(data[currentIndex])) {
					char next = data[currentIndex + 1];
					switch (next) {
					case 'r':
						tokenString.append("\r");
						break;
					case 'n':
						tokenString.append("\n");
						break;
					case 't':
						tokenString.append("\t");
						break;

					default:
						tokenString.append(next); // quotation or backslash
						break;
					}

					currentIndex += 2;

				} else {
					tokenString.append(data[currentIndex]);
					currentIndex++;
				}

			}
			// If the loop ended but current character is not the quotation mark then string
			// is not valid
			if (currentIndex >= data.length || data[currentIndex] != '\"') {
				throw new SmartScriptLexerException("Invalid string input!");
			}
			// Move from quotation marks
			currentIndex++;

			currentToken = new SmartScriptToken(SmartScriptTokenType.STRING, tokenString.toString());

		} else if (currentChar == '{') { // Opening tag
			if (currentIndex < data.length - 1 && data[currentIndex + 1] == '$') {
				currentToken = new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$");

			} else {
				throw new SmartScriptLexerException("Invalid input " + currentChar + " !");
			}
			currentIndex += 2;

		} else if (currentChar == '$') { // Closing tag
			if (currentIndex < data.length - 1 && data[currentIndex + 1] == '}') {
				currentToken = new SmartScriptToken(SmartScriptTokenType.CLOSETAG, "$}");
			} else {
				throw new SmartScriptLexerException("Invalid input " + currentChar + " !");
			}
			currentIndex += 2;

		} else {
			SmartScriptTokenType type;
			switch (currentChar) {
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
				throw new SmartScriptLexerException("Invalid input " + currentChar + " !");
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
	 * @throws SmartScriptLexerException if there is an error while tokenizing
	 */
	private SmartScriptToken textStateToken() {
		// Checks if it's opening tag (that is "{$")
		if (data[currentIndex] == '{' && currentIndex < data.length - 1 && data[currentIndex + 1] == '$') {
			currentToken = new SmartScriptToken(SmartScriptTokenType.OPENTAG, "{$");
			currentIndex += 2;
			
		} else { // Regular text
			StringBuilder tokenString = new StringBuilder();
			while (currentIndex < data.length && !isOpenTag()) {
				if (isValidTextEscapeChar(data[currentIndex])) { // Meaning { or backslash
					tokenString.append(data[currentIndex + 1]);
					currentIndex += 2;
					
				} else {
					tokenString.append(data[currentIndex]);
					currentIndex++;
				}

			}

			currentToken = new SmartScriptToken(SmartScriptTokenType.TEXT, tokenString.toString());
		}

		return currentToken;

	}

	/**
	 * Checks if next token will be an open tag
	 * 
	 * @return {@code true} if next token will be an open tag , {@code false}
	 *         otherwise
	 */
	private boolean isOpenTag() {
		return data[currentIndex] == '{' && currentIndex < data.length - 1 && data[currentIndex + 1] == '$';
	}

	/**
	 * Checks if a character is a valid text escape character. That means if it's a
	 * backslash and there's either '\' or '{' after it.
	 * 
	 * @param c character that is checked if it's a valid text escape character
	 * @return {@code true} if character is a valid text escape character,
	 *         {@code false} if it's not.
	 * @throws SmartScriptLexerException if there is an error while tokenizing
	 */
	private boolean isValidTextEscapeChar(char c) {
		if (c == '\\') {
			// Checks currentIndex because backslash can be at the end of the input
			if (currentIndex < data.length - 1) {
				char next = data[currentIndex + 1];
				if (next == '\\' || next == '{') {
					return true;
				} else {
					throw new SmartScriptLexerException("Not a valid escape character!");
				}

			} else {
				throw new SmartScriptLexerException("Not a valid escape character!");
			}
		}
		return false;

	}

	/**
	 * Gets a string to make the token of. Tests every character with the
	 * {@code Tester} t
	 * 
	 * @param t Tester which we test the characters
	 * @return String that will be turned into a token.
	 */
	private String getTokenString(Predicate<Object> t) {
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
	 * Checks if a character is a valid escape character in STRING token type. That
	 * means if it's a backslash and there's either '\', '"', 'n', 'r' or 't' after
	 * it.
	 * 
	 * @param c character that is checked if it's a valid escape character
	 * @return {@code true} if character is a valid escape character, {@code false}
	 *         if it's not.
	 * @throws SmartScriptLexerException if there is an error while tokenizing
	 */
	private boolean isValidEscapeChar(char c) {
		if (c == '\\') {
			// Checks currentIndex because backslash can be at the end of the input
			if (currentIndex < data.length - 1) {
				char next = data[currentIndex + 1];
				if (next == '\\' || next == '\"' || next == 'n' || next == 'r' || next == 't') {
					return true;
				} else {
					throw new SmartScriptLexerException("Not a valid escape character!");
				}

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
	 * @throws SmartScriptLexerException if state is null
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new SmartScriptLexerException("No such state as null state!");
		}
		currentState = state;
	}

}
