package de.mushroomfinder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.mushroomfinder.entities.Authority;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.UserRepository;
import de.mushroomfinder.util.Properties;

@Service
@Transactional
public class UserService{
	@Autowired
	private UserRepository userRepository;
	

	
	public boolean loginExists(String login) {
		return userRepository.findUserByLogin(login).isPresent();
	}
	
	public boolean emailExists(String email) {
		return userRepository.findByEmail(email)!=null;
	}
}
