package com.restapi.userdpt.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.restapi.userdpt.entities.Email;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(Email email) {
		var message = new SimpleMailMessage();
		message.setFrom("noreply@email.com");
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getBody());
		mailSender.send(message);
	}

}
