package hr.fer.zemris.java.gui.layouts;

/**
 * Exception that is thrown if there is a problem with adding objects to the
 * layout.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class CalcLayoutException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code CalcLayoutException} with no detail message.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructs a new {@code CalcLayoutException} with the specified detail
	 * message.
	 * 
	 * @param message the detail message.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code CalcLayoutException} with the specified cause.
	 * 
	 * @param cause the cause
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code CalcLayoutException} with the specified detail
	 * message and a specified cause.
	 * 
	 * @param message the detail message.
	 * @param cause   the cause
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}
}
