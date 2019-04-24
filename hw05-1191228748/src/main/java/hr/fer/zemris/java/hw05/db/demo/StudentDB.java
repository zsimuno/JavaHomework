/**
 * 
 */
package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.hw05.db.*;

/**
 * Program that reads the data from database.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StudentDB {

	/**
	 * Database of student records.
	 */
	private static StudentDatabase studentDB;

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
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Coudn't read the file!");
			return;
		}

		try {
			studentDB = new StudentDatabase(lines);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		Scanner sc = new Scanner(System.in);

		// Read user queries until he inputs "exit"
		while (true) {

			System.out.printf("> ");

			String input = sc.nextLine();

			if (input.equals("exit"))
				break;

			if (!input.substring(0, 5).equals("query")) {
				System.out.println("Invalid query input!");
				continue;
			}

			String query = input.substring(5);

			QueryParser parser;

			try {
				parser = new QueryParser(query);
			} catch (QueryParserException e) {
				System.out.println(e.getMessage());
				continue;
			}
			
			List<StudentRecord> records = getFromDatabase(parser);
			List<String> output = RecordFormatter.format(records);
			if(parser.isDirectQuery()) {
				output.add(0, "Using index for record retrieval.");
			}
			output.forEach(System.out::println);

		}
		
		System.out.println("Goodbye!");
		
		sc.close();

	}

	/**
	 * Returns list of student records from database based on filtering condition in
	 * the query that was parsed with the given parser.
	 * 
	 * @param parser parser that parsed the query
	 * @return list of student records based on filtering conditions.
	 */
	private static List<StudentRecord> getFromDatabase(QueryParser parser) {
		if (parser.isDirectQuery()) {
			StudentRecord r = studentDB.forJMBAG(parser.getQueriedJMBAG());
			ArrayList<StudentRecord> list = new ArrayList<>();
			if(r != null) {
				list.add(r);
			}
			return list;
			
		} else {
			return studentDB.filter(new QueryFilter(parser.getQuery()));
			
		}
	}

}
