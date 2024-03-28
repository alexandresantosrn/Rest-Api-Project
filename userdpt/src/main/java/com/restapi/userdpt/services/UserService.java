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

		List<User> users = listUsers(false); // Listar os usuários sem disparar exceção.
		if (!users.isEmpty()) {
			for (User userLocal : users) {
				if (userLocal.getEmail().equals(user.getEmail())) {
					throw new NegocioException(
							"Cadastro não permitido. Já existe um usuário cadastrado com o e-mail informado.");
				}
			}
		}

		User userSaved = userRepository.save(user);
		return new UserDTO(userSaved); // Convertendo User em UserDTO.
	}

	public List<User> listUsers() {
		return listUsers(true);
	}

	public List<User> listUsers(boolean showException) {
		Sort sort = Sort.by("department").descending().and(Sort.by("name").ascending()); // Ordenando por departamento
																							// (descendente) e nome
																							// (ascendente).
		List<User> users = userRepository.findAll(sort);
		if (users.isEmpty() && showException) {
			throw new EntityNotFoundException("Users not found.");
		}

		return users;
	}

	public UserDTO listUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found: " + id));
		return modelMapper.map(user, UserDTO.class); // Retornando com modelMapper.
	}

	public void deleteUserById(Long id) {
		UserDTO userDTO = listUserById(id);

		if (userDTO == null) {
			throw new EntityNotFoundException("Id not found: " + id);
		}

		userRepository.deleteById(userDTO.getId());
	}

	public void updateUser(User user) {
		boolean userExists = false;

		List<User> users = listUsers(false); // Listar os usuários sem disparar exceção.
		if (!users.isEmpty()) {
			for (User userLocal : users) {
				if (Objects.equals(userLocal.getId(), user.getId())) {
					userRepository.save(user);
					userExists = true;
					break;
				}
			}
		}

		if (!userExists) {
			throw new EntityNotFoundException("Id not found: " + user.getId());
		}
	}
}
