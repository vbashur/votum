package com.datapine.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datapine.dao.RoleDAO;
import com.datapine.dao.UserDAO;
import com.datapine.domain.Role;
import com.datapine.domain.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	// prevents duplicate users creation
	private Set<String> emailCache = new HashSet<String>();
		
	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	public final static String DEFAULT_USER_ROLE = "ROLE_USER"; // just to
																// simplify
																// application
																// logic

	@Override
	public User register(String email, String password) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setPassword(password);
		if (!emailCache.contains(email)) {
			userDAO.save(newUser);
			Role role = new Role();
			role.setEmail(email);
			role.setRole(DEFAULT_USER_ROLE);
			roleDAO.add(role);
			emailCache.add(email);
			log.info("User with email " + newUser.getEmail() + " is successfully registered");
		} else {
			log.warn("User with email " + email + " has been already registered");
		}
		return newUser;
	}

	@Override
	public User updatePassword(Long userId, String newPassword) {
		User updatedUser = null;
		try {
			updatedUser = userDAO.findById(userId);
			if (updatedUser != null) {
				updatedUser.setPassword(newPassword);
				userDAO.update(updatedUser);
				log.info("User with email " + updatedUser.getEmail() + " is successfully updated");
			}
		} catch (NoResultException nre) { 
			log.warn("User with Id " + userId + " is not registered");
		} catch (NonUniqueResultException nre) {
			log.warn("User with Id " + userId + " is not unique and cannot be processed");
		}
		return updatedUser;
	}

	@Override
	public List<User> listUsers() {
		List<User> users = new LinkedList<User>();
		emailCache.clear();
		Iterator<User> iter = userDAO.findAllOrderById();
		while(iter.hasNext()) {
			User user = iter.next();
			users.add(user);
			emailCache.add(user.getEmail());		
		}		
		return users;
	}

	@Override
	public void unregister(Long userId) {
		User user = null;
		try {
			user = userDAO.findById(userId);
			if (user != null) {
				String email = user.getEmail();
				roleDAO.delete(email);
				userDAO.delete(userId);
				emailCache.remove(email);
				log.info("User with email " + email + " is successfully deleted");
			}
		} catch (NoResultException nre) {
			log.warn("Required user is not registered");
		} catch (NonUniqueResultException nre) {
			log.warn("Required user is not unique and cannot be processed");
		}
	}	

}
