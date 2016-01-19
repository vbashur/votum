package com.vbashur.votum.web.controller;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.domain.Voting;
import com.vbashur.votum.repository.RestaurantRepository;
import com.vbashur.votum.repository.VotingRepository;
import com.vbashur.votum.web.data.OperationResult;
import com.vbashur.votum.web.data.Status;
import com.vbashur.votum.web.data.Vote;
import com.vbashur.votum.web.data.VoteResult;

@RestController
public class RestaurantController {
	
	public static String WRONG_RESTAURANT_MSG = "The restaurant with specified Id doesn't exist";
	public static String SUCCESSFULLY_VOTED_RESTAURANT_MSG = "User %s has successfully voted for the restaurant with id=%s";
	public static String SUCCESSFULLY_CHANGED_RESTAURANT_MSG = "User %s has successfully changed the restaurant, new restaurant id=%s";
	public static String LATELY_CHANGED_RESTAURANT_MSG = "User %s is too late to change the restaurant ";
	public static String NOTHING_TO_UPDATE_MSG = "Nothing to update";
	public static String NO_VOTES_MSG = "Unable to choose restaurant: no votes received";
	public static String DUPLICATE_NAME_MSG = "Restaurant with specified name already exists";
	public static String ADDED_RESTAURTANT_MSG = "Restaurant was successfully added";
	public static String REMOVED_RESTAURTANT_MSG = "Restaurant was successfully removed";
	public static String UPDATED_RESTAURTANT_MSG = "Restaurant was successfully updated";
	
	public static LocalTime DEADLINE = LocalTime.of(11, 00);
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private VotingRepository votingRepository;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
	@ResponseBody
	public ResponseEntity<?> listRestaurants() {		
		Iterable<Restaurant> restaurants = restaurantRepository.findAll();
		List<Restaurant> respBody = new LinkedList<Restaurant>();
		for (Restaurant r : restaurants) {
			respBody.add(r);
		}
		OperationResult<List<Restaurant>> response = new OperationResult<List<Restaurant>>();
		response.setStatus(Status.SUCCESS);
		response.setBody(respBody);
		return new ResponseEntity<OperationResult<List<Restaurant>>>(response, HttpStatus.OK);	
		
	}
	
	@RequestMapping(value = "/vote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> voteForRestaurant(@RequestBody Vote vote) {					
		
		Long restaurantId = vote.getRestaurantId();				
		Restaurant restaurant = restaurantRepository.findOne(restaurantId);	
		OperationResult<String> response = new OperationResult<>();
		if (restaurant == null) {
			response.setStatus(Status.FAILED);
			response.setBody(WRONG_RESTAURANT_MSG);											
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal != null && principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails)principal;			
			String username = userDetails.getUsername(); 
			
			Voting existedUserVoting = votingRepository.findByUser(username);
			if (existedUserVoting == null) {														
				Voting firstUserVoting = new Voting();
				firstUserVoting.setUser(username);				
				firstUserVoting.setRestaurant(restaurant);
				votingRepository.save(firstUserVoting);
				response.setStatus(Status.SUCCESS);
				response.setBody(String.format(SUCCESSFULLY_VOTED_RESTAURANT_MSG, username, restaurant.getName()));					
				return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);
				
			} else if (!existedUserVoting.getRestaurant().getRestaurantId().equals(restaurantId)) {								
				
				LocalTime currentTime = LocalTime.now();
				if (currentTime.isBefore(DEADLINE)) {
					existedUserVoting.setRestaurant(restaurant);
					votingRepository.save(existedUserVoting);
					response.setStatus(Status.SUCCESS);
					response.setBody(String.format(SUCCESSFULLY_CHANGED_RESTAURANT_MSG, username, restaurant.getName()));					
					return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);					
				} else {
					response.setStatus(Status.FAILED);
					response.setBody(String.format(LATELY_CHANGED_RESTAURANT_MSG, username));					
					return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);					
				}				
			}
		}		
		response.setStatus(Status.IGNORED);
		response.setBody(NOTHING_TO_UPDATE_MSG);		
		return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/restaurant/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant fetchedRestaurant = restaurantRepository.findByName(restaurant.getName());
		OperationResult<String> response = new OperationResult<String>();
		if (fetchedRestaurant != null) {			
			response.setStatus(Status.FAILED);
			response.setBody(DUPLICATE_NAME_MSG);
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.BAD_REQUEST);			
		} else {
			restaurantRepository.save(restaurant);
			response.setStatus(Status.SUCCESS);
			response.setBody(ADDED_RESTAURTANT_MSG);
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);			
		}
		
	}
	
	@RequestMapping(value = "/restaurant/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteRestaurant(@RequestBody Vote voteRestaurantToDelete) {
		Long id = voteRestaurantToDelete.getRestaurantId();
		OperationResult<String> response = new OperationResult<String>();
		if (restaurantRepository.exists(id)) {		
			restaurantRepository.delete(id);
			response.setStatus(Status.SUCCESS);
			response.setBody(REMOVED_RESTAURTANT_MSG);
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);						
		} else {
			response.setStatus(Status.FAILED);
			response.setBody(WRONG_RESTAURANT_MSG);
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.BAD_REQUEST);						
		}		
	}
	
	@RequestMapping(value = "/restaurant/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateRestaurant(@RequestBody Restaurant restaurant) {		
		Long id = restaurant.getRestaurantId();		
		OperationResult<String> response = new OperationResult<String>();
		if (restaurantRepository.exists(id)) {			
			restaurantRepository.save(restaurant);			
			response.setStatus(Status.SUCCESS);
			response.setBody(UPDATED_RESTAURTANT_MSG);
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);			
		} else {					
			response.setStatus(Status.FAILED);	
			response.setBody(WRONG_RESTAURANT_MSG);					
			return new ResponseEntity<OperationResult<String>>(response, HttpStatus.OK);											
		}		
	}
	
	
	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> collectVotes() { 
		Iterable<Voting> votesIterable = votingRepository.findAll();
		Set<Voting> votes = new HashSet<Voting>();
		votesIterable.forEach( voting -> votes.add(voting));		
		OperationResult<Set<Voting>> response = new OperationResult<>();
		response.setBody(votes);
		response.setStatus(Status.SUCCESS);
		return new ResponseEntity<OperationResult<Set<Voting>>>(response, HttpStatus.OK);					
	}
	
	@RequestMapping(value = "/top", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTopVotedRestaurant() { 
		Iterable<Voting> votesIterable = votingRepository.findAll();
		Map<Long, Long> voteCountMap = new HashMap<Long, Long>();
		votesIterable.forEach( voting ->
			{
				Restaurant restaurant = voting.getRestaurant();
				if (restaurant != null) {
					Long id = restaurant.getRestaurantId();
					if (voteCountMap.containsKey(id)) {			
						Long votesNum = voteCountMap.get(id);
						voteCountMap.put(id, ++votesNum);
					} else {
						voteCountMap.put(id, 1L);						
					}
					
				}
			});
		if (voteCountMap.size() > 0) {
			Long maxVotes = Collections.max(voteCountMap.values());
			List<Restaurant> topVotedRestaurants = new LinkedList<Restaurant>();
			voteCountMap.keySet().forEach( restaurantId -> {
				if (voteCountMap.get(restaurantId).equals(maxVotes)) {
					Restaurant r = restaurantRepository.findOne(restaurantId);
					topVotedRestaurants.add(r);
				}
			});
			
			VoteResult voteResult = new VoteResult();
			voteResult.setVotesNum(maxVotes);
			voteResult.setRestaurants(topVotedRestaurants);
			OperationResult<VoteResult> response = new OperationResult<>();
			response.setBody(voteResult);
			response.setStatus(Status.SUCCESS);			
			return new ResponseEntity<OperationResult<VoteResult>>(response, HttpStatus.OK);			
		} else {
			OperationResult<String> res = new OperationResult<>();
			res.setBody(NO_VOTES_MSG);
			res.setStatus(Status.IGNORED);
			return new ResponseEntity<OperationResult<String>>(res, HttpStatus.OK);			
		}												
	}
	
}
