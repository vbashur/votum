package rewards.internal.restaurant;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Finds restaurants using the Hibernate API.
 */
public class HibernateRestaurantRepository implements RestaurantRepository {

	private SessionFactory sessionFactory;

	/**
	 * Creates an new hibernate-based restaurant repository.
	 * @param sessionFactory the Hibernate session factory required to obtain sessions
	 */
	public HibernateRestaurantRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Restaurant findByMerchantNumber(String merchantNumber) {
		// TODO 4: Find and return a Restaurant based on its merchant number
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Returns the session associated with the ongoing reward transaction.
	 * @return the transactional session
	 */
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}