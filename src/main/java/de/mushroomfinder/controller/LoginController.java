/*
package de.mushroomfinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import de.mushroomfinder.model.User;
import de.mushroomfinder.service.UserService;
import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(){
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        if(!userService.isValid(user)){
            */
/*model.addAttribute("error", "Invalid Input");*//*

            return "register";
        }
        userService.save(user);
        return "redirect:/success";
    }
}
*/
package de.mushroomfinder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import de.mushroomfinder.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import de.mushroomfinder.service.UserService;
import de.mushroomfinder.repository.UserRepository;

@Controller

public class LoginController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping ("/login")
    public String showLoginForm(User user, Model model) {
        System.out.println("Add user "+ user.getId());
        /*UserService.addUser(user);*/
        userRepository.save(user);
        model.addAttribute("msgs", "Added user");
        return "redirect:/map";
    }


    @RequestMapping ( method=RequestMethod.GET, value="/prelogout")
    public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {

        return "prelogout";
    }



}

