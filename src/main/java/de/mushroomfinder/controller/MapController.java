package de.mushroomfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MapController {
	
	@RequestMapping("/map")
	public String showContact() {
				
		return "map";
	}
}
