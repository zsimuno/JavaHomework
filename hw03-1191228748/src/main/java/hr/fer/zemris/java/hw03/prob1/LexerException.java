/**
 * 
 */
package hr.fer.zemris.java.hw03.prob1;

/**
 * Class that is called when there is a lexer exception
 * 
 * @author Zvonimir Šimunović
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code LexerException} with no detail message.
	 */
	public LexerException() {
	}

	/**
	 * Constructs an {@code LexerException} with the specified detail message.
	 *
	 * @param s the detail message.
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code LexerException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code LexerException} with the specified detail message
	 * and cause.
	 *
	 *
	 * @param message the detail message.
	 * @param cause   the cause (A {@code null} value is permitted, and indicates
	 *                that the cause is nonexistent or unknown.)
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	

}
