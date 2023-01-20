package de.mushroomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import de.mushroomfinder.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import de.mushroomfinder.service.UserService;
import de.mushroomfinder.repository.UserRepository;

@Controller

public class LoginController {

    @Autowired
    private UserRepository userRepository;

/*    @RequestMapping ("/login")
    public String showLoginForm(User user, Model model) {
        System.out.println("Add user "+ user.getId());
        *//*UserService.addUser(user);*//*
        userRepository.save(user);
        model.addAttribute("msgs", "Added user");
        return "redirect:/map";
    }*/
    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setPassword(user.getPassword());

        userRepository.save(user);

        return "map";
    }


    @RequestMapping ( method=RequestMethod.GET, value="/prelogout")
    public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {

        return "prelogout";
    }



}

