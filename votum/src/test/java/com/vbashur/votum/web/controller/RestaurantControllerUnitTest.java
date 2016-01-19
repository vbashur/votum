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
			ObjectMapper mapper = new ObjectMapper();
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

		ObjectMapper mapper = new ObjectMapper();
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

	@Override
	public void cleanupRepositories() {
		restaurantRepository.deleteAll();
	}

}
