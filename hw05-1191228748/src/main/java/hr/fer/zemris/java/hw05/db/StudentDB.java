/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

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
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Coudn't read the file!");
			return;
		}

		StudentDatabase studentDB;
		try {
			studentDB = new StudentDatabase((String[]) lines.toArray());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		Scanner sc = new Scanner(System.in);

		// Read user queries until he inputs "exit"
		while (true) {
			String input = sc.nextLine();

			if (input.equals("exit"))
				return;
			
			if(!input.substring(0, 5).equals("query")) {
				System.out.println("Invalid query input!");
				continue;
			}
			
			String query = input.substring(5);
			
			// TODO what on empty query?
			
			QueryParser parser;

			try {
				parser = new QueryParser(query);
			} catch (QueryParserException e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			
			if (parser.isDirectQuery()) {
				StudentRecord r = studentDB.forJMBAG(parser.getQueriedJMBAG());
			} else {
				for (StudentRecord r : studentDB.filter(new QueryFilter(parser.getQuery()))) {
				}
			}
		}

	}

}
