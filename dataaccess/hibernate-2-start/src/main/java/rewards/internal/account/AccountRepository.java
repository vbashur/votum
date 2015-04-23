package rewards.internal.account;

import org.springframework.transaction.annotation.Transactional;


/**
 * Loads account aggregates. Called by the reward network to find and reconstitute Account entities from an external
 * form such as a set of RDMS rows.
 * 
 * Objects returned by this repository are guaranteed to be fully-initialized and ready to use.
 */
public interface AccountRepository {

	/**
	 * Load an account by its credit card.
	 * @param creditCardNumber the credit card number
	 * @return the account object
	 */
	@Transactional(readOnly = true)
	public Account findByCreditCard(String creditCardNumber);

}