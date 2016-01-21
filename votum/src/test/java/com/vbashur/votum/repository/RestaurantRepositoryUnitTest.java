package com.vbashur.votum.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vbashur.votum.config.BaseVotingUnitTest;
import com.vbashur.votum.config.RepositoryConfiguration;
import com.vbashur.votum.domain.Meal;
import com.vbashur.votum.domain.Restaurant;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class RestaurantRepositoryUnitTest extends BaseVotingUnitTest {

	@Autowired
	private RestaurantRepository restaurantRepository;	
	
	@Test
	public void testSaveRestaurant() {
		
		Restaurant traditionalRestaurant = createTraditionalRestaurant();

		assertTrue( restaurantRepository.count() == 0);				
		assertNull(traditionalRestaurant.getRestaurantId());
		restaurantRepository.save(traditionalRestaurant);
		assertNotNull(traditionalRestaurant.getRestaurantId());		
		assertTrue(restaurantRepository.findAll().iterator().hasNext());
		
		Restaurant exoticRestaurant = createExoticRestaurant();		
		Meal exoticFood1 = createBugMeal();		
		Meal exoticFood2 = createKimchiMeal();
		Set<Meal> exoticMenu = new HashSet<Meal>();
		exoticMenu.add(exoticFood1);
		exoticMenu.add(exoticFood2);
		exoticRestaurant.setMeals(exoticMenu);			
		
		assertNull(exoticRestaurant.getRestaurantId());	
		restaurantRepository.save(exoticRestaurant);					
		assertNotNull(exoticRestaurant.getRestaurantId());		
				
		Restaurant fetchedRestaurant1 = restaurantRepository.findOne(traditionalRestaurant.getRestaurantId());
		assertNotNull(fetchedRestaurant1);
		assertTrue(fetchedRestaurant1.getName().equals(traditionalRestaurant.getName()));
		
		
		Restaurant fetchedRestaurant2 = restaurantRepository.findOne(exoticRestaurant.getRestaurantId());
		assertNotNull(fetchedRestaurant2);
		assertTrue(fetchedRestaurant2.getName().equals(exoticRestaurant.getName()));
		assertTrue(fetchedRestaurant1.getMeals().size() == 0);
		assertNotNull(fetchedRestaurant2.getMeals());		
		assertTrue(fetchedRestaurant2.getMeals().size() == 2);							
	}
		
	@Test
	public void testFindByRestaurantName() {
		Restaurant traditionalRestaurant = createTraditionalRestaurant();
		Meal kimchi = createKimchiMeal();
		Set<Meal> menu1 = new HashSet<Meal>();
		menu1.add(kimchi);		
		traditionalRestaurant.setMeals(Collections.unmodifiableSet(menu1));
		restaurantRepository.save(traditionalRestaurant);
		
		Restaurant exoticRestaurant = createExoticRestaurant();
		Meal bugs = createBugMeal();
		Set<Meal> menu2 = new HashSet<Meal>();
		menu2.add(bugs);
		exoticRestaurant.setMeals(Collections.unmodifiableSet(menu2));		
		restaurantRepository.save(exoticRestaurant);				
		
		Restaurant fetchedExoticRestaurant = restaurantRepository.findByName(exoticRestaurant.getName());
		assertNotNull(fetchedExoticRestaurant);
		assertEquals(exoticRestaurant.getAddress(), fetchedExoticRestaurant.getAddress());
		assertEquals(exoticRestaurant.getName(), fetchedExoticRestaurant.getName());
		assertEquals(exoticRestaurant.getDescription(), fetchedExoticRestaurant.getDescription());
		assertEquals(exoticRestaurant.getEmail(), fetchedExoticRestaurant.getEmail());
		assertEquals(exoticRestaurant.getMeals().size(), fetchedExoticRestaurant.getMeals().size());		
		assertEquals(exoticRestaurant.getMeals().iterator().next(), fetchedExoticRestaurant.getMeals().iterator().next());		
		
				
	}

	@Override
	public void cleanupRepositories() {
		restaurantRepository.deleteAll();		
	}
	
}
