/**
 * 
 */
package hr.fer.zemris.java.tecaj_13.web.servlets.util;

/**
 * Utility class we use for converting byte to hexadecimal.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class HexUtil {

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
		if (number > 9) {
			return Character.toString('a' + number - 10);
		}

		return Integer.toString(number);

	}
}
