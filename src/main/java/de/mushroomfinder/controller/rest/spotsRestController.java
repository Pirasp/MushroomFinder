package de.mushroomfinder.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.repository.SpotRepository;

@RestController
@RequestMapping("/api/spots")
public class spotsRestController {
	@Autowired
	SpotRepository spotRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Spot>> findSpotsInArea(@RequestParam("lat") double lat,
														@RequestParam("lng") double lng,
														@RequestParam("dist") int distInKm){
		List<Spot> allSpots = spotRepository.findAll();
		
		List<Spot> spotsWithin = new ArrayList<>();
		double spotLat;
		double spotLng;
		double distance;
		//Get Entries within the distance
		for(int i = 0; i<allSpots.size(); i++) {

			spotLat = allSpots.get(i).getLatitude();
			spotLng = allSpots.get(i).getLongitude();
		    final int R = 6371; // Radius of the earth

		    double latDistance = Math.toRadians(spotLat - lat);
		    double lonDistance = Math.toRadians(spotLng - lng);
		    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
		            + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(spotLat))
		            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    distance = R * c;

		    System.out.println("Distanz:" + distance);
			if(distance<=distInKm) {
				spotsWithin.add(allSpots.get(i));
			}
		}
		return new ResponseEntity<>(spotsWithin, HttpStatus.OK);
	}
}
