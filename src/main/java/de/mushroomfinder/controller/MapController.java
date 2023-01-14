package de.mushroomfinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mushroomfinder.entities.Marker;
import de.mushroomfinder.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class MapController {

	@Autowired
	private MarkerRepository markerRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping("/")
	public String start(Model model){
		model.addAttribute("markers", markerRepository.findAll());
		return"map";
	}
	
	@RequestMapping("/map")
	public String map(Model model) {
		model.addAttribute("markers", markerRepository.findAll());
		return "map";
	}

	@RequestMapping("/markers")
	@ResponseBody
	public String markers() throws JsonProcessingException {
		List<Marker> markers = markerRepository.findAll();
		System.out.println(objectMapper.writeValueAsString(markers));
		return objectMapper.writeValueAsString(markers);
	}
}
