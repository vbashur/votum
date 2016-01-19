package com.vbashur.votum.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vbashur.votum.config.RepositoryConfiguration;
import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.domain.Voting;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { RepositoryConfiguration.class })
public class VotingRepositoryUnitTest extends BaseRepositoryUnitTest {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private VotingRepository votingRepository;

	@Test
	public void testSaveVoting() {
		
		Restaurant traditionalRestaurant = createTraditionalRestaurant();
		
		assertTrue( restaurantRepository.count() == 0);				
		assertNull(traditionalRestaurant.getRestaurantId());
		restaurantRepository.save(traditionalRestaurant);
		assertNotNull(traditionalRestaurant.getRestaurantId());
		
		Iterator<Restaurant> restaurantIter = restaurantRepository.findAll().iterator();
		assertTrue(restaurantIter.hasNext());		
		
		Voting adminVoting = new Voting();
		adminVoting.setUser("admin");
		adminVoting.setRestaurant(restaurantIter.next());		
		votingRepository.save(adminVoting);
		assertNotNull(adminVoting.getVotingId());
		
		Iterator<Voting> votingIter = votingRepository.findAll().iterator();
		assertTrue(votingIter.hasNext());
		
		restaurantRepository.delete(traditionalRestaurant.getRestaurantId());
		restaurantIter = restaurantRepository.findAll().iterator();
		assertFalse(restaurantIter.hasNext());
		
		votingIter = votingRepository.findAll().iterator();
		assertFalse(votingIter.hasNext());
	
	}
	
	@Test
	public void testFindByUser() {
		
		Restaurant traditionalRestaurant = createTraditionalRestaurant();		
		restaurantRepository.save(traditionalRestaurant);			

		Restaurant exoticRestaurant = createExoticRestaurant();		
		restaurantRepository.save(exoticRestaurant);				
		
		Voting user1Voting = new Voting();
		user1Voting.setUser("user1");
		user1Voting.setRestaurant(traditionalRestaurant);
		votingRepository.save(user1Voting);		
		
		Voting user2Voting = new Voting();
		user2Voting.setUser("user2");
		user2Voting.setRestaurant(exoticRestaurant);
		votingRepository.save(user2Voting);
			
		Voting user1FetchedVoting = votingRepository.findByUser("user1");
		assertEquals(user1Voting.getRestaurant().getRestaurantId(), user1FetchedVoting.getRestaurant().getRestaurantId());
		
		Voting user2FetchedVoting = votingRepository.findByUser("user2");
		assertEquals(user2Voting.getRestaurant().getRestaurantId(), user2FetchedVoting.getRestaurant().getRestaurantId());
		
		Voting dummyFetchedVoting = votingRepository.findByUser("user3");
		assertNull(dummyFetchedVoting);
	}

	@Override
	public void cleanupRepositories() {		
		restaurantRepository.deleteAll();
		votingRepository.deleteAll();					
	}
	
	
}
