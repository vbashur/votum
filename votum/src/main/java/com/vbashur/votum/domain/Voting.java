package com.vbashur.votum.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Table(name = "Voting")
public class Voting {
	
	private String userName;
	
	private String restaurantName;

}
