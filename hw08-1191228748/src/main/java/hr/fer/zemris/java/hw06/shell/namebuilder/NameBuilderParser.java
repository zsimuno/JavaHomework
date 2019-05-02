package hr.fer.zemris.java.hw06.shell.namebuilder;

import java.util.ArrayList;
import java.util.List;
import static hr.fer.zemris.java.hw06.shell.namebuilder.DefaultNameBuilders.*;

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
				throw new NameBuilderParserException("Error while parsing index: " + e.getMessage());
			}
		}

		// Command had index, padding and min width
		String[] parts = supstitutionCmd.split(",");

		if (parts.length != 2) {
			throw new NameBuilderParserException("Supstitution command can have 1 or two arguments!");
		}

		String pad = parts[1].trim();

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
			throw new NameBuilderParserException("Error while parsing index: " + e.getMessage());
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
		if (pad.charAt(0) == '0' && pad.length() != 1)
			return '0';
		return ' ';
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
			if (pad.charAt(0) == '0' && pad.length() != 1)
				return Integer.parseInt(pad.substring(1));
			return Integer.parseInt(pad);

		} catch (Exception e) {
			throw new NameBuilderParserException("Error while parsing minimal width: " + e.getMessage());
		}
	}

}
