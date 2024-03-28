package com.restapi.userdpt.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.userdpt.arq.Messages;
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
	public ResponseEntity<String> createUser(@RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(Messages.SUCESS.getMessage());
	}

	@Operation(description = "Busca todos os usuários.")
	@GetMapping
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = userService.listUsers();
		return ResponseEntity.ok(users);
	}

	@Operation(description = "Busca o usuário através do seu id.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> listUserById(@PathVariable Long id) {
		UserDTO userDTO = userService.listUserById(id);
		return ResponseEntity.ok().body(userDTO);
	}

	@Operation(description = "Remove o usuário através do seu id.")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Message", Messages.REMOVED.getMessage());
		return ResponseEntity.noContent().headers(headers).build();
	}

	@Operation(description = "Atualiza o usuário na base de dados.")
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return ResponseEntity.ok(Messages.UPDATED.getMessage());
	}
}
