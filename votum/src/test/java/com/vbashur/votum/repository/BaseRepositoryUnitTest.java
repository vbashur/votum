package com.vbashur.votum.repository;

import org.junit.After;
import org.junit.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.vbashur.votum.domain.Meal;
import com.vbashur.votum.domain.Restaurant;

public abstract class BaseRepositoryUnitTest {

	@Before
	public void setup() {
		User userDetails = new User("admin", "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"));
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
				userDetails, 
				"admin",
				AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER")));
	}

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();	
		cleanupRepositories();
	}

	public abstract void cleanupRepositories();
	
	public Restaurant createTraditionalRestaurant() {
		Restaurant traditionalRestaurant = new Restaurant();
		String traditionalRestaurantName = "Old good times";
		traditionalRestaurant.setName(traditionalRestaurantName);
		traditionalRestaurant.setAddress("Aschheim, Einsteinring 29");
		traditionalRestaurant.setDescription("Traditional german food for average price");
		traditionalRestaurant.setEmail("traditional@mail.de");
		return traditionalRestaurant;
	}

	public Restaurant createExoticRestaurant() {
		Restaurant exoticRestaurant = new Restaurant();
		String exoticRestaurantName = "Exxxotica!";
		exoticRestaurant.setName(exoticRestaurantName);
		exoticRestaurant.setAddress("Munich, Sohnenstrasse 15");
		exoticRestaurant.setDescription("Fried insects, banana beer, sweet octopus and many more");
		exoticRestaurant.setEmail("exxxotica@mail.de");
		return exoticRestaurant;
	}

	public Meal createBugMeal() {
		Meal meal = new Meal();
		meal.setName("bugs");
		meal.setDescription("deep fried");
		meal.setPrice(1.15F);
		return meal;
	}

	public Meal createKimchiMeal() {
		Meal kimchi = new Meal();
		kimchi.setName("kimchi");
		kimchi.setDescription("korean spicy cabbage");
		kimchi.setPrice(2.50F);
		return kimchi;

	}
}
