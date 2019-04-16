/**
 * 
 */
package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Represents a shell environment.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface Environment {
	/**
	 * Reads the line from the user.
	 * 
	 * @return line that was read.
	 * @throws ShellIOException if reading fails.
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes given text to the user.
	 * 
	 * @param text text to be written to the user.
	 * @throws ShellIOException if writing fails.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes given text in a line to the user.
	 * 
	 * @param text text to be written to the user.
	 * @throws ShellIOException if writing fails.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns unmodifiable map of all available commands.
	 * 
	 * @return unmodifiable map of all available commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Gets the multiple line symbol.
	 * 
	 * @return multiple line symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiple line symbol.
	 * 
	 * @param symbol new multiple line symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets the prompt symbol.
	 * 
	 * @return prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol.
	 * 
	 * @param symbol new prompt symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Gets the more lines symbol.
	 * 
	 * @return more lines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the more lines symbol.
	 * 
	 * @param symbol new more lines symbol.
	 */
	void setMorelinesSymbol(Character symbol);
}
