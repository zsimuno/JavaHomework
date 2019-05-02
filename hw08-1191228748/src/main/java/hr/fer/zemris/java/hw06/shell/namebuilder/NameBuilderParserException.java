package hr.fer.zemris.java.hw06.shell.namebuilder;

/**
 * Class that is called when there is a lexer exception
 * 
 * @author Zvonimir Šimunović
 *
 */
public class NameBuilderParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code NameBuilderParserException} with no detail message.
	 */
	public NameBuilderParserException() {
	}

	/**
	 * Constructs an {@code NameBuilderParserException} with the specified detail
	 * message.
	 *
	 * @param s the detail message.
	 */
	public NameBuilderParserException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code NameBuilderParserException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public NameBuilderParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code NameBuilderParserException} with the specified detail
	 * message and cause.
	 *
	 *
	 * @param message the detail message.
	 * @param cause   the cause (A {@code null} value is permitted, and indicates
	 *                that the cause is nonexistent or unknown.)
	 */
	public NameBuilderParserException(String message, Throwable cause) {
		super(message, cause);
	}

}