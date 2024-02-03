package com.restapi.userdpt.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.repositories.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Operation(description = "Busca todos os usuários.")
	@GetMapping
	public List<User> findAll() {
		List<User> result = userRepository.findAll();
		return result;
	}

	@Operation(description = "Busca o usuário através do seu id.")
	@GetMapping(value = "/{id}")
	public User findById(@PathVariable Long id) {
		User result = userRepository.findById(id).get();
		return result;
	}

	@Operation(description = "Salva um novo usuário na base de dados.")
	@PostMapping
	public User insert(@RequestBody User user) {
		User result = userRepository.save(user);
		return result;
	}

	@DeleteMapping
	public void deletById(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
}
