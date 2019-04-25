package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that demos student records and streams.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StudentDemo {

	/**
	 * Method that starts the program.
	 * 
	 * @param args command line arguments (not used here)
	 */
	public static void main(String[] args) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Cannot open file!");
			return;
		}

		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);

		printAssigment(1);
		System.out.println(broj);

		long broj5 = vratiBrojOdlikasa(records);

		printAssigment(2);
		System.out.println(broj5);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);

		printAssigment(3);
		odlikasi.forEach(System.out::println);

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);

		printAssigment(4);
		odlikasiSortirano.forEach(System.out::println);

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);

		printAssigment(5);
		nepolozeniJMBAGovi.forEach(System.out::println);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);

		printAssigment(6);
		mapaPoOcjenama.forEach((grade, list) -> {
			System.out.println(grade);
			list.forEach(System.out::println);
		});

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);

		printAssigment(7);
		mapaPoOcjenama2.forEach((grade, numberOfStudents) -> {
			System.out.println(grade + ": " + numberOfStudents);
		});

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

		printAssigment(8);
		prolazNeprolaz.forEach((passed, list) -> {
			System.out.println(passed ? "Prolaz:" : "Neprolaz:");
			list.forEach(System.out::println);
		});
	}

	/**
	 * Converts string lines of student records to list of {@link StudentRecord}
	 * objects.
	 * 
	 * @param lines lines of student records.
	 * @return a list of {@link StudentRecord} objects.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		for (String line : lines) {
			if (line.isBlank())
				continue;

			String[] data = line.split("\\s+");

			if (data.length != 7) {
				System.out.println("Wrong input!");
				System.exit(1);
			}

			String jmbag = data[0];
			String lastName = data[1];
			String firstName = data[2];
			Integer grade = null;
			Double midTermPoints = null, endTermPoints = null, labPoints = null;

			try {
				midTermPoints = Double.parseDouble(data[3]);
				endTermPoints = Double.parseDouble(data[4]);
				labPoints = Double.parseDouble(data[5]);
				grade = Integer.parseInt(data[6]);

			} catch (Exception e) {
				System.out.println("Error with parsing number: " + e.getMessage());
				System.exit(1);
			}

			records.add(new StudentRecord(jmbag, lastName, firstName, midTermPoints, endTermPoints, labPoints, grade));
		}

		return records;
	}

	/**
	 * Prints the beginning of the assignment.
	 * 
	 * @param assignmentNumber number of the assignment.
	 */
	private static void printAssigment(int assignmentNumber) {
		System.out.println("Zadatak " + assignmentNumber);
		System.out.println("=========");
	}

	/**
	 * Returns the number of students that had more than 25 points.
	 * 
	 * @param records list of student records.
	 * @return the number of students that had more than 25 points.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter((record) -> record.getMidTermPoints() + record.getEndTermPoints() + record.getLabPoints() > 25)
				.count();
	}

	/**
	 * Returns the number of students that had the '5' grade.
	 * 
	 * @param records list of student records.
	 * @return the number of students that had the '5' grade.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((record) -> record.getGrade() == 5).count();

	}

	/**
	 * Returns the list of students that had the '5' grade.
	 * 
	 * @param records list of student records.
	 * @return the list of students that had the '5' grade.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((record) -> record.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns the sorted by number of points list of students that had the '5'
	 * grade.
	 * 
	 * @param records list of student records.
	 * @return the sorted by number of points list of students that had the '5'
	 *         grade.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter((record) -> record.getGrade() == 5).sorted((s1, s2) -> {
			Double points1 = s1.getMidTermPoints() + s1.getEndTermPoints() + s1.getLabPoints();
			Double points2 = s2.getMidTermPoints() + s2.getEndTermPoints() + s2.getLabPoints();
			return points1.compareTo(points2);
		}).collect(Collectors.toList());

	}

	/**
	 * Returns list of jmbags of students that didn't pass.
	 * 
	 * @param records list of student records.
	 * @return List of jmbags of students that didn't pass.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter((record) -> record.getGrade() == 1).map(StudentRecord::getJmbag)
				.sorted(String::compareTo).collect(Collectors.toList());
	}

	/**
	 * Returns the map where keys are grades and values are students that have that
	 * grade.
	 * 
	 * @param records list of student records.
	 * @return the map where keys are grades and values are students that have that
	 *         grade.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Returns the map where keys are grades and values are number of students that
	 * have that grade.
	 * 
	 * @param records list of student records.
	 * @return the map where keys are grades and values are number of students that
	 *         have that grade.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getGrade, (record) -> 1, (a, b) -> a + b));
	}

	/**
	 * Returns the map where keys are true/false (as in passed or not) and values
	 * are students that either passed or didn't pass.
	 * 
	 * @param records list of student records.
	 * @return the map where keys are true/false (as in passed or not) and values
	 *         are students that either passed or didn't pass.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy((record) -> record.getGrade() > 1));
	}

}
