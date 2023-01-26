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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.mushroomfinder.entities.Filter;
import de.mushroomfinder.entities.Marker;
import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.FilterRepository;
import de.mushroomfinder.repository.LexiconRepository;
import de.mushroomfinder.repository.SpotRepository;
import de.mushroomfinder.repository.UserRepository;

@Controller
public class FilterController {
	@Autowired
	FilterRepository filterRepository;
	
	@Autowired
	LexiconRepository mushroomRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SpotRepository spotRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public List<Spot> filteredMarkers;
	
	@GetMapping("/filter/")
	public ModelAndView addEditFilter(Principal principal) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		Long id = oLoggedUser.get().getId();
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
		mv.setViewName("filter/addEditFilter");
		mv.addObject("filter", filter);
		List<Mushroom> mushrooms = mushroomRepository.findAll();
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
		return "redirect:/spots/filtered";
	}
	@RequestMapping("/filter/delete")
	public String deleteFilter(Principal principal) {
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		Optional<Filter> optFilter = filterRepository.findFilterByIdUser(oLoggedUser.get().getId());
		if(optFilter.isPresent()) {
			filterRepository.deleteById(optFilter.get().getId());
		}
		return "redirect:/spots/filtered";
	}
	
	@GetMapping("/spots/filtered")
	public ModelAndView showSpots(Principal principal) {
		this.filteredMarkers = new ArrayList<>();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("filter/filteredSpots");
		Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
		
		Optional<Filter> oFilter = filterRepository.findFilterByIdUser(oLoggedUser.get().getId());
		List<Spot> spots = new ArrayList<>();
		if(oFilter.isPresent()) {
			spots = spotRepository.findAll();
			List<Mushroom> filterMush = oFilter.get().getMushrooms();
			if(filterMush.isEmpty() == false) {
				List<Spot> filteredSpots = new ArrayList<>();
				for(int i = 0; i<filterMush.size(); i++) {
					for(int j = 0; j<spots.size(); j++) {
						try {
							if(spots.get(j).getMushroom().getId()==filterMush.get(i).getId()) {
								filteredSpots.add(spots.get(j));
							}
						}catch (Exception e) {
							
						}

					}
					
				}
				
				spots = filteredSpots;
			}else {
				spots = spotRepository.findAll();
			}

			
			//Calculate distances
			List<Spot> spotsWithin = new ArrayList<>();
			double spotLat;
			double spotLng;
			double distance;
			//Get Entries within the distance
			for(int i = 0; i<spots.size(); i++) {
	
				spotLat = spots.get(i).getLatitude();
				spotLng = spots.get(i).getLongitude();
			    final int R = 6371; // Radius of the earth
	
			    double latDistance = Math.toRadians(spotLat - oFilter.get().getLatitude());
			    double lonDistance = Math.toRadians(spotLng - oFilter.get().getLongitude());
			    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			            + Math.cos(Math.toRadians(oFilter.get().getLatitude())) * Math.cos(Math.toRadians(spotLat))
			            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
			    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			    distance = R * c;
	
			    System.out.println("Distanz:" + distance);
				if(distance<=oFilter.get().getDistance()) {
					spotsWithin.add(spots.get(i));
				//Calculate distances
				System.out.println("spotsWithin: " + spotsWithin);
				mv.addObject("spots", spotsWithin);
				
				}
			}
			this.filteredMarkers = spotsWithin;
			return mv;
		}
		else {
			List<Spot> allSpots = spotRepository.findAll();
			System.out.println("allSpots(no Filter): " + allSpots);
			mv.addObject("spots", allSpots);
			this.filteredMarkers = allSpots;
			return mv;
		}
	}
	
	@RequestMapping("/filteredMarkers")
	@ResponseBody
	public String markers() throws JsonProcessingException {
		List<Marker> markers = new ArrayList<>();
		for(int i = 0; i< this.filteredMarkers.size(); i++) {
			Marker marker = new Marker();
			marker.setId(this.filteredMarkers.get(i).getId());
			marker.setDescription(this.filteredMarkers.get(i).getDescription());
			marker.setIdmushroom(this.filteredMarkers.get(i).getMushroom().getId().intValue());
			marker.setLatitude(this.filteredMarkers.get(i).getLatitude());
			marker.setLongitude(this.filteredMarkers.get(i).getLongitude());
			markers.add(marker);
			
		}
		System.out.println(objectMapper.writeValueAsString(markers));
		return objectMapper.writeValueAsString(markers);
	}


}
