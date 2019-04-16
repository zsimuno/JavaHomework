/**
 * 
 */
package hr.fer.zemris.java.hw06.shell;

/**
 * Class represents shell input/output exception.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code ShellIOException} with no detail message.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Constructs a new {@code ShellIOException} with the specified detail message.
	 * 
	 * @param message the detail message.
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code ShellIOException} with the specified cause.
	 * 
	 * @param cause the cause
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code ShellIOException} with the specified detail message
	 * and a specified cause.
	 * 
	 * @param message the detail message.
	 * @param cause   the cause
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

}
