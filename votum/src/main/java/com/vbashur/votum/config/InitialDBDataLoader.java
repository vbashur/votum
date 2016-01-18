package com.vbashur.votum.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vbashur.votum.domain.Meal;
import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.repository.MealRepository;
import com.vbashur.votum.repository.RestaurantRepository;

@Component
public class InitialDBDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private Logger log = Logger.getLogger(InitialDBDataLoader.class);
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	MealRepository mealRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
		
		Restaurant traditionalRestaurant = new Restaurant();
		traditionalRestaurant.setName("Old good times");
		traditionalRestaurant.setAddress("Aschheim, Einsteinring 29");
		traditionalRestaurant.setDescription("Traditional german food for average price");
		traditionalRestaurant.setEmail("traditional@mail.de");
		
		Meal oldSchoolFood1 = new Meal();
		oldSchoolFood1.setName("Pommes");
		oldSchoolFood1.setDescription("Traditional german meal");
		oldSchoolFood1.setPrice(1F);		
		
		Meal oldSchoolFood2 = new Meal();
		oldSchoolFood2.setName("Bayern sausages");
		oldSchoolFood2.setDescription("Das ist lecker!!");
		oldSchoolFood2.setPrice(3F);	
		
		Set<Meal> oldSchoolMenu = new HashSet<Meal>();
		oldSchoolMenu.add(oldSchoolFood1);
		oldSchoolMenu.add(oldSchoolFood2);
		traditionalRestaurant.setMeals(oldSchoolMenu);
		restaurantRepository.save(traditionalRestaurant);
		log.info(String.format("Saved Restaurant - id: %s - name: %s", traditionalRestaurant.getRestaurantId(), traditionalRestaurant.getName()));
		
		Restaurant exoticRestaurant = new Restaurant();
		exoticRestaurant.setName("Exxxotica!");
		exoticRestaurant.setAddress("Munich, Sohnenstrasse 15");
		exoticRestaurant.setDescription("Fried insects, banana beer, sweet octopus and many more");
		exoticRestaurant.setEmail("exxxotica@mail.de");
		
		Meal exoticFood1 = new Meal();
		exoticFood1.setName("bugs");
		exoticFood1.setDescription("deep fried");
		exoticFood1.setPrice(7.15F);		
		
		Meal exoticFood2 = new Meal();
		exoticFood2.setName("kimchi");
		exoticFood2.setDescription("korean spicy cabbage");
		exoticFood2.setPrice(2.50F);
		Set<Meal> exoticMenu = new HashSet<Meal>();
		exoticMenu.add(exoticFood1);
		exoticMenu.add(exoticFood2);
		exoticRestaurant.setMeals(exoticMenu);		
		restaurantRepository.save(exoticRestaurant);
		log.info(String.format("Saved Restaurant - id: %s - name: %s", exoticRestaurant.getRestaurantId(), exoticRestaurant.getName()));
		
		Restaurant luxeryRestaurant = new Restaurant();
		luxeryRestaurant.setName("Sir Karabas Barabas");
		luxeryRestaurant.setAddress("Augsburg, Rosenheimerplatz 2");
		luxeryRestaurant.setDescription("We bring our Gourmet Restaurant to your doorstep");
		luxeryRestaurant.setEmail("karabas@mail.de");
		
		Meal luxeryFood1 = new Meal();
		luxeryFood1.setName("carbonara de la penna");
		luxeryFood1.setDescription("Made by Master Paolo from Florenzia with love");
		luxeryFood1.setPrice(89F);		
			
		Set<Meal> luxeryMenu = new HashSet<Meal>();
		luxeryMenu.add(luxeryFood1);
		luxeryRestaurant.setMeals(luxeryMenu);
		
		restaurantRepository.save(luxeryRestaurant);
		log.info(String.format("Saved Restaurant - id: %s - name: %s", luxeryRestaurant.getRestaurantId(), luxeryRestaurant.getName()));
		
		SecurityContextHolder.clearContext();

	}

}
