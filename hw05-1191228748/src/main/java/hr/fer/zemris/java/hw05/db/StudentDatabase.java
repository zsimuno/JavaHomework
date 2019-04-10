/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the database of student records (object {@link StudentRecord} )
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StudentDatabase {

	/**
	 * Database of records
	 */
	private HashMap<String, StudentRecord> database = new HashMap<>();

	/**
	 * List of student records
	 */
	private ArrayList<StudentRecord> studentRecords = new ArrayList<>();
	
	/**
	 * Contains all valid grades
	 */
	private static ArrayList<String> grades;
	static {
		grades = new ArrayList<>();
		grades.add("1");
		grades.add("2");
		grades.add("3");
		grades.add("4");
		grades.add("5");
	}

	/**
	 * Constructs a {@link StudentDatabase} object with given lines where every line
	 * is one student record.
	 * 
	 * @param lines lines where every line is one student record
	 * @throws IllegalArgumentException if the input database is invalid
	 */
	public StudentDatabase(String[] lines) {
		
		
		
		for (String lineString : lines) {
			String[] line = lineString.split("\\s+");

			if (line.length != 4) {
				throw new IllegalArgumentException("Invalid input from file! Input: " + String.join(" ", line));
			}

			if (database.containsKey(line[0])) {
				throw new IllegalArgumentException("There are duplicate JMBAGs!");
			}

			if (!grades.contains(line[3])) {
				throw new IllegalArgumentException("Invalid grade input " + line[3] + "!");
			}
			

			StudentRecord record = new StudentRecord(line[0], line[1], line[2], line[3]);

			studentRecords.add(record);
			database.put(line[0], record);
			
			
		}

	}

	/**
	 * Uses {@code jmbag} to obtain a requested record in O(1) complexity.
	 * 
	 * @param jmbag JMBAG of the student
	 * @return requested student record or {@code null} if record doesn't exist
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return database.get(jmbag);
	}

	/**
	 * Filters trough all student records in the internal list of records. Uses
	 * given {@code filter}.
	 * 
	 * @param filter filter which we use for filtering the list of records
	 * @return new list of records filtered so it only has requested records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		ArrayList<StudentRecord> records = new ArrayList<>();

		for (StudentRecord studentRecord : studentRecords) {
			if (filter.accepts(studentRecord)) {
				records.add(studentRecord);
			}
		}

		return records;

	}

}
