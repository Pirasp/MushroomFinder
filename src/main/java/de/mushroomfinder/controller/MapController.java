package de.mushroomfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MapController {
	@RequestMapping("/")
	public String start(){
		return"addmap";
	}
	
	@RequestMapping("/addmap")
	public String showContact() {
				
		return "addmap";
	}
}
