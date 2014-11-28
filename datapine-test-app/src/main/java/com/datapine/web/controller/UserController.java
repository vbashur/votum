package com.datapine.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datapine.domain.User;
import com.datapine.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user) {
		userService.register(user.getEmail(), user.getPassword());
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
	public String listUsers(Map<String, Object> map) {
		map.put("user", new User());		
		map.put("userList", userService.listUsers());
		return "index";
	}

	@RequestMapping("/modify/{userId}/{newPassword}")	
	public String updateUser(@PathVariable("userId") Long userId, @PathVariable("newPassword") String newPassword) {
		userService.updatePassword(userId, newPassword);		
		return "redirect:/index";
	}
	
	@RequestMapping("/delete/{userId}")	
	public String deleteUser(@PathVariable("userId") Long userId) {
		userService.unregister(userId);		
		return "redirect:/index";
	}

}
