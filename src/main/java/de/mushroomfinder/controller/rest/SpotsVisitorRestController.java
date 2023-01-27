package de.mushroomfinder.controller.rest;


import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.SpotVisitor;
import de.mushroomfinder.repository.MushroomSpotRepository;
import de.mushroomfinder.repository.SpotVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class SpotsVisitorRestController {
    @Autowired
    SpotVisitorRepository spotVisitorRepository;

    @GetMapping("/total/{spotId}")
    public ResponseEntity<SpotVisitor> getTotalVisitors(@PathVariable Long spotId){
        SpotVisitor spotVisitor = spotVisitorRepository.searchById(spotId);
        if (spotVisitor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SpotVisitor totalVisitors = spotVisitorRepository.findFirstByidspotOrderByDateDesc(spotId);
        return new ResponseEntity<>(totalVisitors, HttpStatus.OK);
    }

    @GetMapping("/last/{spotId}")
    public ResponseEntity<Long> getLastVisitor(@PathVariable Long spotId){
        SpotVisitor spotVisitor = spotVisitorRepository.searchById(spotId);
        if (spotVisitor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Long lastVisitor = spotVisitorRepository.countById(spotId);
        return new ResponseEntity<>(lastVisitor, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SpotVisitor>> getAllVisitor(){
        List<SpotVisitor> spotVisitor = spotVisitorRepository.findAll();
        if (spotVisitor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(spotVisitor, HttpStatus.OK);
    }
}
