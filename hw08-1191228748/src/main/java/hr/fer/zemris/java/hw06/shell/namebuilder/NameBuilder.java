package hr.fer.zemris.java.hw06.shell.namebuilder;

import java.util.Objects;

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

	/**
	 * Returns a composed {@code NameBuilder} that executes this followed by the
	 * execution of {@code other}.
	 * 
	 * @param other the builder to execute after this one.
	 * @return composed {@code NameBuilder} that executes this followed by the
	 *         execution of {@code other}.
	 * @throws NullPointerException if {@code after} is null
	 */
	default NameBuilder then(NameBuilder other) {
		Objects.requireNonNull(other);
		return (result, sb) -> {
			this.execute(result, sb);
			other.execute(result, sb);
		};
	}

}
