/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.List;

import hr.fer.zemris.java.hw05.db.querylexer.*;

/**
 * TODO Javadoc queryparser
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
		while (!isTokenType(QueryTokenType.EOF)) {

			if(!isTokenType(QueryTokenType.ATTRIBUTE)) {
				parserException();
			}
			
			nextToken();
			if(!isTokenType(QueryTokenType.OPERATOR)) {
				parserException();
			}
			
			
			nextToken();
			if(!isTokenType(QueryTokenType.STRING)) {
				parserException();
			}
			
			nextToken();
			if(!isTokenType(QueryTokenType.AND) || !isTokenType(QueryTokenType.EOF)) {
				parserException();
			}
			
			// Skip "and"
			if(isTokenType(QueryTokenType.AND)) {
				nextToken();
			}

		}
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
	 * Method must return true if query has only one comparison, on attribute jmbag,
	 * and operator must be equals.
	 * 
	 * @return {@code true} if query has only one comparison, on attribute jmbag,
	 *         and operator must be equals, {@code false} otherwise
	 */
	public boolean isDirectQuery() {
		return false; // TODO

	}

	/**
	 * Returns the string which was given in equality comparison in direct query.
	 * 
	 * @return the string which was given in equality comparison in direct query
	 * @throws IllegalStateException if the query was not a direct one
	 */
	public String getQueriedJMBAG() {
		return null; // TODO

		
//		if( query not direct) {
//			throw new IllegalStateException("Query was not a direct one");
//		}
	}
	
	/**
	 * Return a list of conditional expressions from query.
	 * 
	 * @return a list of conditional expressions from query
	 */
	public List<ConditionalExpression> getQuery() {
		return null; // TODO
		
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
	 * Returns the value of the current token.
	 * 
	 * @return value of the current token.
	 */
	private Object currentTokenValue() {
		return lexer.getToken().getValue();
	}

	/**
	 * Returns the type of the current token.
	 * 
	 * @return type of the current token.
	 */
	private QueryTokenType currentTokenType() {
		return lexer.getToken().getType();
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
		parserException("Invalid token: " + currentTokenValue());
	}
}
