package hr.fer.zemris.java.hw06.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for {@link NameBuilder} objects. Generates objects from given
 * expression.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class NameBuilderParser {

	/**
	 * Expression used in a command.
	 */
	private String expression;

	/**
	 * Current index in the expression.
	 */
	private int currentIndex = 0;

	/**
	 * Last name builder that contains all name builders made from the given
	 * expression.
	 */
	private NameBuilder nameBuilder;

	/**
	 * Constructs new {@link NameBuilderParser} from given expression and parses it.
	 * 
	 * @param expression expression to be parsed in a {@link NameBuilder} object.
	 */
	public NameBuilderParser(String expression) {
		this.expression = expression;

		List<NameBuilder> list = new ArrayList<>();

		int strLength = expression.length();

		while (currentIndex < strLength) {

			if (currentIndex < strLength - 2 && expression.charAt(currentIndex) == '$'
					&& expression.charAt(currentIndex + 1) == '{') {
				list.add(parseGroup());
			} else {
				list.add(parseText());
			}
		}

		nameBuilder = execute(list);
	}

	/**
	 * Returns the {@link NameBuilder} object parsed from the expression given in
	 * the constructor.
	 * 
	 * @return the {@link NameBuilder} object parsed from the expression given in
	 *         the constructor.
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;

	}

	/**
	 * Parses a textual {@code NameBuilder} object.
	 * 
	 * @return a textual {@code NameBuilder} object.
	 */
	private NameBuilder parseText() {
		int stringBegin = currentIndex;
		int strLength = expression.length();
		while (currentIndex < strLength) {
			currentIndex++;
			if (currentIndex < strLength - 2 && expression.charAt(currentIndex) == '$'
					&& expression.charAt(currentIndex + 1) == '{') {
				break;
			}

		}

		return text(expression.substring(stringBegin, currentIndex));

	}

	/**
	 * Parses a grouping {@code NameBuilder} object.
	 * 
	 * @return a grouping {@code NameBuilder} object.
	 * @throws NameBuilderParserException if there is an error while parsing.
	 */
	private NameBuilder parseGroup() {

		currentIndex += 2; // Skip '$' and '{'
		int exprBegin = currentIndex;
		int strLength = expression.length();
		while (currentIndex < strLength && expression.charAt(currentIndex) != '}') {
			currentIndex++;
		}

		// Ended without a closing tag
		if (currentIndex == strLength) {
			throw new NameBuilderParserException("Invalid expression");
		}

		String supstitutionCmd = expression.substring(exprBegin, currentIndex).trim();

		currentIndex++; // Skip the closing '}' tag

		if (!supstitutionCmd.contains(",")) { // command is just one number, the index
			try {
				return group(Integer.parseInt(supstitutionCmd));

			} catch (Exception e) {
				throw new NameBuilderParserException(e.getMessage());
			}
		}

		// Command had index, padding and min width
		String[] parts = supstitutionCmd.split(",");

		if (parts.length != 2) {
			throw new NameBuilderParserException("Supstitution command can have 1 or two arguments!");
		}

		String pad = parts[1].trim();

		if (pad.length() != 1 && pad.length() != 2) {
			throw new NameBuilderParserException("Invalid supstitution command length argument!");
		}

		return group(getIndex(parts[0]), getPadding(pad), getMinWidth(pad));
	}

	/**
	 * Returns the required index of the group from the string.
	 * 
	 * @param part part of the command that stores the index.
	 * @return the required index of the group from the string.
	 */
	private int getIndex(String part) {
		try {
			return Integer.parseInt(part.trim());
		} catch (Exception e) {
			throw new NameBuilderParserException(e.getMessage());
		}
	}

	/**
	 * Returns the character that will be used as padding in new name of the file
	 * from the second part of the command. Padding will be one space (i.e. ' ') if
	 * element is not provided or zero (Only valid character that can be provided).
	 * 
	 * @param pad part of the command that stores the padding character.
	 * @return the character that will be used as padding in new name of the file..
	 */
	private char getPadding(String pad) {
		char padding = pad.length() == 1 ? ' ' : pad.charAt(0);
		if (padding != '0' && padding != ' ') {
			throw new NameBuilderParserException("Invalid supstitution command length argument (invalid argument)!");
		}
		return padding;
	}

	/**
	 * Returns the minimal width of the selected group. Part of the name will be
	 * padded with the padding character to be of minimal width.
	 * 
	 * @param pad part of the command that stores the minimal width.
	 * @return the minimal width of the selected group
	 */
	private int getMinWidth(String pad) {
		try {
			return Integer.parseInt(
					pad.length() == 1 ? Character.toString(pad.charAt(0)) : Character.toString(pad.charAt(1)));

		} catch (Exception e) {
			throw new NameBuilderParserException(e.getMessage());
		}
	}

	/**
	 * {@code NameBuilder} that appends the given text to the {@link StringBuilder}
	 * object.
	 * 
	 * @param t text to be appended.
	 * @return {@code NameBuilder} that appends the given text to the
	 *         {@link StringBuilder} object.
	 */
	private static NameBuilder text(String t) {
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
	private static NameBuilder group(int index) {
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
	private static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> {
			String group = result.group(index);
			String pad = Character.toString(padding).repeat(minWidth - group.length());
			sb.append(pad + group);
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
	private static NameBuilder execute(List<NameBuilder> list) {
		return (result, sb) -> {
			list.forEach((nb) -> nb.execute(result, sb));
		};
	}
}
