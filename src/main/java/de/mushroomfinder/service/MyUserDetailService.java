
package de.mushroomfinder.service;
  
import java.util.Optional;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.mushroomfinder.config.MyUserDetails;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.UserRepository;
 
  
@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> oUser= userRepository.findUserByLogin(username);
		oUser.orElseThrow(()-> new UsernameNotFoundException("User "+username + "not found"));
		System.out.println("User found "+ oUser.get().getLogin());
		return new MyUserDetails(oUser.get());
	}

}
  
 