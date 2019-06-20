package hr.fer.zemris.java.tecaj_13.web.servlets.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for creating password hash.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class HashUtil {

	/**
	 * Creates a SHA-1 hash from the given {@code password}.
	 * 
	 * @param password password that needs to be hashed.
	 * @return hashed password.
	 */
	public static String createHash(String password) {
		MessageDigest sha = null;

		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] hash = sha.digest(password.getBytes());
		return HexUtil.bytetohex(hash);
	}
}
