package com.restapi.userdpt.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.restapi.userdpt.arq.Messages;
import com.restapi.userdpt.dtos.UserDTO;
import com.restapi.userdpt.entities.User;
import com.restapi.userdpt.exceptions.ConflictException;
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

	/**
	 * Realiza o cadastro de um novo usuário.
	 * 
	 * @param user
	 * @return
	 */
	public UserDTO createUser(User user) {
		validarCamposUser(user);
		validarEmail(user);

		User userSaved = userRepository.save(user); // Salvando no repositório
		return new UserDTO(userSaved); // Convertendo User em UserDTO e retornando para a view.
	}

	/**
	 * Valida os campos obrigatórios para cadastro de um novo usuário.
	 * 
	 * @param user
	 */
	private void validarCamposUser(User user) {
		if (user.getName().equals("") || user.getEmail().equals("") || user.getDepartment() == null) {
			throw new NegocioException("Cadastro não permitido. Campos obrigatórios não foram informados.");
		}
	}

	/**
	 * Valida o e-mail do usuário. Não é possível cadastrar um novo usuário se o
	 * e-mail já estiver em uso.
	 * 
	 * @param user
	 */
	private void validarEmail(User user) {
		User userLocal = findUserByEmail(user.getEmail());

		if (userLocal != null) {
			throw new ConflictException(
					"Cadastro não permitido. Já existe um usuário cadastrado com o e-mail informado.");
		}

	}

	/**
	 * Retorna a instância de um usuário a partir do seu e-mail.
	 * 
	 * @param email
	 * @return
	 */
	private User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	/**
	 * Retorna a listagem de todos os usuários.
	 * 
	 * @return
	 */
	public List<User> listUsers() {
		Sort sort = Sort.by("department").descending().and(Sort.by("name").ascending()); // Ordenando por departamento
																							// (descendente) e nome
																							// (ascendente).
		List<User> users = userRepository.findAll(sort);
		if (users.isEmpty()) {
			throw new EntityNotFoundException("Não há registros de usuários na base de dados.");
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

}
