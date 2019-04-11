/**
 * 
 */
package hr.fer.zemris.java.hw05.db.querylexer;

/**
 * Lexer that tokenizes the given text written in Query
 * 
 * @author Zvonimir Šimunović
 *
 */
public class QueryLexer {

	/**
	 * Input text
	 */
	private char[] data;
	/**
	 * Current QueryToken
	 */
	private QueryToken currentToken;
	/**
	 * Index of the first char that is not processed
	 */
	private int currentIndex;

	/**
	 * Constructor that accepts the input string that will be tokenized
	 * 
	 * @param text input string that will be tokenized
	 * @throws QueryLexerException if the {@code text} is {@code null} or there's an
	 *                             error.
	 */
	public QueryLexer(String text) {
		if (text == null) {
			throw new QueryLexerException("No text was given!");
		}
		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates and returns the next QueryToken
	 * 
	 * @return the next QueryToken
	 * @throws QueryLexerException if there is an error while tokenizing
	 */
	public QueryToken nextQueryToken() {
		if (currentToken != null && currentToken.getType() == QueryTokenType.EOF) {
			throw new QueryLexerException("No more tokens!");
		}

		// Ignores all whitespace
		while (true) {
			if (currentIndex == data.length) {
				currentToken = new QueryToken(QueryTokenType.EOF, null);
				return currentToken;
			}

			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}

			break;
		}

		return nextToken();

	}

	/**
	 * Gets the next token.
	 * 
	 * @return next token in data
	 * @throws QueryLexerException if there is an error while tokenizing
	 */
	private QueryToken nextToken() {
		char currentChar = data[currentIndex];

		if (Character.isLetter(currentChar)) { // Variable name or "and" or "like" operator
			String tokenString = getTokenString(Character::isLetter);

			if (tokenString.toLowerCase().equals("and")) {
				currentToken = new QueryToken(QueryTokenType.AND, "AND");

			} else if (tokenString.toLowerCase().equals("like")) {
				currentToken = new QueryToken(QueryTokenType.OPERATOR, "LIKE");

			} else {
				currentToken = new QueryToken(QueryTokenType.ATTRIBUTE, tokenString);

			}

		} else if (currentChar == '\"') { // String literal

			currentIndex++; // Move from quotation

			String tokenString = getTokenString((c) -> c != '\"');

			// If the loop ended but current character is not the quotation mark then string
			// is not valid
			if (currentIndex >= data.length || data[currentIndex] != '\"') {
				throw new QueryLexerException("Invalid string input!");
			}

			// Move from quotation marks
			currentIndex++;

			currentToken = new QueryToken(QueryTokenType.STRING, tokenString.toString());

		} else {
			QueryTokenType type;
			String tokenValue = "";

			switch (currentChar) {

			case '=':
				type = QueryTokenType.OPERATOR;
				tokenValue = "=";
				break;

			case '<':
			case '>':
				type = QueryTokenType.OPERATOR;
				tokenValue = Character.toString(currentChar);

				// Less/Greater and equals to
				if (currentIndex < data.length - 1 && data[currentIndex + 1] == '=') {
					tokenValue += "=";
					currentIndex++;
				}
				break;
			case '!':
				// Must have an equals sign after
				if (currentIndex < data.length - 1 && data[currentIndex + 1] == '=') {
					type = QueryTokenType.OPERATOR;
					tokenValue = "!=";
					currentIndex++;

				} else {
					throw new QueryLexerException("Invalid input " + currentChar + " !");
				}
				break;
			default:
				throw new QueryLexerException("Invalid input " + currentChar + " !");
			}

			currentToken = new QueryToken(type, tokenValue);
			currentIndex++;
		}

		return currentToken;

	}

	/**
	 * Tester for characters.
	 * 
	 * @author Zvonimir Šimunović
	 *
	 */
	private static interface CharTester {

		/**
		 * Checks if the character passes the test.
		 * 
		 * @param c character to be tested
		 * @return {@code true} if character passes the test, {@code false} otherwise
		 */
		boolean test(char c);
	}

	/**
	 * Gets a string to make the token of. Tests every character with the
	 * {@code Tester} t
	 * 
	 * @param t Tester which we test the characters
	 * @return String that will be turned into a token.
	 */
	private String getTokenString(CharTester tester) {
		int startingIndex = currentIndex;

		while (currentIndex < data.length && tester.test(data[currentIndex])) {
			currentIndex++;
		}

		return new String(data, startingIndex, currentIndex - startingIndex);
	}

	/**
	 * Returns the last generated QueryToken. Can be called multiple times. Doesn't
	 * start the generation of the next QueryToken.
	 * 
	 * @return last generated QueryToken
	 */
	public QueryToken getToken() {
		return currentToken;
	}

}