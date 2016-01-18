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
	
	private static String WRONG_RESTAURANT_MSG = "The restaurant with specified Id doesn't exist";
	private static String SUCCESSFULLY_CHANGED_RESTAURANT_MSG = "User %s has successfully changed the restaurant, new restaurant id=%s";
	private static String LATELY_CHANGED_RESTAURANT_MSG = "User %s is too late to change the restaurant ";
	private static String NOTHING_TO_UPDATE_MSG = "Nothing to update";
	private static String NO_VOTES_MSG = "Unable to choose restaurant: no votes received";
	private static String DUPLICATE_NAME_MSG = "Restaurant with specified name already exists";
	private static String ADDED_RESTAURTANT_MSG = "Restaurant was successfully added";
	private static String REMOVED_RESTAURTANT_MSG = "Restaurant was successfully removed";
	private static String UPDATED_RESTAURTANT_MSG = "Restaurant was successfully updated";
	
	private static LocalTime DEADLINE = LocalTime.of(11, 00);
	
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
		ResponseEntity<List<Restaurant>> usersResp = new ResponseEntity<List<Restaurant>>(respBody, HttpStatus.OK);		
		return usersResp;
	}
	
	@RequestMapping(value = "/vote", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> voteForRestaurant(@RequestBody Vote vote) {					
		
		Long restaurantId = vote.getRestaurantId();				
		Restaurant restaurant = restaurantRepository.findOne(restaurantId);				
		if (restaurant == null) {
			ResponseEntity<String> response = new ResponseEntity<String>(WRONG_RESTAURANT_MSG, HttpStatus.BAD_REQUEST);					
			return response;
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
			} else if (existedUserVoting.getRestaurant().getRestaurantId() != restaurantId) {								
				
				LocalTime currentTime = LocalTime.now();
				if (currentTime.isBefore(DEADLINE)) {
					existedUserVoting.setRestaurant(restaurant);
					votingRepository.save(existedUserVoting);
					return new ResponseEntity<String>(
							String.format(SUCCESSFULLY_CHANGED_RESTAURANT_MSG, username, restaurant.getName()), 
							HttpStatus.OK);
				} else {
					return new ResponseEntity<String>(
							String.format(LATELY_CHANGED_RESTAURANT_MSG, username), 
							HttpStatus.OK);
				}				
			}
		}
		return new ResponseEntity<String>(NOTHING_TO_UPDATE_MSG, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/restaurant/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant) {
		Restaurant fetchedRestaurant = restaurantRepository.findByName(restaurant.getName());
		OperationResult operationResult = new OperationResult();
		if (fetchedRestaurant != null) {			
			operationResult.setStatus(Status.ERRROR);
			operationResult.setMessage(DUPLICATE_NAME_MSG);
			return new ResponseEntity<OperationResult>(operationResult, HttpStatus.BAD_REQUEST);			
		} else {
			restaurantRepository.save(restaurant);
			operationResult.setStatus(Status.SUCCESS);
			operationResult.setMessage(ADDED_RESTAURTANT_MSG);
			return new ResponseEntity<OperationResult>(operationResult, HttpStatus.OK);			
		}
		
	}
	
	@RequestMapping(value = "/restaurant/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteRestaurant(@RequestBody Vote voteRestaurantToDelete) {
		Long id = voteRestaurantToDelete.getRestaurantId();
		OperationResult operationResult = new OperationResult();
		if (restaurantRepository.exists(id)) {		
			restaurantRepository.delete(id);
			operationResult.setStatus(Status.SUCCESS);
			operationResult.setMessage(REMOVED_RESTAURTANT_MSG);
			return new ResponseEntity<OperationResult>(operationResult, HttpStatus.OK);						
		} else {
			operationResult.setStatus(Status.ERRROR);
			operationResult.setMessage(WRONG_RESTAURANT_MSG);
			return new ResponseEntity<OperationResult>(operationResult, HttpStatus.BAD_REQUEST);						
		}		
	}
	
	@RequestMapping(value = "/restaurant/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateRestaurant(@RequestBody Restaurant restaurant) {		
		Long id = restaurant.getRestaurantId();		
		OperationResult operationResult = new OperationResult();
		if (restaurantRepository.exists(id)) {			
			restaurantRepository.save(restaurant);			
			operationResult.setStatus(Status.SUCCESS);
			operationResult.setMessage(UPDATED_RESTAURTANT_MSG);
			return new ResponseEntity<OperationResult>(operationResult, HttpStatus.OK);			
		} else {			
			operationResult.setStatus(Status.ERRROR);
			operationResult.setMessage(WRONG_RESTAURANT_MSG);
			return new ResponseEntity<OperationResult>(operationResult, HttpStatus.BAD_REQUEST);											
		}		
	}
	
	
//
//	@RequestMapping(value = "/vote", method = RequestMethod.GET/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
//	@ResponseBody
//	public ResponseEntity<?> voteForRestaurant(@RequestBody Vote vote) {
//		
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if (principal != null && principal instanceof UserDetails) {
//			UserDetails userDetails = (UserDetails)principal;
//			System.out.println("user>>>>>>>>>" + userDetails.getUsername());
//		
//			
//		}
//		System.out.println("vote>>>>>>>>>" + vote.getRestaurantId());
//		
//	        
//		Iterable<Restaurant> restaurants = restaurantRepository.findAll();
//		List<Restaurant> respBody = new LinkedList<Restaurant>();
//		for (Restaurant r : restaurants) {
//			respBody.add(r);
//		}
//		ResponseEntity<List<Restaurant>> usersResp = new ResponseEntity<List<Restaurant>>(respBody, HttpStatus.OK);		
//		return usersResp;
//	}
	
	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> collectVotes() { 
		Iterable<Voting> votesIterable = votingRepository.findAll();
		Set<Voting> votes = new HashSet<Voting>();
		votesIterable.forEach( voting -> votes.add(voting));				
		return new ResponseEntity<Set<Voting>>(votes, HttpStatus.OK);					
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
			return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);			
		} else {
			return new ResponseEntity<String>(NO_VOTES_MSG, HttpStatus.OK);			
		}												
	}
	
//	private static class VotesComparator implements Comparator<Voting> {
//
//		@Override
//		public int compare(Voting arg0, Voting arg1) {			
//			return arg0.;
//		}
//		
//	}

	
}
