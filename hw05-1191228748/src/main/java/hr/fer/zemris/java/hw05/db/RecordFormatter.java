/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Formats student records so they can be printed on screen.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class RecordFormatter {

	/**
	 * Formats student records so they can be printed on screen.
	 * 
	 * @param records list of student records that need to be formatted
	 * @return list of formatted student records
	 */
	public static List<String> format(List<StudentRecord> records) {
		ArrayList<String> formattedList = new ArrayList<String>();

		if (records.isEmpty()) {

			formattedList.add("Records selected: 0");
			return formattedList;
		}

		// Column width without the starting and ending space
		int lastNameWidth = 0, firstNameWidth = 0, jmbagWidth = 10, gradeWidth = 1;

		// Calculate widths of the columns
		for (StudentRecord studentRecord : records) {
			int currentLastName = studentRecord.getLastName().length();
			if (lastNameWidth < currentLastName) {
				lastNameWidth = currentLastName;
			}

			int currentFirstName = studentRecord.getFirstName().length();
			if (firstNameWidth < currentFirstName) {
				firstNameWidth = currentFirstName;
			}
		}

		String equals = "=";

		// Format top row
		StringBuilder topRow = new StringBuilder();
		topRow.append("+" + equals.repeat(jmbagWidth + 2));
		topRow.append("+" + equals.repeat(lastNameWidth + 2));
		topRow.append("+" + equals.repeat(firstNameWidth + 2));
		topRow.append("+" + equals.repeat(gradeWidth + 2) + "+");
		formattedList.add(topRow.toString());

		String space = " ";

		// Formating record by record
		for (StudentRecord studentRecord : records) {

			String jmbag = studentRecord.getJmbag();
			String lastName = studentRecord.getLastName();
			String firstName = studentRecord.getFirstName();
			String finalGrade = studentRecord.getFinalGrade();

			StringBuilder row = new StringBuilder();

			// adding spaces to jmbag and grade if we decide to change to formatting later.
			row.append("| " + jmbag + space.repeat(jmbagWidth - jmbag.length()));
			row.append(" | " + lastName + space.repeat(lastNameWidth - lastName.length()));
			row.append(" | " + firstName + space.repeat(firstNameWidth - firstName.length()));
			row.append(" | " + finalGrade + space.repeat(gradeWidth - finalGrade.length()) + " |");

			formattedList.add(row.toString());
		}

		// Top row and bottom one are the same
		formattedList.add(topRow.toString());
		formattedList.add("Records selected: " + records.size());

		return formattedList;
	}

}
