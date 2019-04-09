/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Program that reads the data from database.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StudentDB {
	
	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		List<String> lines;
		try {
			lines = Files.readAllLines(
					Paths.get("./database.txt"),
					StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("Coudn't read the file!");
			return;
		}
	}

}
