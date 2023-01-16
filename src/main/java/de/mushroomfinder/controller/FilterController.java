package de.mushroomfinder.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.entities.Filter;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.FilterRepository;
import de.mushroomfinder.repository.UserRepository;

@Controller
public class FilterController {
	@Autowired
	FilterRepository filterRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/filter/")
	public ModelAndView addEditFilter(Principal principal) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		Integer id = oLoggedUser.get().getId();
		System.out.println("aktuelle Userid:" + id);
		Optional<Filter> optFilter = filterRepository.findFilterByIdUser(id);
		Filter filter;
		if(optFilter.isPresent()) {
			System.out.println("Bestehender Filter gefunden");
			filter = optFilter.get();
		}else {
			System.out.println("neuen Filter anlegen");
			filter = new Filter();
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("addEditFilter");
		mv.addObject("filter", filter);
		return mv;
	}
	
	@PostMapping("/filter/save")
	public String saveFilter(Principal principal, @ModelAttribute("filter") Filter filter) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		Optional<Filter> optFilter = filterRepository.findFilterByIdUser(oLoggedUser.get().getId());
		//If there is already a filter for the current user, set the existing filter's id as the id for the new filter to update the existing one in the database
		//else: set the current user for the newly created filter and save it as a new one
		if(optFilter.isPresent()) {
			filter.setId(optFilter.get().getId());
			filter.setUser(oLoggedUser.get());
		}else {
			filter.setUser(oLoggedUser.get());
		}
		filterRepository.save(filter);
		return "redirect:/map";
	}
}
