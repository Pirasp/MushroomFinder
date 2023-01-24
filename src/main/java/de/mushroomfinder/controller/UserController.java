package de.mushroomfinder.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/user/edit/{id}")
	public ModelAndView editUser(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/editUser");
		Optional<User> optUser = userRepository.findById(id);
		User user = optUser.get();
		mv.addObject("user", user);
		return mv;
	}
	
	@PostMapping("/user/edit/save")
	public String saveUser(@ModelAttribute User user) {
		Optional<User> optUser = userRepository.findById(user.getId());
		User existingUser = optUser.get();
		existingUser.setEmail(user.getEmail());
		existingUser.setName(user.getName());
		if(user.getNewPassword1().isBlank() == false && user.getNewPassword1().equals(user.getNewPassword2())) {
			  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			  String encodedPassword = passwordEncoder.encode(user.getNewPassword1());
			  existingUser.setPassword(encodedPassword);
		}else {
			System.out.println("New passwords do not match or are blank!");
		}
		userRepository.save(existingUser);
		return "redirect:/user/all";
	}
}
