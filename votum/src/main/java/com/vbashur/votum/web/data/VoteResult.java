package com.vbashur.votum.web.data;

import java.io.Serializable;
import java.util.List;

import com.vbashur.votum.domain.Restaurant;

public class VoteResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4251590460257053735L;

	private Long votesNum;
	
	private List<Restaurant> restaurants;

	public Long getVotesNum() {
		return votesNum;
	}

	public void setVotesNum(Long votesNum) {
		this.votesNum = votesNum;
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	

}
