package hr.fer.zemris.java.p12.model;

/**
 * Represents one option of one of the given polls.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class PollOption {

	/** Identification (ID) of the poll option. Unique to every poll option. */
	private long id;
	/** Title of this option. */
	private String optionTitle;
	/** Link to a page that is connected to this poll option. */
	private String optionLink;
	/**
	 * Identification (ID) of the poll that this option is for. Unique to every
	 * poll.
	 */
	private long pollID;
	/** Count of the votes that this option received. */
	private long votesCount;

	/**
	 * Default constructor.
	 */
	public PollOption() {
	}

	/**
	 * Construct using fields.Without ID to use in situations where this values are
	 * used for inserting into database.
	 * 
	 * @param optionTitle Title of the option.
	 * @param optionLink  Link connected to the option.
	 * @param votesCount  Vote count of this poll option.
	 */
	public PollOption( String optionTitle, String optionLink, long votesCount) {
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
	}

	/**
	 * Sets the poll option ID.
	 * 
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Sets the title of this poll option.
	 * 
	 * @param optionTitle the poll option title to set.
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Sets the link of this poll option.
	 * 
	 * @param optionTitle the poll link title to set.
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Sets the ID of the poll that this option is from.
	 * 
	 * @param pollID the ID of the poll to set.
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Sets the number of votes this poll option received.
	 * 
	 * @param votesCount the number of votes to set.
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

	/**
	 * @return the ID of the poll option. Unique to every poll option.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the title of this option.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @return the link to a page that is connected to this poll option.
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @return the ID of the poll that this option is for. Unique to every poll.
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * @return the count of the votes that this option received.
	 */
	public long getVotesCount() {
		return votesCount;
	}

}
