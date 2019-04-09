/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

/**
 * Class that is called when there is a lexer exception
 * 
 * @author Zvonimir Šimunović
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code QueryLexerException} with no detail message.
	 */
	public QueryParserException() {
	}

	/**
	 * Constructs an {@code QueryLexerException} with the specified detail
	 * message.
	 *
	 * @param s the detail message.
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code QueryLexerException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code QueryLexerException} with the specified detail
	 * message and cause.
	 *
	 *
	 * @param message the detail message.
	 * @param cause   the cause (A {@code null} value is permitted, and indicates
	 *                that the cause is nonexistent or unknown.)
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
