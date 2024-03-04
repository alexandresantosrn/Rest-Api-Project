package com.restapi.userdpt.services;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restapi.userdpt.dtos.UserDTO;
import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.exceptions.EntityNotFoundException;
import com.restapi.userdpt.exceptions.NegocioException;
import com.restapi.userdpt.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	public UserService(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	public UserDTO createUser(User user) {

		List<User> users = listUsers();
		for (User userLocal : users) {
			if (userLocal.getEmail().equals(user.getEmail())) {
				throw new NegocioException(
						"Cadastro não permitido. Já existe um usuário cadastrado com o e-mail informado.");
			}
		}

		User result = userRepository.save(user);
		return new UserDTO(result);
	}

	public List<User> listUsers() {
		Sort sort = Sort.by("department").descending().and(Sort.by("name").ascending());
		return userRepository.findAll(sort);
	}

	public UserDTO listUserById(Long id) {
		User result = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: " + id));
		return modelMapper.map(result, UserDTO.class);
	}

	public void deleteUserById(Long id) {
		UserDTO userDTO = listUserById(id);
		userRepository.deleteById(userDTO.getId());
	}

	public void updateUser(User user) {
		boolean userExists = false;

		List<User> users = listUsers();
		for (User userLocal : users) {
			if (Objects.equals(userLocal.getId(), user.getId())) {
				userRepository.save(user);
				userExists = true;
				break;
			}
		}

		if (!userExists) {
			throw new EntityNotFoundException("Id not found: " + user.getId());
		}
	}
}
