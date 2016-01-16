package com.vbashur.votum.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class RestaurantRepositoryUnitTest {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Before
	public void setup() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER")));
	}
	
	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	public void testSaveRestaurant() {
		
		Restaurant traditionalRestaurant = new Restaurant();		
		String traditionalRestaurantName = "Old good times";
		traditionalRestaurant.setName(traditionalRestaurantName);
		traditionalRestaurant.setAddress("Aschheim, Einsteinring 29");
		traditionalRestaurant.setDescription("Traditional german food for average price");
		traditionalRestaurant.setEmail("traditional@mail.de");

		assertTrue( restaurantRepository.count() == 0);				
		assertNull(traditionalRestaurant.getRestaurantId());
		restaurantRepository.save(traditionalRestaurant);
		assertNotNull(traditionalRestaurant.getRestaurantId());		
		assertTrue(restaurantRepository.findAll().iterator().hasNext());
		
		Restaurant exoticRestaurant = new Restaurant();
		String exoticRestaurantName = "Exxxotica!";
		exoticRestaurant.setName(exoticRestaurantName);
		exoticRestaurant.setAddress("Munich, Sohnenstrasse 15");
		exoticRestaurant.setDescription("Fried insects, banana beer, sweet octopus and many more");
		exoticRestaurant.setEmail("exxxotica@mail.de");
		
		assertNull(exoticRestaurant.getRestaurantId());	
		restaurantRepository.save(exoticRestaurant);					
		assertNotNull(exoticRestaurant.getRestaurantId());		
		
		
		Restaurant fetchedRestaurant1 = restaurantRepository.findOne(traditionalRestaurant.getRestaurantId());
		assertNotNull(fetchedRestaurant1);
		assertTrue(fetchedRestaurant1.getName().equals(traditionalRestaurantName));
		
		
		Restaurant fetchedRestaurant2 = restaurantRepository.findOne(exoticRestaurant.getRestaurantId());
		assertNotNull(fetchedRestaurant2);
		assertTrue(fetchedRestaurant2.getName().equals(exoticRestaurantName));
		
	}
}
