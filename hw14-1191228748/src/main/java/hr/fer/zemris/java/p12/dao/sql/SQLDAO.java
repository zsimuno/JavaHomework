package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of DAO that uses SQL technology. This implementation expects a
 * connection through the {@link SQLConnectionProvider} class.
 * 
 * @author Zvonimir Šimunović
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getAllPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while retrieveing the list of polls.", ex);
		}
		return polls;
	}

	@Override
	public List<PollOption> getAllPollOptions(long pollId) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=? order by votesCount DESC");
			pst.setLong(1, Long.valueOf(pollId));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption pollOption = new PollOption();
						pollOption.setId(rs.getLong(1));
						pollOption.setOptionTitle(rs.getString(2));
						pollOption.setOptionLink(rs.getString(3));
						pollOption.setPollID(rs.getLong(4));
						pollOption.setVotesCount(rs.getLong(5));
						pollOptions.add(pollOption);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while retrieveing the list of poll options.", ex);
		}
		return pollOptions;
	}

	@Override
	public void voteOnOption(long optionId) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update PollOptions set votesCount = votesCount + 1 where id = ?");
			pst.setLong(1, Long.valueOf(optionId));
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while updating the vote count on the given option.", ex);
		}

	}

	@Override
	public Poll getPoll(long pollId) throws DAOException {
		Poll poll = new Poll();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(pollId));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					rs.next();
					poll.setId(rs.getLong(1));
					poll.setTitle(rs.getString(2));
					poll.setMessage(rs.getString(3));

				} catch (Exception ignorable) {
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while retrieveing the list of polls.", ex);
		}
		return poll;
	}

}