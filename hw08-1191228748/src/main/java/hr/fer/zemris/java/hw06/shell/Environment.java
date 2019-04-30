/**
 * 
 */
package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
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

	/**
	 * Gets the current directory the environment is in.
	 * 
	 * @return the current directory the environment is in as a Path
	 */
	Path getCurrentDirectory();

	/**
	 * Sets the current directory the environment is in.
	 * 
	 * @param path the new directory to set the environment in.
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Returns shared data in the environment.
	 * 
	 * @param key key of the data we want to get.
	 * @return Object in the shared data with the given key.
	 */
	Object getSharedData(String key);

	/**
	 * Sets shared data in the environment.
	 * 
	 * @param key   key of the data we want to set.
	 * @param value Object put in the shared data with the given key.
	 */
	void setSharedData(String key, Object value);
}
