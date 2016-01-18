package com.vbashur.votum.web.data;

import java.io.Serializable;

public class Vote implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6800548526266616156L;
	
	private Long restaurantId;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

}
