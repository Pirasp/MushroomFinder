/*
package de.mushroomfinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import de.mushroomfinder.model.User;
import de.mushroomfinder.repository.UserRepository;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderImpl passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoderImpl passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user){
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    public boolean isValid(Object o){
        User user = (User) o;
        String email = user.getEmail();
        String password = user.getPassword();

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$")) {
            return false;
        }
        else if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
            return false;
        }
        else return true;



    }
}
*/

package de.mushroomfinder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.mushroomfinder.config.MyUserDetails;
import de.mushroomfinder.model.User;
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

