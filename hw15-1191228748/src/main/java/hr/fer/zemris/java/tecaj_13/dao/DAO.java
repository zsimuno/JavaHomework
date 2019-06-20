package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * 
 * Interface for communication with the database.
 * 
 * @author Zvonimir Šimunović
 *
 */
public interface DAO {

	/**
	 * Gets the blog entry with the given {@code id}. If no such entry exists then
	 * it returns {@code null}.
	 * 
	 * 
	 * @param id primary key of the entry
	 * @return entry or {@code null} if no such entry exists
	 * @throws DAOException if there is an error while working with data.
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

//	// TODO insert entry, login and insert comment

	/**
	 * Check if the given nick already exists.
	 * 
	 * @param nick nickname to be checked.
	 * @return {@code true} if the nick already exists, {@code false} otherwise.
	 * @throws DAOException if there is an error while working with data.
	 */
	public boolean nickExists(String nick) throws DAOException;


	/**
	 * Retrieves the user data of the user with the given {@code nick}.
	 * 
	 * @param nick nick of the user.
	 * @return User with the given nick or {@code null} if no such user exists.
	 * @throws DAOException if there is an error while working with data.
	 */
	public BlogUser getUser(String nick) throws DAOException;

	/**
	 * Registers a new user.
	 * 
	 * @param newUser user to be registered.
	 * @throws DAOException if there is an error while working with data.
	 */
	public void register(BlogUser newUser) throws DAOException;

	/**
	 * Returns a list of all blog authors.
	 * 
	 * @return list of all blog authors as {@link BlogUser} objects.
	 * @throws DAOException if there is an error while working with data.
	 */
	public List<BlogUser> getAllAuthors() throws DAOException;

	/**
	 * TODO javadoc i ima li smisla slat sve ili samo ID od usera?
	 * 
	 * @param user
	 * @return
	 * @throws DAOException if there is an error while working with data.
	 */
	public List<BlogEntry> getUserEntries(BlogUser user) throws DAOException;

	/**
	 * Edits the given entry to the given data.
	 * 
	 * @param entry entry to be edited.
	 * @throws DAOException if there is an error while working with data.
	 */
	public void editEntry(BlogEntry entry) throws DAOException;

	/**
	 * Adds the given entry to the data.
	 * 
	 * @param entry entry to be added.
	 * @throws DAOException if there is an error while working with data.
	 */
	public void newEntry(BlogEntry entry) throws DAOException;

	/**
	 * TODO uzimamo cijeli ili samo ID? 
	 * Returns the list of all the comments on the
	 * given blog {@code entry}.
	 * 
	 * @param entry entry for which we retrieve the comments of.
	 * @return list of all the comments on the given blog {@code entry}.
	 * @throws DAOException if there is an error while working with data.
	 */
	public List<BlogComment> getEntryComments(BlogEntry entry) throws DAOException;
	
	/**
	 * Adds the given comment to the data.
	 * 
	 * @param comment comment to be added.
	 * @throws DAOException if there is an error while working with data.
	 */
	public void newComment(BlogComment comment) throws DAOException;

}
