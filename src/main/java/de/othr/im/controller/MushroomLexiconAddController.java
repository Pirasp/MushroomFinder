package de.othr.im.controller;

import de.othr.im.entities.Mushroom;
import de.othr.im.service.MushroomLexiconAddService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MushroomLexiconAddController {

    private final MushroomLexiconAddService mushroomLexiconAddService;

    public MushroomLexiconAddController(MushroomLexiconAddService mushroomService) {
        this.mushroomLexiconAddService = mushroomService;
    }

    @GetMapping("/lexicon/add")
    public String addMushroom(Model model) {
        model.addAttribute("mushroom", new Mushroom());
        return "addLexiconEntry";
    }

    @PostMapping("/mushrooms/add")
    public String addMushroom(@ModelAttribute Mushroom mushroom) {
        mushroomLexiconAddService.addMushroom(mushroom);
        return "redirect:/mushrooms";
    }
}