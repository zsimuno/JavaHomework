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
		try {
			return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		} catch (Exception e) {
			throw new DAOException("Error while retrieving blog entry.", e);
		}
	}

	@Override
	public boolean nickExists(String nick) throws DAOException {
		try {
			Long count = JPAEMProvider.getEntityManager()
					.createQuery("select count(e) from BlogUser e where e.nick=:ni", Long.class)
					.setParameter("ni", nick).getSingleResult();
			return !count.equals(0L);
		} catch (Exception e) {
			throw new DAOException("Error while checking if the nick exists.", e);
		}
	}

	@Override
	public BlogUser getUser(String nick) throws DAOException {
		try {
			List<BlogUser> users = JPAEMProvider.getEntityManager()
					.createQuery("select e from BlogUser e where e.nick=:ni", BlogUser.class).setParameter("ni", nick)
					.getResultList();
			return users.isEmpty() ? null : users.get(0);
		} catch (Exception e) {
			throw new DAOException("Error while trying to login.", e);
		}
	}

	@Override
	public void register(BlogUser newUser) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(newUser);
		} catch (Exception e) {
			throw new DAOException("Error while registering a new user.", e);
		}
	}

	@Override
	public List<BlogUser> getAllAuthors() throws DAOException {
		try {
			return JPAEMProvider.getEntityManager().createQuery("select u from BlogUser u", BlogUser.class)
					.getResultList();
		} catch (Exception e) {
			throw new DAOException("Error while retrieving all authors.", e);
		}
	}

	@Override
	public List<BlogEntry> getUserEntries(BlogUser user) throws DAOException {
		try {
			return JPAEMProvider.getEntityManager()
					.createQuery("select b from BlogEntry b where b.creator=:cre", BlogEntry.class)
					.setParameter("cre", user).getResultList();
		} catch (Exception e) {
			throw new DAOException("Error while retrieving all authors entries.", e);
		}
	}

	@Override
	public void editEntry(BlogEntry entry) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().merge(entry); 	
		} catch (Exception e) {
			throw new DAOException("Error while editing blog entry.", e);
		}
	}

	@Override
	public void newEntry(BlogEntry entry) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(entry);
		} catch (Exception e) {
			throw new DAOException("Error while adding new blog entry.", e);
		}
	}


	@Override
	public void newComment(BlogComment comment) throws DAOException {
		try {
			JPAEMProvider.getEntityManager().persist(comment);
		} catch (Exception e) {
			throw new DAOException("Error while adding a new comment.", e);
		}
	}

}