package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * 
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {

	/**
	 * Retrieves a list of all polls from the database.
	 * 
	 * @return a list of all polls.
	 * @throws DAOException if an error occurs.
	 */
	public List<Poll> getAllPolls() throws DAOException;

	/**
	 * Retrieves list of all the options of the poll with the given {@code pollId} sorted by vote count. 
	 * 
	 * @param pollId ID of the poll we want the options of.
	 * @return list of all the options of the given poll.
	 * @throws DAOException if an error occurs.
	 */
	public List<PollOption> getAllPollOptions(long pollId) throws DAOException;

	/**
	 * Increases the vote count (by one) of the poll option with the given
	 * {@code optionId}.
	 * 
	 * @param optionId ID of the poll option.
	 * @throws DAOException if an error occurs.
	 */
	public void voteOnOption(long optionId) throws DAOException;
}
