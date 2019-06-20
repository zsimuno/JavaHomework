package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * DAO implementation that uses JPA.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
	}

	@Override
	public boolean nickExists(String nick) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(BlogUser user) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void register(BlogUser newUser) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BlogUser> getAllAuthors() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BlogEntry> getUserEntries(BlogUser user) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void editEntry(BlogEntry entry) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newEntry(BlogEntry entry) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BlogComment> getEntryComments(BlogEntry entry) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newComment(BlogComment comment) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}