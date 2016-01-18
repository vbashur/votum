package com.vbashur.votum.web.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.repository.RestaurantRepository;
import com.vbashur.votum.web.data.Vote;

@RestController
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository repository;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
	@ResponseBody
	public ResponseEntity<?> listRestaurants() {
		
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("user>>>>>>>>>" + userDetails.getUsername());
		
		Iterable<Restaurant> restaurants = repository.findAll();
		List<Restaurant> respBody = new LinkedList<Restaurant>();
		for (Restaurant r : restaurants) {
			respBody.add(r);
		}
		ResponseEntity<List<Restaurant>> usersResp = new ResponseEntity<List<Restaurant>>(respBody, HttpStatus.OK);		
		return usersResp;
	}
	
	@RequestMapping(value = "/vote", method = RequestMethod.GET/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
	@ResponseBody
	public ResponseEntity<?> voteForRestaurant(@RequestBody Vote vote) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal != null && principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails)principal;
			System.out.println("user>>>>>>>>>" + userDetails.getUsername());
		
			
		}
		System.out.println("vote>>>>>>>>>" + vote.getRestaurantId());
		
	        
		Iterable<Restaurant> restaurants = repository.findAll();
		List<Restaurant> respBody = new LinkedList<Restaurant>();
		for (Restaurant r : restaurants) {
			respBody.add(r);
		}
		ResponseEntity<List<Restaurant>> usersResp = new ResponseEntity<List<Restaurant>>(respBody, HttpStatus.OK);		
		return usersResp;
	}


}
