package de.mushroomfinder.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.mushroomfinder.entities.Filter;
import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.FilterRepository;
import de.mushroomfinder.repository.MushroomRepository;
import de.mushroomfinder.repository.UserRepository;

@Controller
public class FilterController {
	@Autowired
	FilterRepository filterRepository;
	
	@Autowired
	MushroomRepository mushroomRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/filter/")
	public ModelAndView addEditFilter(Principal principal) {
		List<Mushroom> mushrooms = mushroomRepository.findAll();
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
		mv.addObject("mushrooms", mushrooms);
		return mv;
	}
	
	@PostMapping("/filter/save")
	public String saveFilter(Principal principal, @ModelAttribute("filter") Filter filter, @RequestParam(value = "params[]", required=false) Long[] params) {
		
		List<Mushroom> mushrooms = new ArrayList<>();
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
		if(params != null) {
			for(int i = 0; i<params.length; i++) {
				System.out.println(params[i]);
				Optional<Mushroom> optMushroom = mushroomRepository.findById(params[i]);
				mushrooms.add(optMushroom.get());
			}
		}
		filter.setMushrooms(mushrooms);
		

		filterRepository.save(filter);
		return "redirect:/map";
	}
	@RequestMapping("/filter/delete")
	public String deleteFilter(Principal principal) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		Optional<Filter> optFilter = filterRepository.findFilterByIdUser(oLoggedUser.get().getId());
		if(optFilter.isPresent()) {
			filterRepository.deleteById(optFilter.get().getId());
		}
		return "redirect:/map";
	}

}
