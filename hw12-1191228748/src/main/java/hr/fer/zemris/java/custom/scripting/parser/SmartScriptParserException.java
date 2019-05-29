/**
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception that is thrown if an exception occurs during parsing.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code SmartScriptParserException} with no detail message.
	 */
	public SmartScriptParserException() {
	}

	/**
	 * Constructs an {@code SmartScriptParserException} with the specified detail message.
	 *
	 * @param s the detail message.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code SmartScriptParserException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code SmartScriptParserException} with the specified detail message
	 * and cause.
	 *
	 *
	 * @param message the detail message.
	 * @param cause   the cause (A {@code null} value is permitted, and indicates
	 *                that the cause is nonexistent or unknown.)
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
