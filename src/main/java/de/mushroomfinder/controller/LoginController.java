package de.mushroomfinder.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import de.mushroomfinder.entities.Authority;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.entities.VerificationToken;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.repository.UserRepository;
import de.mushroomfinder.service.ConfirmationMailService;
import de.mushroomfinder.service.UserService;
import de.mushroomfinder.util.Properties;

@Controller

public class LoginController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ConfirmationMailService confirmationMailService;

	
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
	  public String processRegister(@ModelAttribute @Valid User user, Model model, HttpServletRequest request) {
		  System.out.println("process register");
		  System.out.println("password1: " + user.getNewPassword1());
		  System.out.println("password2: " + user.getNewPassword2());
		  
		  //Check if the passwords match
		  if(user.getNewPassword1().equals(user.getNewPassword2()) == false) {
			  System.out.println("Passwords do not match");
			  model.addAttribute("error", "Passwords do not match");
			  return "registration";
		  }
		  
		  //Check if Login name already exists
		  if (userService.loginExists(user.getLogin())) {
			  model.addAttribute("error", "Login " + user.getLogin() + " already exists!");
			  return "registration";
		  }
		  //Check if eMail address already exists
		  if(userService.emailExists(user.getEmail())) {
			  model.addAttribute("error", "EMail address " + user.getEmail() + " already exists!");
			  return "registration";
		  }
		//encrypt password
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); String
		  encodedPassword = passwordEncoder.encode(user.getNewPassword1());
		  System.out.println("encoded Passwort:" + encodedPassword);
		  user.setPassword(encodedPassword);
		  //set new user as active by default
		  //user.setActive(1);
		  
		  //Set default role User
		  Authority authority = new Authority(); authority.setId(Properties.USER_TYPE_USER);
		  List<Authority> authorities = new ArrayList<>();
		  authorities.add(authority);
		  user.setMyauthorities(authorities);
		  //save User		  
		  userRepository.save(user);
		  System.out.println("AppUrl: " + request.getContextPath());
		  
		  //Send confirmation Mail
		  confirmationMailService.confirmRegistration(user);
		  
		  return "redirect:/login"; 
	  }
	  
	@GetMapping("/registrationConfirm")
	public String confirmRegistration(Model model, @RequestParam("token") String token) {
		System.out.println("start confirm");
		VerificationToken verificationToken = confirmationMailService.getVerificationToken(token);
		System.out.println("found token");
		if(verificationToken == null) {
			System.out.println("invalid token");
			model.addAttribute("error", "Token " + token + "does not exist!");
			return "register";
		}
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime())<=0) {
			System.out.println("Token expired");
			model.addAttribute("error", "Token expired!");
			return "register";
		}
		user.setActive(1);
		userRepository.save(user);
		return "redirect:/login";
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
    	return "redirect:/map";
    }

}

