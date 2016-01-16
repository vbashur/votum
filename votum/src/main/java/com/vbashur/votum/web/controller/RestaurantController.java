package com.vbashur.votum.web.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.domain.User;
import com.vbashur.votum.repository.RestaurantRepository;

@Controller
public class RestaurantController {
	
	@Autowired
	private RestaurantRepository repository;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> listRestaurants() {
		Iterable<Restaurant> restaurants = repository.findAll();
		List<Restaurant> respBody = new LinkedList<Restaurant>();
		for (Restaurant r : restaurants) {
			respBody.add(r);
		}
		ResponseEntity<List<Restaurant>> usersResp = new ResponseEntity<List<Restaurant>>(respBody, HttpStatus.OK);		
		return usersResp;
	}

}
