/**
 * 
 */
package hr.fer.zemris.java.hw06.crypto;

import java.util.List;
import java.util.Arrays;

/**
 * Utility class we use for converting hexadecimal to byte and vice-versa
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Util {

	/**
	 * COntains valid hexadecimal numbers
	 */
	private static List<Character> validHexChars;

	static {
		validHexChars = Arrays
				.asList(new Character[] { 
						'0','1', '2', '3', '4', '5', '6', '7', '8', '9',
						'a', 'b', 'c', 'd', 'e', 'f' });
	}

	/**
	 * Converts a string representation of a hexadecimal number in to a byte array.
	 * 
	 * @param keyText string representation of a hexadecimal number
	 * @return a byte array
	 * @throws IllegalArgumentException if the string is not valid
	 */
	public static byte[] hextobyte(String keyText) {

		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Hex string is odd-sized!");
		}

		byte[] result = new byte[keyText.length() / 2];

		for (int i = 0; i < keyText.length(); i += 2) {
			Character c1 = Character.toLowerCase(keyText.charAt(i));
			Character c2 = Character.toLowerCase(keyText.charAt(i + 1));

			if (!validHexChars.contains(c1) || !validHexChars.contains(c2)) {
				throw new IllegalArgumentException("Invalid character!");
			}

			result[i / 2] = (byte) (16 * convertCharacter(c1) + convertCharacter(c2));

		}

		return result;

	}

	/**
	 * Converts the hex character to {@code int}
	 * 
	 * @return character converted to {@code int}
	 */
	private static int convertCharacter(Character ch) {

		if (Character.isDigit(ch)) {
			return Integer.parseInt(ch.toString());
		}

		// Character from a-f
		return ch - 'a' + 10;

	}

	/**
	 * Converts a byte array in to a string representation of a hexadecimal number.
	 * 
	 * @param byteArray byte array of numbers
	 * @return string representation of a hexadecimal number
	 */
	public static String bytetohex(byte[] byteArray) {
		if (byteArray.length == 0)
			return "";
		
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			int current = (byteArray[i] < 0) ? 256 + byteArray[i] : byteArray[i];
			
			 int second = current % 16;
			 current /= 16;
			 
			 result.append(toHex(current % 16));
			 result.append(toHex(second));
		}
		
		return result.toString();

	}
	
	/**
	 * Converts integer to hexadecimal string
	 * 
	 * @param number integer to be converted
	 * @return hexadecimal string
	 */
	private static String toHex(int number) {
		if(number > 9 ) {
			return Character.toString('a' + number - 10 );
		} 
		
		return Integer.toString(number);
			
	}
}
