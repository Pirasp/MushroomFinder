package de.mushroomfinder.controller.rest;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.service.MushroomLexiconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mushrooms")
public class LexiconRestController {

    @Autowired
    private MushroomLexiconService mushroomLexiconService;

    @GetMapping("/{id}")
    public ResponseEntity<Mushroom> getMushroomById(@PathVariable("id") Long id) {
        Mushroom mushroom = mushroomLexiconService.getMushroomById(id);
        if (mushroom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mushroom, HttpStatus.OK);
    }
}