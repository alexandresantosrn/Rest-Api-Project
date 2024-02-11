package com.restapi.userdpt.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		User result = userRepository.save(user);
		return result;
	}

	public List<User> listUsers() {
		Sort sort = Sort.by("department").descending().and(Sort.by("name").ascending()); 
		List<User> result = userRepository.findAll(sort);
		return result;
	}

	public User listUserById(Long id) {
		User result = userRepository.findById(id).get();
		return result;
	}

	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public void updateUser(User user) {
		userRepository.save(user);
	}
}
