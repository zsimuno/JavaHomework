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
	 * @throws NullPointerException if the {@code text} is {@code null} 
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

		// Removes all whitespace
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
	 * Calculates the token in the {@code BASIC} lexer state and returns it.
	 * 
	 * @return next token in data
	 */
	private Token basicState() {
		char currentChar = data[currentIndex];

		if (isLetter(currentChar)) {
			token = new Token(TokenType.WORD, getTokenString(this::isLetter));

		} else if (Character.isDigit(currentChar)) {
			try {
				token = new Token(TokenType.NUMBER, Long.valueOf(getTokenString(this::isDigit)));
			} catch (NumberFormatException e) {
				throw new LexerException("Invalid input!");
			}

		} else if (currentChar != '\\') {
			// Symbol, meaning that it is not a whitespace, number, letter or escape
			// character
			token = new Token(TokenType.SYMBOL, Character.valueOf(currentChar));
			currentIndex++;
		} else {
			throw new LexerException("Invalid input!");
		}

		return token;
	}

	/**
	 * Calculates the token in the {@code EXTENDED} lexer state and returns it.
	 * 
	 * @return next token in data
	 */
	private Token extendedState() {

		char currentChar = data[currentIndex];

		if (notHashOrWhitespace(currentChar)) {
			token = new Token(TokenType.WORD, getTokenString(this::notHashOrWhitespace));

		} else { // currentChar is #
			token = new Token(TokenType.SYMBOL, Character.valueOf(currentChar));
			currentIndex++;
		}

		return token;
	}


	/**
	 * Gets a string to make the token of. Tests every character with the
	 * {@code Tester} t
	 * 
	 * @param t Tester which we test the characters
	 * @return String that will be turned into a token.
	 */
	private String getTokenString(Tester t) {
		StringBuilder tokenValue = new StringBuilder();
		while (currentIndex < data.length && t.test(data[currentIndex])) {
			// If it's an escape character then the next char is the part of the token
			// Check will only pass in words
			if (isEscapeChar(data[currentIndex]) && currentState == LexerState.BASIC) {
				currentIndex++;
			}

			tokenValue.append(data[currentIndex]);
			currentIndex++;
		}
		return tokenValue.toString();
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

		if (Character.isLetter(c))
			return true;

		// Checks if it's an escaped characters (valid ones are numbers and a backslash)
		return (isEscapeChar(c) && (Character.isDigit(data[currentIndex + 1]) || data[currentIndex + 1] == '\\'));
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
	 * Checks if character is not a hash ('#') or a whitespace character.
	 * 
	 * @param obj object that is casted to {@link Character} and checked if it's not
	 *            a hash ('#') or a whitespace character
	 * @return {@code true} if the {@code obj} is a {@code Character} and a hash
	 *         ('#') or a whitespace character, {@code false} if it's not both of
	 *         those
	 */
	private boolean notHashOrWhitespace(Object obj) {
		if (!(obj instanceof Character)) {
			return false;
		}
		char c = (Character) obj;

		return c != '#' && !Character.isWhitespace(c);
	}

	/**
	 * Checks if a character is an escape character. That means if it's a backslash
	 * and there's more characters after it.
	 * 
	 * @param c character that is checked if it's a escape character
	 * @return {@code true} if character is an escape character, {@code false} if
	 *         it's not.
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
