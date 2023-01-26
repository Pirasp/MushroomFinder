package de.mushroomfinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mushroomfinder.entities.Marker;
import de.mushroomfinder.repository.MarkerRepository;
import de.mushroomfinder.repository.MushroomSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import de.mushroomfinder.entities.Spot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Controller
public class MapController {
	@Autowired
	private MushroomSpotRepository mushroomSpotRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MarkerRepository markerRepository;


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

	//ADDMAP -----------------------------------------------------------------------------------------------------------
	@GetMapping("/map/add")
	public String showContact(Model model/*, @RequestParam("id") Long id*/) {
		/*model.addAttribute("mushroomspot", mushroomSpotRepository.findById(id));*/
		model.addAttribute("spot", new Spot());
		return "addmap";
	}
	/*lang long from marker and id from user*/
	@PostMapping("/map/add")
	public String addMarker(@ModelAttribute Spot spot,
							@RequestParam("name") String name,
							@RequestParam("picture") MultipartFile picture,
							@RequestParam("description") String description,
							@RequestParam("latitude") double latitude,
							@RequestParam("longitude") double longitude) throws IOException {
		spot.setName(name);
		try {
			byte [] tmp = picture.getBytes();

			if(tmp.length > 64000){
				throw new IOException();
			}
			spot.setPicture(tmp);
		} catch (IOException e) {
			return "redirect:/map/add/errorPictureSize";
		}
		spot.setDescription(description);
		spot.setLatitude(latitude);
		spot.setLongitude(longitude);
		mushroomSpotRepository.save(spot);
		return "redirect:/map/add";

	}

	@RequestMapping("/map/add/errorPictureSize")
	public String errorPicsize(){
		return "/errorPictureSize_addMap";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
							  ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
}
