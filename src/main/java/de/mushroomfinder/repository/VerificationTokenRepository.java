package de.mushroomfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.mushroomfinder.entities.User;
import de.mushroomfinder.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);
	
	VerificationToken findByUser(User user);
}
