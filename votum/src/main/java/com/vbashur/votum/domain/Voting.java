package com.vbashur.votum.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "Voting")
public class Voting {
	
	@Column(name = "voting_id")		
	@Id
	@GeneratedValue
	private Long votingId;
	
	@Column(name = "user")
	private String user;
		
	@JoinColumn
	@ManyToOne
	private Restaurant restaurant;

	public Long getVotingId() {
		return votingId;
	}

	public void setVotingId(Long votingId) {
		this.votingId = votingId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
