package com.vbashur.votum.web.controller;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vbashur.votum.domain.User;
import com.vbashur.votum.repository.UserRepository;

@Controller
public class UserController {

	// UserRepository userService;
	//
	// @RequestMapping(value = "/add", method = RequestMethod.POST)
	// public String addUser(@ModelAttribute("user") User user) {
	// userService.register(user.getEmail(), user.getPassword());
	// return "redirect:/users";
	// }
	//
	// @RequestMapping("/users")
	// public String listUsers(Map<String, Object> map) {
	// map.put("user", new User());
	// map.put("userList", userService.listUsers());
	// return "users";
	// }
	//
	// @RequestMapping("/modify/{userId}/{newPassword}")
	// public String updateUser(@PathVariable("userId") Long userId,
	// @PathVariable("newPassword") String newPassword) {
	// userService.updatePassword(userId, newPassword);
	// return "redirect:/users";
	// }
	//
	// @RequestMapping("/delete/{userId}")
	// public String deleteUser(@PathVariable("userId") Long userId) {
	// userService.unregister(userId);
	// return "redirect:/users";
	// }

	@Autowired
	private UserRepository userRepository;
//
//	/**
//	 * GET / -> show the index page.
//	 */
//	@RequestMapping("/")
//	public String index() {
//		return "index.html";
//	}
//
//	/**
//	 * POST /user -> create a new user.
//	 *
//	 * @param user
//	 *            A json object representing the user.
//	 * @return An http 2xx status in case of success.
//	 */
//	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseEntity<?> addUser(@RequestBody User user) {
//		userRepository.save(user);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//	
	@RequestMapping(value = "/users", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> listUsers() {
		Iterable<User> users = userRepository.findAll();
		List<User> respBody = new LinkedList<User>();
		for (User user : users) {
			respBody.add(user);
		}
		ResponseEntity<List<User>> usersResp = new ResponseEntity<List<User>>(respBody, HttpStatus.OK);		
		return usersResp;
	}
}
