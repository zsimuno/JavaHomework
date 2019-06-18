package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

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

//	/**
//	 * @return
//	 * @throws DAOException if there is an error while working with data.
//	 */
//	public List<BlogEntry> getAllBlogEntries() throws DAOException;
//	
//	/**
//	 * @param id
//	 * @return
//	 * @throws DAOException if there is an error while working with data.
//	 */
//	public List<BlogEntry> getBlogComments(Long id) throws DAOException;
//	
//	// TODO insert entry, login and insert comment
}