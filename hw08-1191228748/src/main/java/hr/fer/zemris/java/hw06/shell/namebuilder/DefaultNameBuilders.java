package hr.fer.zemris.java.hw06.shell.namebuilder;

import java.util.List;

/**
 * Implements default name builders.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DefaultNameBuilders {
	/**
	 * {@code NameBuilder} that appends the given text to the {@link StringBuilder}
	 * object.
	 * 
	 * @param t text to be appended.
	 * @return {@code NameBuilder} that appends the given text to the
	 *         {@link StringBuilder} object.
	 */
	public static NameBuilder text(String t) {
		return (result, sb) -> {
			sb.append(t);
		};
	}

	/**
	 * {@code NameBuilder} that returns the group with the given index from the
	 * filter result.
	 * 
	 * @param index index of the group.
	 * @return {@code NameBuilder} that returns the group with the given index from
	 *         the filter result.
	 */
	public static NameBuilder group(int index) {
		return (result, sb) -> {
			sb.append(result.group(index));
		};
	}

	/**
	 * {@code NameBuilder} that returns the group with the given index from the
	 * filter result. Padds the result with the padding so that in has the given
	 * minimum width.
	 * 
	 * @param index    index of the group.
	 * @param padding  padding that is to be added.
	 * @param minWidth minimum width of the result.
	 * @return {@code NameBuilder} that returns the group with the given index from
	 *         the filter result and padded with the given padding.
	 */
	public static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> {
			String group = result.group(index);
			if (group.length() >= minWidth) {
				sb.append(group);
			} else {
				String pad = Character.toString(padding).repeat(minWidth - group.length());
				sb.append(pad + group);
			}

		};
	}

	/**
	 * {@code NameBuilder} that executes every {@code NameBuilder} in the given
	 * list.
	 * 
	 * @param list list of {@code NameBuilder} objects that are parsed from the
	 *             expression in this parser.
	 * @return {@code NameBuilder} object that executes every {@code NameBuilder} in
	 *         the given list.
	 */
	public static NameBuilder execute(List<NameBuilder> list) {
		return (result, sb) -> {
			list.forEach((nb) -> nb.execute(result, sb));
		};
	}
}
