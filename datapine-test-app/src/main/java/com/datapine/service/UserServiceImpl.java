package com.datapine.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datapine.dao.RoleDAO;
import com.datapine.dao.UserDAO;
import com.datapine.domain.Role;
import com.datapine.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	RoleDAO roleDAO;
	
	public final static String DEFAULT_USER_ROLE = "ROLE_USER"; // just to simplify application logic
	
	@Override
	public User register(String email, String password) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setPassword(password);		
		userDAO.save(newUser);
		
		Role role = new Role();
		role.setEmail(newUser.getEmail());
		role.setRole(DEFAULT_USER_ROLE);
		roleDAO.add(role);
		return newUser;
	}

	@Override
	public User updatePassword(Long userId,	String newPassword) {
		User updatedUser = userDAO.findById(userId);
		if (updatedUser != null) {
			updatedUser.setPassword(newPassword);
			userDAO.update(updatedUser);
		}
		return updatedUser;
	}
	
	@Override
	public List<User> listUsers() {
		List<User> users = new LinkedList<User>();
		Iterator<User> iter = userDAO.findAllOrderById();
		while(iter.hasNext()) {
			users.add(iter.next());
		}
		return users;
	}

	@Override
	public void unregister(Long userId) {
		User user = userDAO.findById(userId);
		if (user != null) {
			roleDAO.delete(user.getEmail());
		}
		userDAO.delete(userId);		
	}

}
