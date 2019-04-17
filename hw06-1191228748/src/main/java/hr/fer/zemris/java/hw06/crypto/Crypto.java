/**
 * 
 */
package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Represents a program that will allow the user to encrypt/decrypt given file
 * using the AES crypto-algorithm and the 128-bit encryption key or calculate
 * and check the SHA-256 file digest.We input type of job to do and necessary
 * arguments to do it over the command line arguments.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Crypto {

	/**
	 * Says whether we encrypt or decrypt
	 */
	private static boolean encrypt;

	/**
	 * Main method that starts the program.
	 * 
	 * @param args command line arguments. We input type of job to do and necessary
	 *             arguments to do it.
	 */
	public static void main(String[] args) {

		if (args.length != 2 && args.length != 3) {
			System.out.println("Wrong number of command line arguments!");
			return;
		}

		switch (args[0]) {
		// Do message digest
		case "checksha":
			checkCmdArgs(2, args);
			checksha(args[1]);
			break;

		// Do encrypting or decrypting
		case "encrypt":
		case "decrypt":
			checkCmdArgs(3, args);
			encrypt = args[0].equals("encrypt");

			crypto(args[1], args[2]);

			break;

		default:
			System.out.println("No such command is supported!");
			return;
		}

	}

	/**
	 * Calculates and checks the SHA-256 file digest of the given file.
	 * 
	 * @param filePath path of the file which we calculate the digest of
	 * @param sc       scanner used to input user data
	 */
	private static void checksha(String filePath) {
		Scanner sc = new Scanner(System.in);
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
		String expectedDigest = sc.nextLine();

		sc.close();

		MessageDigest sha = null;

		// Get the digest
		try {
			sha = MessageDigest.getInstance("SHA-256");

		} catch (NoSuchAlgorithmException e) {
			System.out.println("Exception while obtaining message digest: " + e.getMessage());
			System.exit(1);
		}

		Path p = Paths.get(filePath);

		// Open the file input stream
		try (InputStream is = Files.newInputStream(p)) {
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r == -1)
					break;

				// Update digest
				sha.update(buff, 0, r);
			}
		} catch (IOException e) {
			System.out.println("Exception while opening the file: " + e.getMessage());
			sc.close();
			System.exit(1);

		}

		// Return the digest and turn to hex string
		byte[] hash = sha.digest();
		String actualDigest = Util.bytetohex(hash);

		// Compare digests
		if (expectedDigest.equals(actualDigest)) {
			System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");

		} else {
			System.out.println("Digesting completed. Digest of " + filePath
					+ " does not match the expected digest. Digest was: " + actualDigest);

		}

	}

	/**
	 * Does encrypt/decrypt of the given file using the AES crypto-algorithm and the
	 * 128-bit encryption key
	 * 
	 * @param string  input file path
	 * @param string2 output file path
	 */
	private static void crypto(String filePath1, String filePath2) {

		// Retrieve the cipher
		Cipher cipher;
		try {
			cipher = obtainCipher();

		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			System.out.println("Exception while obtaining cypher: " + e.getMessage());
			return;
		}

		Path p = Paths.get(filePath1);
		Path p2 = Paths.get(filePath2);

		// Open the file input and output stream
		try (InputStream is = Files.newInputStream(p); OutputStream os = Files.newOutputStream(p2)) {
			byte[] buff = new byte[1024];
			
			while (true) {
				int r = is.read(buff);
				
				if (r == -1) {
					// Finishing the cipher
					try {
						os.write(cipher.doFinal());
					} catch (IllegalBlockSizeException | BadPaddingException e) {
						System.out.println("Exception while finalizaing cipher: " + e.getMessage());
						System.exit(1);
					}
					break;
				}
					
				// Update cipher and write into new file
				os.write(cipher.update(buff, 0, r));
			}
		} catch (IOException e) {
			System.out.println("Exception while opening the file: " + e.getMessage());
			System.exit(1);

		}


		System.out.println((encrypt ? "Encryption" : "Decryption") + " completed. Generated file " + filePath2
				+ " based on file " + filePath1 + ".");

	}

	/**
	 * Obtains the needed {@link Cipher} object that we need to encrypt/decrypt
	 * given file using the AES crypto-algorithm and the 128-bit encryption key
	 * 
	 * @param sc scanner used to input user data
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @return {@link Cipher} object that we need to encrypt/decrypt given file
	 *         using the AES crypto-algorithm and the 128-bit encryption key
	 */
	private static Cipher obtainCipher() throws InvalidKeyException, InvalidAlgorithmParameterException,
			NoSuchAlgorithmException, NoSuchPaddingException {

		Scanner sc = new Scanner(System.in);
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
		String keyText = sc.nextLine();
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
		String ivText = sc.nextLine();
		sc.close();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		return cipher;
	}

	/**
	 * Checks if there are requested number of command line arguments.
	 * 
	 * @param numberOfArgs number of arguments we need to have
	 * @param args         command line arguments
	 */
	private static void checkCmdArgs(int numberOfArgs, String[] args) {
		if (args.length != numberOfArgs) {
			System.out.println("Wrong number of command line arguments!");
			return;
		}

	}

}
