package com.restapi.userdpt.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restapi.userdpt.arq.Messages;
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
		validarEmail(user);

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
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Messages.ID_NOT_FOUND.getMessage() + id));
		return modelMapper.map(user, UserDTO.class); // Retornando com modelMapper.
	}

	public void deleteUserById(Long id) {
		UserDTO userDTO = listUserById(id);
		if (userDTO == null) {
			throw new EntityNotFoundException(Messages.ID_NOT_FOUND.getMessage() + id);
		}

		userRepository.deleteById(userDTO.getId());
	}

	public void updateUser(User user) {
		validarEmail(user);

		UserDTO userDTO = listUserById(user.getId());
		if (userDTO == null) {
			throw new EntityNotFoundException(Messages.ID_NOT_FOUND.getMessage() + user.getId());
		}

		userRepository.save(user);
	}
	
	public void deleteUsers() {	
		
		userRepository.deleteAll();
	}

	public void validarEmail(User user) {

		List<User> users = listUsers(false); // Listar os usuários sem disparar exceção.
		if (!users.isEmpty()) {
			for (User userLocal : users) {
				if (userLocal.getEmail().equals(user.getEmail()) && (!user.getId().equals(userLocal.getId()))) {
					throw new NegocioException(
							"Cadastro não permitido. Já existe um usuário cadastrado com o e-mail informado.");
				}
			}
		}
	}
}
