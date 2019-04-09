/**
 * 
 */
package hr.fer.zemris.java.hw05.db.querylexer;

/**
 * Class that is called when there is a lexer exception
 * 
 * @author Zvonimir Šimunović
 *
 */
public class QueryLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code QueryLexerException} with no detail message.
	 */
	public QueryLexerException() {
	}

	/**
	 * Constructs an {@code QueryLexerException} with the specified detail
	 * message.
	 *
	 * @param s the detail message.
	 */
	public QueryLexerException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code QueryLexerException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public QueryLexerException(Throwable cause) {
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
	public QueryLexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
