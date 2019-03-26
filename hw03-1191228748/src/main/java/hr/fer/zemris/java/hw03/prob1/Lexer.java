/**
 * 
 */
package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Class that turns the input string into tokens ( {@link Token} )
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Lexer {
	/**
	 * Input text
	 */
	private char[] data;
	/**
	 * Current token
	 */
	private Token token;
	/**
	 * Index of the first char that is not processed
	 */
	private int currentIndex;

	/**
	 * Current state of the lexer
	 */
	private LexerState currentState = LexerState.BASIC;

	/**
	 * Constructor that accepts the input string that will be tokenized
	 * 
	 * @param text input string that will be tokenized
	 */
	public Lexer(String text) {
		data = Objects.requireNonNull(text).toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates and returns the next token
	 * 
	 * @return the next token
	 * @throws LexerException if there is an error
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("You can't read next token after the end of file (EOF)!");
		}

		while (true) {
			if (currentIndex == data.length) {
				token = new Token(TokenType.EOF, null);
				return token;
			}
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			break;
		}

		if (currentState == LexerState.BASIC) {
			return basicState();
		}

		return extendedState();

	}

	/**
	 * @return
	 */
	private Token basicState() {
		char currentChar = data[currentIndex];

		if (isLetter(currentChar)) {
			token = new Token(TokenType.WORD, getToken(this::isLetter));

		} else if (Character.isDigit(currentChar)) {
			try {
				token = new Token(TokenType.NUMBER, Long.valueOf(getToken(Character::isDigit)));
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid input!");
			}

		} else if (currentChar != '\\') {
			// Symbol, meaning that it is not a whitespace, number, letter or escape
			// character
			if (currentChar == '#') {
				setState(LexerState.EXTENDED);
			}
			token = new Token(TokenType.SYMBOL, Character.valueOf(currentChar));
			currentIndex++;
		} else {
			throw new LexerException("Invalid input!");
		}

		return token;
	}

	/**
	 * @return
	 */
	private Token extendedState() {

		char currentChar = data[currentIndex];

		if (notHashOrWhitespace(currentChar)) {
			token = new Token(TokenType.WORD, getToken(this::notHashOrWhitespace));

		} else { // currentChar is #
			token = new Token(TokenType.SYMBOL, Character.valueOf(currentChar));
			currentIndex++;
			setState(LexerState.BASIC);
		}

		return token;
	}

	public boolean notHashOrWhitespace(char c) {
		return c != '#' && !Character.isWhitespace(c);
	}

	// TODO Javadoc
	/**
	 * @author Zvonimir Šimunović
	 *
	 */
	private static interface CharTester {

		/**
		 * @param c
		 * @return
		 */
		public boolean testChar(char c);
	}

	/**
	 * @param t
	 * @return
	 */
	private String getToken(CharTester t) {
		String tokenValue = "";
		while (t.testChar(data[currentIndex])) {
			// If it's an escape character then the next char is the part of the token
			// Check will only pass in words
			if (isEscapeChar(data[currentIndex]) && currentState == LexerState.BASIC) {
				currentIndex++;
			}

			tokenValue += data[currentIndex];
			currentIndex++;
			if (currentIndex == data.length) {
				break;
			}
		}
		return tokenValue;
	}

	/**
	 * @param c
	 * @return
	 */
	private boolean isLetter(char c) {
		if (Character.isLetter(c))
			return true;

		// Checks if it's an escaped characters (valid ones are numbers and a backslash)
		return (isEscapeChar(c) && (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\'));
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
	 * Returns the last generated token. Can be called multiple times. Doesn't start
	 * the generation of the next token.
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the state of the lexer
	 * 
	 * @param state state that the lexers state will be put in
	 */
	public void setState(LexerState state) {
		currentState = Objects.requireNonNull(state);
	}
}
