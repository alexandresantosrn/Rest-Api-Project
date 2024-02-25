package com.restapi.userdpt.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restapi.userdpt.dtos.UserDTO;
import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.exceptions.EntityNotFoundException;
import com.restapi.userdpt.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDTO createUser(User user) {
		User result = userRepository.save(user);
		UserDTO dto = new UserDTO(result);
		return dto;
	}

	public List<User> listUsers() {
		Sort sort = Sort.by("department").descending().and(Sort.by("name").ascending());
		return userRepository.findAll(sort);
	}

	public UserDTO listUserById(Long id) {
		User result = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: " + id));
		UserDTO dto = new UserDTO(result);
		return dto;
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}
}
