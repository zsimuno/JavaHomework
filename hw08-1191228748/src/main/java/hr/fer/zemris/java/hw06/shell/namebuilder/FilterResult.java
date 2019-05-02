package hr.fer.zemris.java.hw06.shell.namebuilder;

import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * 
 * Represents a result of filtering files with regex pattern.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class FilterResult {

	/**
	 * Filtered file.
	 */
	private Path file;

	/**
	 * Matcher of the pattern and this file name.
	 */
	private Matcher matcher;

	/**
	 * Constructs a new filter result from the file that is the result of filtering
	 * with the given pattern and getting a matcher.
	 * 
	 * @param file    filtered file.
	 * @param pattern matcher for the file and the pattern.
	 */
	public FilterResult(Path file, Matcher matcher) {
		this.file = file;
		this.matcher = matcher;
	}

	/**
	 * Returns the number of found groups.
	 * 
	 * @return the number of found groups.
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}

	/**
	 * Returns the selected group.
	 * 
	 * @param index index of the group (0 <= index <= numberOfGroups())
	 * @return the selected group.
	 */
	public String group(int index) {
		return matcher.group(index);
	}

	@Override
	public String toString() {
		return file.getFileName().toString();
	}

	/**
	 * Returns the filtered file.
	 * 
	 * @return the filtered file.
	 */
	public Path getFile() {
		return file;
	}

}
