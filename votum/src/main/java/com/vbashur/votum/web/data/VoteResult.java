package com.vbashur.votum.web.data;

import java.io.Serializable;
import java.util.List;

import com.vbashur.votum.domain.Restaurant;

public class VoteResult <T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4251590460257053735L;

	private Long votesNum;
	
	private List<T> details;

	public Long getVotesNum() {
		return votesNum;
	}

	public void setVotesNum(Long votesNum) {
		this.votesNum = votesNum;
	}

	public List<T> getDetails() {
		return details;
	}

	public void setDetails(List<T> details) {
		this.details = details;
	}
	

}
