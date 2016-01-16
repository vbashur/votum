package com.vbashur.votum.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vbashur.votum.config.DBConfiguration;
import com.vbashur.votum.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DBConfiguration.class})
@Ignore
public class UserRepositoryUnitTest {
	

	@Autowired
	private UserRepository repository;
	
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
	public void testSaveUser() {
		User user = new User();
		user.setEmail("admin@admin.me");
		user.setPassword("admin");
		User newUser = repository.save(user);
		assertNotNull(newUser.getId());
	}

}
