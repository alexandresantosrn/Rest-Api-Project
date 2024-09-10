package com.restapi.userdpt.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.repositories.UserRepository;

@Controller
public class UserController {

	private final UserRepository userRepository;	
	
	public UserController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "users";
	}
	
}
