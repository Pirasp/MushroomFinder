package de.mushroomfinder.controller.rest;


import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.SpotVisitor;
import de.mushroomfinder.repository.MushroomSpotRepository;
import de.mushroomfinder.repository.SpotVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visitors")
public class SpotsVisitorRestController {
    @Autowired
    MushroomSpotRepository mushroomSpotRepository;
    @Autowired
    SpotVisitorRepository spotVisitorRepository;
    @GetMapping("/lastvisitor/{spotId}")
    public SpotVisitor getLastVisitor(@PathVariable Long spotId) {
        Spot spot = mushroomSpotRepository.findById(spotId).orElse(null);
        if (spot == null) {
            // Return appropriate response for spot not found
        }
        return spotVisitorRepository.findFirstBySpotOrderByVisitDateDesc(spot);
    }

    @GetMapping("/visitorcount/{spotId}")
    public Long getVisitorCount(@PathVariable Long spotId) {
        Spot spot = mushroomSpotRepository.findById(spotId).orElse(null);
        if (spot == null) {
            // Return appropriate response for spot not found
        }
        return spotVisitorRepository.countBySpot(spot);
    }
}
