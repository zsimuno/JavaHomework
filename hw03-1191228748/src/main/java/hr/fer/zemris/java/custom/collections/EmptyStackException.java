/**
 * 
 */
package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that a method has been called on an empty stack.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an {@code EmptyStackException} with no detail message.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructs an {@code EmptyStackException} with the specified detail message.
	 *
	 * @param s the detail message.
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code EmptyStackException} with the specified cause
	 *
	 * @param cause the cause (A {@code null} value is permitted, and indicates that
	 *              the cause is nonexistent or unknown.)
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs an {@code EmptyStackException} with the specified detail message
	 * and cause.
	 *
	 *
	 * @param message the detail message.
	 * @param cause   the cause (A {@code null} value is permitted, and indicates
	 *                that the cause is nonexistent or unknown.)
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
