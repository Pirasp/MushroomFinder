package de.mushroomfinder.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mushroomfinder.entities.Marker;
import de.mushroomfinder.repository.MarkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import de.mushroomfinder.entities.Spot;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class MapController {
	@Autowired
	private MushroomSpotRepository mushroomSpotRepository;


	@RequestMapping("/")
	public String start(){
		return"map";
	}
	
	@RequestMapping("/map")
	public String showContact() {
				
		return "map";
	}


	//ADDMAP
	@GetMapping("/addmap")
	public String showContact(Model model/*, @RequestParam("id") Long id*/) {
		/*model.addAttribute("mushroomspot", mushroomSpotRepository.findById(id));*/
		model.addAttribute("spot", new Spot());
		return "addmap";
	}
	/*lang long from marker and id from user*/
	@PostMapping("/addmap")
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
			return "redirect:/addmap/errorPictureSize";
		}
		spot.setDescription(description);
		spot.setLatitude(latitude);
		spot.setLongitude(longitude);
		mushroomSpotRepository.save(spot);
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
