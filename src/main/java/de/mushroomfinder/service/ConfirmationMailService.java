package de.mushroomfinder.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import de.mushroomfinder.entities.User;
import de.mushroomfinder.entities.VerificationToken;
import de.mushroomfinder.repository.VerificationTokenRepository;

@Service
public class ConfirmationMailService {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	public void confirmRegistration(User user) {
		String token = UUID.randomUUID().toString();
		this.createVerificationToken(user, token);
		
		String recipient = user.getEmail();
		String subject = "Registration Confirmation";
		String url = "http://127.0.0.1:8080/registrationConfirm?token=" + token;
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("MushroomFinder42@gmail.com");
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText("Confirm your sign up on MushroomFinder: " + url);
		System.out.println("Token: " + token);
		System.out.println("Send mail...");
		mailSender.send(email);
	}
	
	public VerificationToken getVerificationToken(String verificationToken) {
		return verificationTokenRepository.findByToken(verificationToken);
	}
	
	public void createVerificationToken(User user, String token) {
		VerificationToken newToken = new VerificationToken(user, token);
		verificationTokenRepository.save(newToken);
	}
}
