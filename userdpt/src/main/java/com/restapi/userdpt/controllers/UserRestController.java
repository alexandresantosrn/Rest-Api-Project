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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/users-list")
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@Operation(description = "Salva um novo usuário na base de dados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Salva um novo usuário."),
			@ApiResponse(responseCode = "422", description = "Impede o cadastro caso já exista um usuário com e-mail informado.") })
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user) {
		userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(Messages.SUCESS.getMessage());
	}

	@Operation(description = "Busca todos os usuários.")
	@GetMapping
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista todos os usuários."),
			@ApiResponse(responseCode = "400", description = "Informa que a lista é vazia.") })
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = userService.listUsers();
		return ResponseEntity.ok(users);
	}

	@Operation(description = "Busca o usuário através do seu id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista o usuário através do id informado."),
			@ApiResponse(responseCode = "400", description = "Informa que não há usuário com o id informado.") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> listUserById(@PathVariable Long id) {
		UserDTO userDTO = userService.listUserById(id);
		return ResponseEntity.ok().body(userDTO);
	}

	@Operation(description = "Remove o usuário através do seu id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Remove o usuário através do id informado."),
			@ApiResponse(responseCode = "400", description = "Informa que não há usuário com o id informado.") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
		userService.deleteUserById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Message", Messages.REMOVED.getMessage());
		return ResponseEntity.noContent().headers(headers).build();
	}

	@Operation(description = "Atualiza o usuário na base de dados.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Atualiza o usuário."),
			@ApiResponse(responseCode = "400", description = "Informa que não há usuário com o id informado."),
			@ApiResponse(responseCode = "422", description = "Impede a atualização caso já exista um usuário com e-mail informado.") })
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return ResponseEntity.ok(Messages.UPDATED.getMessage());
	}

	public ResponseEntity<String> deleteUsers() {
		userService.deleteUsers();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Message", Messages.REMOVED.getMessage());
		return ResponseEntity.noContent().headers(headers).build();
	}
}
