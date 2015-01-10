package com.datapine.service;

import java.util.List;

import com.datapine.domain.User;

public interface UserService {

	User register(String email, String password);
	
	void unregister(Long userId);

	User updatePassword(Long userId, String newPassword);

	List<User> listUsers(); 
}
