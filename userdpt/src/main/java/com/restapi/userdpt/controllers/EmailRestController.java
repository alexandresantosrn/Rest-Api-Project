package com.restapi.userdpt.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.userdpt.entities.Email;
import com.restapi.userdpt.services.EmailService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/email")
public class EmailRestController {

	private final EmailService emailService;

	public EmailRestController(EmailService emailService) {
		this.emailService = emailService;
	}

	@Operation(description = "Envia um e-mail para o usuário destinatário.")
	@PostMapping
	public void sendEmail(Email email) {
		emailService.sendEmail(email);
	}
	
}
