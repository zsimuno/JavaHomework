/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Represents a record of one student.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class StudentRecord {

	/**
	 * JMBAG of the student.
	 */
	private String jmbag;
	/**
	 * Last name of the student.
	 */
	private String lastName;
	/**
	 * First name of the student.
	 */
	private String firstName;
	/**
	 * Final grade of the student.
	 */
	private String finalGrade;

	/**
	 * Constructs a {@code StudentRecord} using given values
	 * 
	 * @param jmbag      JMBAG of the student
	 * @param lastName   Last name of the student
	 * @param firstName  First name of the student
	 * @param finalGrade Final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * @return the JMBAG of the student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return the last name of the student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the first name of the student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the final grade of the student
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(finalGrade, firstName, jmbag, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return  Objects.equals(jmbag, other.jmbag);
	}

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", finalGrade="
				+ finalGrade + "]";
	}

}
