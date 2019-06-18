package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Singleton class that provides {@link EntityManagerFactory} object that is
 * set that provides connections to the database. 
 * 
 * @author Zvonimir Šimunović
 *
 */
public class JPAEMFProvider {

	/** The entity manager factory. */
	public static EntityManagerFactory emf;

	/**
	 * @return The entity manager factory.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets The entity manager factory.
	 * 
	 * @param emf The given entity manager factory.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}