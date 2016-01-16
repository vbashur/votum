package com.vbashur.votum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/").permitAll()
			.and()
			.authorizeRequests().antMatchers("/console/**").permitAll()
			.and()
			.authorizeRequests().antMatchers("/users").permitAll()
			.and()
			.authorizeRequests().antMatchers("/list").permitAll();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
	}
	
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
			.withUser("user1").password("user1").roles("USER")
			.and()
			.withUser("user2").password("user2").roles("USER")
			.and()
			.withUser("user3").password("user3").roles("USER")
			.and()
			.withUser("user4").password("user4").roles("USER")
			.and()
			.withUser("user5").password("user5").roles("USER")
			.and()
			.withUser("admin").password("admin").roles("USER", "ADMIN");
	}

}