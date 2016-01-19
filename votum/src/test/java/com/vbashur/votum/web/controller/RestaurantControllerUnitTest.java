package com.vbashur.votum.web.controller;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.vbashur.votum.config.WebConfiguration;
import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.repository.BaseRepositoryUnitTest;
import com.vbashur.votum.repository.RestaurantRepository;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {WebConfiguration.class})
public class RestaurantControllerUnitTest extends BaseRepositoryUnitTest {

	private MockMvc mockMvc;
	
	@Mock
	RestaurantRepository restaurantRepository;

	@InjectMocks
	private RestaurantController controller;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	
	@Test
	public void testListuccess() {
		try {
			Restaurant exoticRestaurant = createExoticRestaurant();
			Restaurant traditionalRestaurant = createTraditionalRestaurant();			
			when(restaurantRepository.findAll()).thenReturn(Arrays.asList(exoticRestaurant, traditionalRestaurant)) ;
			mockMvc.perform(get("/list")).andExpect(status().isOk());
		} catch (Exception e) {
			fail();
		}					
	}


	@Override
	public void cleanupRepositories() {
		restaurantRepository.deleteAll();		
	}
	

}
