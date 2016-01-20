package com.vbashur.votum.web.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbashur.votum.config.WebConfiguration;
import com.vbashur.votum.domain.Restaurant;
import com.vbashur.votum.domain.Voting;
import com.vbashur.votum.repository.BaseRepositoryUnitTest;
import com.vbashur.votum.repository.RestaurantRepository;
import com.vbashur.votum.repository.VotingRepository;
import com.vbashur.votum.web.data.Status;
import com.vbashur.votum.web.data.Vote;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { WebConfiguration.class })
public class RestaurantControllerUnitTest extends BaseRepositoryUnitTest {

	private MockMvc mockMvc;

	@Mock
	RestaurantRepository restaurantRepository;

	@Mock
	private VotingRepository votingRepository;

	@InjectMocks
	private RestaurantController controller;

	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListSuccess() {
		try {
			Restaurant exoticRestaurant = createExoticRestaurant();
			Restaurant traditionalRestaurant = createTraditionalRestaurant();
			when(restaurantRepository.findAll()).thenReturn(Arrays.asList(exoticRestaurant, traditionalRestaurant));
			mockMvc.perform(get("/list")).andExpect(status().isOk());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testVoteError() {
		try {
			Vote wrongVote = new Vote();
			wrongVote.setRestaurantId(Long.MAX_VALUE);
			when(restaurantRepository.findOne(Long.MAX_VALUE)).thenReturn(null);
			MvcResult wrongIdVoteRes = mockMvc.perform(
					post("/vote").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(wrongVote)))
					.andExpect(status().isOk()).andReturn();
			String wrongIdVoteResponseString = wrongIdVoteRes.getResponse().getContentAsString();
			assertTrue(wrongIdVoteResponseString.contains(Status.FAILED.getStatusValue()));
			assertTrue(wrongIdVoteResponseString.contains(RestaurantController.WRONG_RESTAURANT_MSG));

			LocalTime currentTime = LocalTime.now();
			if (currentTime.isAfter(RestaurantController.DEADLINE)) {

				Vote correctVote = new Vote();
				correctVote.setRestaurantId(Long.MIN_VALUE);
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantId(Long.MIN_VALUE);

				when(restaurantRepository.findOne(Long.MIN_VALUE)).thenReturn(restaurant);

				Restaurant votingRestaurant = new Restaurant();
				votingRestaurant.setRestaurantId(Long.MAX_VALUE);
				Voting outdatedVoting = new Voting();
				outdatedVoting.setRestaurant(votingRestaurant);

				when(votingRepository.findByUser("admin")).thenReturn(outdatedVoting);

				MvcResult outdatedVoteRes = mockMvc
						.perform(post("/vote").contentType(MediaType.APPLICATION_JSON)
								.content(mapper.writeValueAsString(correctVote)))
						.andExpect(status().isOk()).andReturn();
				String outdatedVoteResString = outdatedVoteRes.getResponse().getContentAsString();
				assertTrue(outdatedVoteResString.contains(Status.FAILED.getStatusValue()));
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testVoteNone() {

		Vote correctVote = new Vote();
		correctVote.setRestaurantId(Long.MIN_VALUE);
		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(Long.MIN_VALUE);

		when(restaurantRepository.findOne(Long.MIN_VALUE)).thenReturn(restaurant);

		Restaurant votingRestaurant = new Restaurant();
		votingRestaurant.setRestaurantId(Long.MIN_VALUE);
		Voting identicalVoting = new Voting();
		identicalVoting.setRestaurant(votingRestaurant);
		when(votingRepository.findByUser("admin")).thenReturn(identicalVoting);

		try {
			MvcResult outdatedVoteRes = mockMvc.perform(post("/vote").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(correctVote))).andExpect(status().isOk()).andReturn();

			String outdatedVoteResString = outdatedVoteRes.getResponse().getContentAsString();
			assertTrue(outdatedVoteResString.contains(Status.IGNORED.getStatusValue()));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testVoteSuccess() {
		
		Vote correctVote = new Vote();
		correctVote.setRestaurantId(Long.MIN_VALUE);
		Restaurant restaurant = new Restaurant();
		restaurant.setRestaurantId(Long.MIN_VALUE);

		when(restaurantRepository.findOne(Long.MIN_VALUE)).thenReturn(restaurant);
		
		when(votingRepository.findByUser("admin")).thenReturn(null);

		
		try {
			MvcResult successVoteRes = mockMvc.perform(post("/vote").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(correctVote))).andExpect(status().isOk()).andReturn();

			String outdatedVoteResString = successVoteRes.getResponse().getContentAsString();
			assertTrue(outdatedVoteResString.contains(Status.SUCCESS.getStatusValue()));
		} catch (Exception e) {
			fail();
		}
		
		LocalTime currentTime = LocalTime.now();
		if (currentTime.isBefore(RestaurantController.DEADLINE)) {
			Restaurant votingRestaurant = new Restaurant();
			votingRestaurant.setRestaurantId(Long.MIN_VALUE);
			Voting identicalVoting = new Voting();
			identicalVoting.setRestaurant(votingRestaurant);
			when(votingRepository.findByUser("admin")).thenReturn(identicalVoting);
			
			try {
				MvcResult actualVoteRes = mockMvc.perform(post("/vote").contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(correctVote))).andExpect(status().isOk()).andReturn();

				String outdatedVoteResString = actualVoteRes.getResponse().getContentAsString();
				assertTrue(outdatedVoteResString.contains(Status.SUCCESS.getStatusValue()));
			} catch (Exception e) {
				fail();
			}

			
		}
	}
	
	@Test
	public void testAddRestaurantSuccess() {
		Restaurant restaurant = createExoticRestaurant();	
		when(restaurantRepository.findByName(Mockito.anyString())).thenReturn(null);
		
		try {
			MvcResult addRestaurantResponse = mockMvc.perform(post("/restaurant/add").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(restaurant))).andExpect(status().isOk()).andReturn();

			String responseContent = addRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.SUCCESS.getStatusValue()));
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testAddRestaurantAlreadyExists() {
		Restaurant restaurant = createExoticRestaurant();		
		when(restaurantRepository.findByName(Mockito.anyString())).thenReturn(restaurant);
		
		try {
			MvcResult addRestaurantResponse = mockMvc.perform(post("/restaurant/add").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(restaurant))).andExpect(status().isOk()).andReturn();

			String responseContent = addRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.IGNORED.getStatusValue()));
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testDeleteRestaurantSuccess() {		
		
		when(restaurantRepository.exists(Long.MAX_VALUE)).thenReturn(true);		
		Vote vote = new Vote();
		vote.setRestaurantId(Long.MAX_VALUE);		
		try {
			MvcResult deleteRestaurantResponse = mockMvc.perform(post("/restaurant/delete").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(vote))).andExpect(status().isOk()).andReturn();

			String responseContent = deleteRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.SUCCESS.getStatusValue()));
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testDeleteRestaurantFail() {		
		
		when(restaurantRepository.exists(Mockito.anyLong())).thenReturn(false);		
		Vote vote = new Vote();
		vote.setRestaurantId(Long.MAX_VALUE);		
		try {
			MvcResult deleteRestaurantResponse = mockMvc.perform(post("/restaurant/delete").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(vote))).andExpect(status().isOk()).andReturn();

			String responseContent = deleteRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.FAILED.getStatusValue()));
		} catch (Exception e) {
			fail();
		}				
	}
	

	@Test
	public void testUpdateRestaurantSuccess() {		
		
		Restaurant restaurant = createExoticRestaurant();
		when(restaurantRepository.exists(Mockito.anyLong())).thenReturn(true);					
		try {
			MvcResult updateRestaurantResponse = mockMvc.perform(post("/restaurant/update").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(restaurant))).andExpect(status().isOk()).andReturn();

			String responseContent = updateRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.SUCCESS.getStatusValue()));
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testUpdateRestaurantFail() {		
		
		Restaurant restaurant = createExoticRestaurant();
		when(restaurantRepository.exists(Mockito.anyLong())).thenReturn(false);		
		try {
			MvcResult updateRestaurantResponse = mockMvc.perform(post("/restaurant/delete").contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(restaurant))).andExpect(status().isOk()).andReturn();

			String responseContent = updateRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.FAILED.getStatusValue()));
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testCollect() {
		Restaurant exoticRestaurant = createExoticRestaurant();
		Restaurant traditionalRestaurant = createTraditionalRestaurant();
		Voting voting1 = new Voting();
		voting1.setRestaurant(traditionalRestaurant);
		voting1.setUser("user1");
		
		Voting voting2 = new Voting();
		voting2.setRestaurant(exoticRestaurant);
		voting2.setUser("user2");
		when(votingRepository.findAll()).thenReturn(Arrays.asList(voting1, voting2));
		try {
			MvcResult addRestaurantResponse = mockMvc.perform(get("/collect")).andExpect(status().isOk()).andReturn();
			String responseContent = addRestaurantResponse.getResponse().getContentAsString();
			assertTrue(responseContent.contains(Status.SUCCESS.getStatusValue()));			
		} catch (Exception e) {
			fail();
		}		
		
	}
	

	@Override
	public void cleanupRepositories() {
		restaurantRepository.deleteAll();
	}

}
