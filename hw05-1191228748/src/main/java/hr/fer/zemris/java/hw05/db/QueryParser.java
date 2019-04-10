/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.java.hw05.db.querylexer.*;

/**
 * Parser for database queries on the database of student records.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class QueryParser {

	/**
	 * Lexer that will be user to get tokens
	 */
	private QueryLexer lexer;

	/**
	 * List of all conditional expressions
	 */
	private ArrayList<ConditionalExpression> expressionsList = new ArrayList<>();

	/**
	 * Stores if the query is direct query (form of jmbag="xxx") or not
	 */
	private boolean isDirectQuery;

	/**
	 * Stores jmbag used in a direct query ("xxx" in query of the form jmbag="xxx")
	 */
	private String directQueryJmbag;

	/**
	 * Contains valid attributes
	 */
	private static HashMap<String, IFieldValueGetter> validAttrs;

	// Initialize the list of valid attributes
	static {
		validAttrs = new HashMap<>();
		validAttrs.put("jmbag", FieldValueGetters.JMBAG);
		validAttrs.put("lastName", FieldValueGetters.LAST_NAME);
		validAttrs.put("firstName", FieldValueGetters.FIRST_NAME);
	}

	/**
	 * Contains all operators
	 */
	private static HashMap<String, IComparisonOperator> operators;

	// Initialize the list of valid attributes
	static {
		operators = new HashMap<>();
		operators.put(">", ComparisonOperators.GREATER);
		operators.put(">=", ComparisonOperators.GREATER_OR_EQUALS);
		operators.put("<", ComparisonOperators.LESS);
		operators.put("<=", ComparisonOperators.LESS_OR_EQUALS);
		operators.put("=", ComparisonOperators.EQUALS);
		operators.put("!=", ComparisonOperators.NOT_EQUALS);
		operators.put("LIKE", ComparisonOperators.LIKE);
	}

	/**
	 * Constructs a parser and parses given query
	 * 
	 * @param text text that will be parsed
	 * @throws SmartScriptParserException on parse error
	 */
	public QueryParser(String text) {

		// Construct the lexer
		try {
			lexer = new QueryLexer(text);
		} catch (QueryLexerException e) {
			throw new QueryParserException(e.getMessage());
		}

		// Get the first token
		nextToken();

		parse();

	}

	/**
	 * Parses the query
	 * 
	 * @throws SmartScriptParserException on parse error
	 */
	private void parse() {
		boolean multipleAttributes = false;
		while (!isTokenType(QueryTokenType.EOF)) {

			// Should be an attribute. Parse it.
			checkToken(QueryTokenType.ATTRIBUTE);

			String attribute = getCurrentTokenValue();

			// Is it a valid attribute?
			if (!validAttrs.containsKey(attribute)) {
				parserException("Invalid attribute: " + attribute + "!");
			}

			IFieldValueGetter valueGetter = validAttrs.get(attribute);

			nextToken();

			// Should be an operator. Parse it.
			checkToken(QueryTokenType.OPERATOR);

			String operatorValue = getCurrentTokenValue();

			IComparisonOperator operator = operators.get(operatorValue);

			nextToken();

			// Should be a string literal. Parse it.
			checkToken(QueryTokenType.STRING);

			String stringLiteral = getCurrentTokenValue();

			// If the operator is "LIKE" then we check if the number of wildcards (*) is
			// valid (one)
			if (operatorValue.equals("LIKE") && !hasValidWildcardCount()) {
				parserException("Invalid number of wildcards in " + stringLiteral + "!");
			}

			nextToken();

			// Should be "and" or end of file
			if (!isTokenType(QueryTokenType.AND) && !isTokenType(QueryTokenType.EOF)) {
				parserException();
			}

			// Skip "and"
			if (isTokenType(QueryTokenType.AND)) {
				nextToken();
				multipleAttributes = true;
			}

			// If the given query has only a single attribute
			if (!multipleAttributes) {

				// Attribute must be jmbag
				if (!attribute.equals("jmbag") ) {
					parserException("Single command query must only be on jmbag!");
				}

				// Checks if it's a direct query
				if (operatorValue.equals("=") ) {
					isDirectQuery = true;
					directQueryJmbag = stringLiteral;
				}

			}

			expressionsList.add(new ConditionalExpression(valueGetter, stringLiteral, operator));
		}
	}

	/**
	 * Checks if the number of wildcards (*) is valid (one)
	 * 
	 * @return {@code true} if the number of wildcards (*) is valid (one),
	 *         {@code false} otherwise
	 */
	private boolean hasValidWildcardCount() {
		String stringLiteral = getCurrentTokenValue();
		boolean hasWildcard = false;

		for (int i = 0; i < stringLiteral.length(); i++) {
			if (stringLiteral.charAt(i) == '*') {
				// If it already has one
				if (hasWildcard)
					return false;

				hasWildcard = true;
			}
		}
		return true;
	}

	/**
	 * Moves lexer to the next token
	 * 
	 * @throws QueryParserException on parse error
	 */
	private void nextToken() {
		try {
			lexer.nextQueryToken();
		} catch (QueryLexerException e) {
			throw new QueryParserException(e.getMessage());
		}
	}

	/**
	 * Checks if the current token is of the given {@code type}
	 * 
	 * @param type type that is compared with current tokens type
	 * @return {@code true} if current token is of the given {@code type},
	 *         {@code false} if it's not
	 * @throws SmartScriptParserException if can't parse because of lexer error
	 */
	private boolean isTokenType(QueryTokenType type) {
		if (lexer == null || lexer.getToken() == null) {
			parserException("Problem with lexer");
		}
		return lexer.getToken().getType() == type;
	}

	/**
	 * Checks if the the current token is what we need and throws exception if it's
	 * not.
	 * 
	 * @param type type of token that the current one must be
	 * 
	 * @throws QueryParserException if the type of the token is not the one that is
	 *                              needed
	 */
	private void checkToken(QueryTokenType type) {
		if (!isTokenType(type)) {
			parserException();
		}
	}

	/**
	 * Returns the value of the current token.
	 * 
	 * @return value of the current token.
	 */
	private String getCurrentTokenValue() {
		return lexer.getToken().getValue();
	}

	/**
	 * Throws parser exception with appropriate message
	 * 
	 * @param message message to be show
	 * @throws QueryParserException always
	 */
	private void parserException(String message) {
		throw new QueryParserException(message);
	}

	/**
	 * Throws parser exception with appropriate message
	 * 
	 * @throws QueryParserException always
	 */
	private void parserException() {
		parserException("Invalid token: " + getCurrentTokenValue());
	}

	/**
	 * Method must return true if query has only one comparison, on attribute jmbag,
	 * and operator must be equals.
	 * 
	 * @return {@code true} if query has only one comparison, on attribute jmbag,
	 *         and operator must be equals, {@code false} otherwise
	 */
	public boolean isDirectQuery() {
		return isDirectQuery;

	}

	/**
	 * Returns the string which was given in equality comparison in direct query.
	 * 
	 * @return the string which was given in equality comparison in direct query
	 * @throws IllegalStateException if the query was not a direct one
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery) {
			throw new IllegalStateException("Query was not a direct one");
		}

		return directQueryJmbag;

	}

	/**
	 * Return a list of conditional expressions from query.
	 * 
	 * @return a list of conditional expressions from query
	 */
	public List<ConditionalExpression> getQuery() {
		return expressionsList;

	}
}
