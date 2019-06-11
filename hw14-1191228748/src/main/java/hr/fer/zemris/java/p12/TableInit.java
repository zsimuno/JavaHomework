package hr.fer.zemris.java.p12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Contains methods for initializing of poll tables.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class TableInit {
	// TODO provjerit kada bacat exceptione i kako ih hvatam i slicno.

	/** Poll for choosing favorite band. */
	private static Poll bandPoll = new Poll("Vote for your favorite band:",
			"Out of the given bands, which one is your favorite? Click on the link to vote!");
	/** Poll for choosing favorite movie. */
	private static Poll moviePoll = new Poll("Vote for your favorite movie:",
			"Out of the given movies, which one is your favorite? Click on the link to vote!");
	/** Initial values for band poll inserted into {@code PollOptions} table. */
	private static List<PollOption> initialBandPollOptions = new ArrayList<>();
	/** Initial values for movie poll inserted into {@code PollOptions} table. */
	private static List<PollOption> initialMoviePollOptions = new ArrayList<>();

	// Initialize polls and options. We don't set ID because we don't
	// know their ID until we put the values in the table.
	static {

		initialBandPollOptions.add(new PollOption("The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", 0));
		initialBandPollOptions.add(new PollOption("The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", 0));
		initialBandPollOptions.add(new PollOption("The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", 0));
		initialBandPollOptions
				.add(new PollOption("The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", 0));
		initialBandPollOptions.add(new PollOption("The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", 0));
		initialBandPollOptions
				.add(new PollOption("The Everly Brothers", "https://www.youtube.com/watch?v=tbU3zdAgiX8", 0));
		initialBandPollOptions
				.add(new PollOption("The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", 0));

		initialMoviePollOptions.add(new PollOption("The Revenant", "https://www.youtube.com/watch?v=LoebZZ8K5N0", 0));
		initialMoviePollOptions.add(
				new PollOption("Godzilla: King of The Monsters", "https://www.youtube.com/watch?v=QFxN2oDKk0E", 0));
		initialMoviePollOptions.add(new PollOption("Birdman", "https://www.youtube.com/watch?v=uJfLoE6hanc", 0));
		initialMoviePollOptions
				.add(new PollOption("Pulp Fiction", "https://www.youtube.com/watch?v=s7EdQ4FqbhY&pbjreload=10", 0));
		initialMoviePollOptions
				.add(new PollOption("The Big Lebowski", "https://www.youtube.com/watch?v=cd-go0oBF4Y", 0));
		initialMoviePollOptions
				.add(new PollOption("Star Wars: A New Hope", "https://www.youtube.com/watch?v=1g3_CFmnU7k", 0));
		initialMoviePollOptions.add(new PollOption("The Room", "https://www.youtube.com/watch?v=9-dIdFXeFhs", 0));

	}

	/**
	 * Create Polls table and PollOptions table if they do not exist and fills them
	 * with values if needed.
	 * 
	 * @param dataSource Data source for getting the connection to database.
	 */
	public static void createTableIfNeeded(DataSource dataSource) {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		String createPoll = "CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)";

		if (create(createPoll, con)) {
			if (isPollsTableEmpty(con)) {
				initTableValues(con);
			}
		} else { // Table just created.
			initTableValues(con);
		}

		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Create a table with the given {@code createCommand} if it doesn't exist.
	 * 
	 * @param createCommand command used to create the table.
	 * @param con           connection to database.
	 * @return {@code true} if table already exists, {@code false} if it doesn't.
	 */
	private static boolean create(String createCommand, Connection con) {
		PreparedStatement pst = null;
		boolean alreadyExists = false;
		try {
			pst = con.prepareStatement(createCommand);
			pst.executeUpdate();
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("X0Y32")) { // Table exists
				alreadyExists = true;
			}
		} finally {
			try {
				if (pst != null)
					pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return alreadyExists;

	}

	/**
	 * Checks if the {@code Polls} table is empty.
	 * 
	 * @param con connection to DB.
	 * @return {@code true} if {@code Polls} table is empty, {@code false}
	 *         otherwise.
	 */
	private static boolean isPollsTableEmpty(Connection con) {
		PreparedStatement pst = null;
		int rowCount = 0;
		try {
			pst = con.prepareStatement("SELECT COUNT(*) FROM Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					rs.next();
					rowCount = rs.getInt(1);
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
			ex.printStackTrace();
		}
		return rowCount == 0;
	}

	/**
	 * Put initial values in {@code Polls} and {@code PollOptions} tables. If
	 * {@code PollOptions} table already exists it is emptied and then filled with
	 * initial values.
	 * 
	 * @param con connection to database.
	 */
	private static void initTableValues(Connection con) {
		// Create PollOptions table if needed. If it exists then empty it.
		String createPollOptions = "CREATE TABLE PollOptions (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
				+ "optionTitle VARCHAR(100) NOT NULL, optionLink VARCHAR(150) NOT NULL, pollID BIGINT, "
				+ "votesCount BIGINT, FOREIGN KEY (pollID) REFERENCES Polls(id))";

		if (create(createPollOptions, con)) {
			emptyPollOptions(con);
		}

		insertPoll(con, bandPoll, initialBandPollOptions);
		insertPoll(con, moviePoll, initialMoviePollOptions);
	}

	/**
	 * Empties {@code PollOptions} table.
	 * 
	 * @param con connection to database.
	 */
	private static void emptyPollOptions(Connection con) {
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("DELETE FROM PollOptions");
			try {
				pst.executeUpdate();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Inserts given poll into {@code Polls} table and inserts given
	 * {@code initialPollOptions} into {@code PollOptions} with pollID of the
	 * inserted poll.
	 * 
	 * @param con                connection to database.
	 * @param poll               poll to be inserted.
	 * @param initialPollOptions poll options to be inserted.
	 */
	private static void insertPoll(Connection con, Poll poll, List<PollOption> initialPollOptions) {
		PreparedStatement pst = null;
		long pollID = -1;

		// Insert poll
		try {
			pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, poll.getTitle());
			pst.setString(2, poll.getMessage());
			pst.executeUpdate();

			ResultSet rset = pst.getGeneratedKeys();

			try {
				if (rset != null && rset.next()) {
					pollID = rset.getLong(1);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		// Insert poll options of this poll
		try {
			pst = con.prepareStatement(
					"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			for (PollOption pollOption : initialPollOptions) {
				pst.setString(1, pollOption.getOptionTitle());
				pst.setString(2, pollOption.getOptionLink());
				pst.setLong(3, pollID);
				pst.setLong(4, pollOption.getVotesCount());
				pst.executeUpdate();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

	}
}
