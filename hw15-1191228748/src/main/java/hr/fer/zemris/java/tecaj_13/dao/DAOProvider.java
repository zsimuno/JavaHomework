package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * 
 * Singleton class that knows who to return as a provider for the communication
 * with the database.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class DAOProvider {

	/** One DAO implementation. */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Get the provider.
	 * 
	 * @return the DAO provider that communicates with the database.
	 */
	public static DAO getDAO() {
		return dao;
	}

}