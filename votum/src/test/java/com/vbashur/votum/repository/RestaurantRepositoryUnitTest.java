package com.vbashur.votum.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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
import com.vbashur.votum.domain.Meal;
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
		
		Meal exoticFood1 = new Meal();
		exoticFood1.setName("bugs");
		exoticFood1.setDescription("deep fried");
		exoticFood1.setPrice(1.15F);
		
		Meal exoticFood2 = new Meal();
		exoticFood1.setName("kimchi");
		exoticFood1.setDescription("korean spicy cabbage");
		exoticFood1.setPrice(2.50F);
		Set<Meal> exoticMenu = new HashSet<Meal>();
		exoticMenu.add(exoticFood1);
		exoticMenu.add(exoticFood2);
		exoticRestaurant.setMeals(exoticMenu);			
		
		assertNull(exoticRestaurant.getRestaurantId());	
		restaurantRepository.save(exoticRestaurant);					
		assertNotNull(exoticRestaurant.getRestaurantId());		
				
		Restaurant fetchedRestaurant1 = restaurantRepository.findOne(traditionalRestaurant.getRestaurantId());
		assertNotNull(fetchedRestaurant1);
		assertTrue(fetchedRestaurant1.getName().equals(traditionalRestaurantName));
		
		
		Restaurant fetchedRestaurant2 = restaurantRepository.findOne(exoticRestaurant.getRestaurantId());
		assertNotNull(fetchedRestaurant2);
		assertTrue(fetchedRestaurant2.getName().equals(exoticRestaurantName));
		assertNotNull(fetchedRestaurant2.getMeals());
		System.out.println(fetchedRestaurant2.getMeals().size());
		System.out.println(fetchedRestaurant1.getMeals().size());
		assertTrue(fetchedRestaurant2.getMeals().size() == 2);
		
	}
}
