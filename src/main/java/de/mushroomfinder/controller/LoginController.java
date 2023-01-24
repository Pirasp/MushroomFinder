package de.mushroomfinder.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import de.mushroomfinder.entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.repository.UserRepository;

@Controller

public class LoginController {

    @Autowired
    private UserRepository userRepository;

	
    @RequestMapping ("/login")
	public String showLoginForm() {
    	
    	return "login"; 
	}
 
    @GetMapping("/register")
    public ModelAndView showRegistrationForm(Model model){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registration");
    	mv.addObject("user", new User());
        
        return mv;
    }

	
	  @PostMapping("/register/save")
	  public String processRegister(@ModelAttribute User user, Model model) {
		  System.out.println("process register");
		  System.out.println("password1: " + user.getNewPassword1());
		  System.out.println("password2: " + user.getNewPassword2());
		  if(user.getNewPassword1().equals(user.getNewPassword2()) == false) {
			  System.out.println("Passwords do not match");
			  model.addAttribute("error", "Passwords do not match");
			  return "registration";
		  }
		  
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		  String encodedPassword = passwordEncoder.encode(user.getNewPassword1());
		  System.out.println("encoded Passwort:" + encodedPassword);
		  user.setPassword(encodedPassword);
		  user.setActive(1);
		  userRepository.save(user);
		  
	  return "login"; 
	  }
	 

    @RequestMapping ( method=RequestMethod.GET, value="/prelogout")
    public String showPreLogout(HttpServletRequest request, HttpServletResponse response) {

        return "prelogout";
    }

    @RequestMapping("/index")
    public String showIndex() {
    	return "index";
    }
    
    @RequestMapping("/setSession")
    public String setSession(HttpSession session, Principal principal) {
    	session.setAttribute("login", principal.getName());
    	return "redirect:map";
    }

}

