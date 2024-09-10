package com.restapi.userdpt.dtos;

import com.restapi.userdpt.entities.Departament;
import com.restapi.userdpt.entities.User;

public class UserDTO {

	private Long id;
	private String name;
	private String email;
	private Departament department;

	public UserDTO() {
		
	}

	public UserDTO(Long id, String name, String email, Departament department) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.department = department;
	}

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
		department = user.getDepartment();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Departament getDepartment() {
		return department;
	}

	public void setDepartment(Departament department) {
		this.department = department;
	}

}
