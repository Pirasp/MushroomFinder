package de.mushroomfinder.controller;

import de.mushroomfinder.entities.MushroomSpot;
import de.mushroomfinder.repository.MarkerRepository;
import de.mushroomfinder.repository.MushroomSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
	public String showContact(/*Model model, @RequestParam("id") Long id*/) {
		/*model.addAttribute("mushroomspot", mushroomSpotRepository.findById(id));*/
		return "addmap";
	}
	/*lang long from marker and id from user*/
	@PostMapping("/addmap")
	public String addMarker(@ModelAttribute MushroomSpot mushroomspot,
							@RequestParam("id") Long id,
							@RequestParam("name") String name,
							@RequestParam("picture") MultipartFile picture,
							@RequestParam("description") String description,
							@RequestParam("latitude") double latitude,
							@RequestParam("longitude") double longitude) throws IOException {
		/*Long testid = 1L;*/
		/*double testlatitude = 10.5;
		double testlongitude = 11;*/
		mushroomspot.setId(id);
		mushroomspot.setName(name);
		try {
			byte [] tmp = picture.getBytes();

			if(tmp.length > 64000){
				throw new IOException();
			}
			mushroomspot.setPicture(tmp);
		} catch (IOException e) {
			return "redirect:/addmap/errorPictureSize";
		}
		mushroomspot.setDescription(description);
		mushroomspot.setLatitude(latitude);
		mushroomspot.setLongitude(longitude);
		mushroomSpotRepository.save(mushroomspot);
		return "redirect:/addmap";

	}

	@RequestMapping("/addmap/errorPictureSize")
	public String errorPicsize(){
		return "/errorPictureSize";
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
							  ServletRequestDataBinder binder) throws ServletException {
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}
}
