package com.restapi.userdpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapi.userdpt.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByEmail(String email);
	
}
