package de.mushroomfinder.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/profile")
	public String showProfile(Principal principal, Model model) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		User user = oLoggedUser.get();
		model.addAttribute("user", user);
		return "user/profile";
	}
	@RequestMapping("/user/all")
	public String showAllUsers(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "user/all";
	}
}
