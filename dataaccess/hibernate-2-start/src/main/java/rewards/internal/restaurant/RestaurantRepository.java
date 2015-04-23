package rewards.internal.restaurant;

import org.springframework.transaction.annotation.Transactional;

/**
 * Loads restaurant aggregates. Called by the reward network to find and reconstitute Restaurant entities from an
 * external form such as a set of RDMS rows.
 * 
 * Objects returned by this repository are guaranteed to be fully-initialized and ready to use.
 */
public interface RestaurantRepository {

	/**
	 * Load a Restaurant entity by its merchant number.
	 * @param merchantNumber the merchant number
	 * @return the restaurant
	 */
	@Transactional(readOnly = true)
	public Restaurant findByMerchantNumber(String merchantNumber);
}
