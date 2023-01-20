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
public class UserService implements UserDetailsService{


    @Autowired
    private UserRepository userRepository;

/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<User> oUser= userRepository.findUserByLogin(username);
        oUser.orElseThrow(()-> new UsernameNotFoundException("Not found "+username));
        System.out.println("User found at the UserDetailsService="+ oUser.get().getLogin());
        return new MyUserDetails(oUser.get());
    }

    public void addUser(User user){
        userRepository.save(user);
    }*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(user);
    }


}

