/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class that is called when there is a lexer exception
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code SmartScriptLexerException} with no detail message.
	 */
	public SmartScriptLexerException() {
	}

	/**
	 * Constructs an {@code SmartScriptLexerException} with the specified detail
	 * message.
	 *
	 * @param s the detail message.
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code SmartScriptLexerException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code SmartScriptLexerException} with the specified detail
	 * message and cause.
	 *
	 *
	 * @param message the detail message.
	 * @param cause   the cause (A {@code null} value is permitted, and indicates
	 *                that the cause is nonexistent or unknown.)
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
