/**
 * 
 */
package hr.fer.zemris.java.hw07.demo4;

/**
 * Represents one student record.
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
	 * Points of the mid term exam of the student.
	 */
	private Double midTermPoints;

	/**
	 * Points of the end term exam of the student.
	 */
	private Double endTermPoints;

	/**
	 * Points of laboratory exams of the student.
	 */
	private Double labPoints;

	/**
	 * Grade of the student.
	 */
	private Integer grade;

	/**
	 * Constructs a new {@code StudentRecord} from the given values
	 * 
	 * @param jmbag         of the student.
	 * @param lastName      of the student.
	 * @param firstName     of the student.
	 * @param midTermPoints of the student.
	 * @param endTermPoints of the student.
	 * @param labPoints     of the student.
	 * @param grade         of the student.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, Double midTermPoints, Double endTermPoints,
			Double labPoints, Integer grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midTermPoints = midTermPoints;
		this.endTermPoints = endTermPoints;
		this.labPoints = labPoints;
		this.grade = grade;
	}

	/**
	 * @return the jmbag of the student.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * @return the last name of the student.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the first name of the student.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the mid term points of the student.
	 */
	public Double getMidTermPoints() {
		return midTermPoints;
	}

	/**
	 * @return the end term points of the student.
	 */
	public Double getEndTermPoints() {
		return endTermPoints;
	}

	/**
	 * @return the lab points of the student.
	 */
	public Double getLabPoints() {
		return labPoints;
	}

	/**
	 * @return the grade of the student.
	 */
	public Integer getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + firstName + "\t" + midTermPoints + "\t" + endTermPoints + "\t"
				+ labPoints + "\t" + grade;
	}

}
