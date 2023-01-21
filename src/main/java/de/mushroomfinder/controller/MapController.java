package de.mushroomfinder.controller;

import de.mushroomfinder.entities.MushroomSpot;
import de.mushroomfinder.repository.MarkerRepository;
import de.mushroomfinder.repository.MushroomSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class MapController {
	@Autowired
	private MushroomSpotRepository mushroomSpotRepository;

	@RequestMapping("/")
	public String start(){
		return"addmap";
	}
	
	@RequestMapping("/addmap")
	public String showContact(Model model, @RequestParam("id") Long id) {
		model.addAttribute("mushroomspot", mushroomSpotRepository.findById(id));
		return "addmap";
	}

	@PostMapping("map/addMarker")
	public String addMarker(@ModelAttribute MushroomSpot mushroomspot,
							@RequestParam("id") Long id, @RequestParam("name") String name,
							@RequestParam("picture") MultipartFile picture,
							@RequestParam("description") String description,
							@RequestParam("latitude") double latitude,
							@RequestParam("longitude") double longitude) throws IOException {
		try{
			byte [] tmp = picture.getBytes();

			if(tmp.length > 64000){
				throw new IOException();
			}
			mushroomspot.setPicture(tmp);
		} catch (IOException e) {
			return "redirect:/addmap/add/errorPictureSize";
		}
		mushroomspot.setId(id);
		mushroomspot.setDescription(description);
		mushroomspot.setName(name);
		mushroomspot.setLatitude(latitude);
		mushroomspot.setLongitude(longitude);
		mushroomSpotRepository.save(mushroomspot);
		return "redirect:/addmap";

	}

	@RequestMapping("/addmap/add/errorPictureSize")
	public String errorPicsize(){
		return "/errorPictureSize";
	}
}
