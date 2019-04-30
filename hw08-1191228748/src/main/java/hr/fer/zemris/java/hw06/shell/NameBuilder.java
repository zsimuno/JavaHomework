package hr.fer.zemris.java.hw06.shell;

/**
 * Interface that represents objects that we use i massername command so we can
 * build new names from given user input.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface NameBuilder {

	/**
	 * Executes this {@code NameBuilder} object. Executing appends needed strings to
	 * the given {@code StringBuilder}
	 * 
	 * @param result result of the filtering with the mask.
	 * @param sb     string builder that we append the result of the execute command
	 *               to.
	 */
	void execute(FilterResult result, StringBuilder sb);

	// TODO NameBuilder nb = text("gradovi-").group(2).text("-").group(1, '0', 3); ????
}
