package com.vbashur.votum.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.repository.RestaurantRepository;

@Component
public class InitialDBDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private Logger log = Logger.getLogger(InitialDBDataLoader.class);
	
	@Autowired
	RestaurantRepository restaurantRepository;
			
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("admin", "admin", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
		
		Restaurant traditionalRestaurant = new Restaurant();
		traditionalRestaurant.setName("Old good times");
		traditionalRestaurant.setAddress("Aschheim, Einsteinring 29");
		traditionalRestaurant.setDescription("Traditional german food for average price");
		traditionalRestaurant.setEmail("traditional@mail.de");
		restaurantRepository.save(traditionalRestaurant);
		log.info(String.format("Saved Restaurant - id: %s - name: %s", traditionalRestaurant.getRestaurantId(), traditionalRestaurant.getName()));
		
		Restaurant exoticRestaurant = new Restaurant();
		exoticRestaurant.setName("Exxxotica!");
		exoticRestaurant.setAddress("Munich, Sohnenstrasse 15");
		exoticRestaurant.setDescription("Fried insects, banana beer, sweet octopus and many more");
		exoticRestaurant.setEmail("exxxotica@mail.de");
		restaurantRepository.save(exoticRestaurant);
		log.info(String.format("Saved Restaurant - id: %s - name: %s", exoticRestaurant.getRestaurantId(), exoticRestaurant.getName()));
		
		Restaurant luxeryRestaurant = new Restaurant();
		luxeryRestaurant.setName("Sir Karabas Barabas");
		luxeryRestaurant.setAddress("Augsburg, Rosenheimerplatz 2");
		luxeryRestaurant.setDescription("We bring our Gourmet Restaurant to your doorstep");
		luxeryRestaurant.setEmail("karabas@mail.de");
		restaurantRepository.save(luxeryRestaurant);
		log.info(String.format("Saved Restaurant - id: %s - name: %s", luxeryRestaurant.getRestaurantId(), luxeryRestaurant.getName()));
		
		SecurityContextHolder.clearContext();

	}

}
