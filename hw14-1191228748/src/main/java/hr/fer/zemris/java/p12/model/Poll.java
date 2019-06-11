package hr.fer.zemris.java.p12.model;

/**
 * Represents one avaliable poll to vote on.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class Poll {

	/** Identification (ID) of the poll. Unique to every poll. */
	private long id;
	/** Title of the poll. */
	private String title;
	/** Message describing the poll. */
	private String message;

	/**
	 * Default constructor.
	 */
	public Poll() {

	}

	/**
	 * Construct using fields. Without ID to use in situations where this values are
	 * used for inserting into database.
	 * 
	 * @param title   Title of the poll.
	 * @param message Message of the poll.
	 */
	public Poll(String title, String message) {
		this.title = title;
		this.message = message;
	}

	/**
	 * Sets the poll ID.
	 * 
	 * @param id the id of the poll to set.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the poll title.
	 * 
	 * @param title the title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Sets the poll message.
	 * 
	 * @param message the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the ID of the poll. Unique to every poll.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the title of the poll.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the message describing the poll.
	 */
	public String getMessage() {
		return message;
	}

}
