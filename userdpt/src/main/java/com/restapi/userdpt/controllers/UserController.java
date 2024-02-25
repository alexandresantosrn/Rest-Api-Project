package com.restapi.userdpt.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.userdpt.dtos.UserDTO;
import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(description = "Salva um novo usuário na base de dados.")
	@PostMapping
	public UserDTO createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@Operation(description = "Busca todos os usuários.")
	@GetMapping
	public List<User> listUsers() {
		return userService.listUsers();
	}

	@Operation(description = "Busca o usuário através do seu id.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> listUserById(@PathVariable Long id) {
		UserDTO result = userService.listUserById(id);
		return ResponseEntity.ok().body(result);
	}

	@Operation(description = "Remove o usuário através do seu id.")
	@DeleteMapping(value = "/{id}")
	public void deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
	}

	@Operation(description = "Atualiza o usuário na base de dados.")
	@PutMapping
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}
}
