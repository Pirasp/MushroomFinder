package de.mushroomfinder.controller;

import de.mushroomfinder.entities.MushroomId;
import de.mushroomfinder.repository.IdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IdController {

        private final IdRepository mushroomRepository;

        public IdController(IdRepository mushroomRepository) {
            this.mushroomRepository = mushroomRepository;
        }

        @GetMapping("/mushroomIds")
        public String list(Model model) {
            model.addAttribute("mushrooms", mushroomRepository.findAll());
            return "mushroomIds/idRequests";
        }

        @GetMapping("/mushroomIds/new")
        public String newMushroom(Model model) {
            model.addAttribute("mushroom", new MushroomId());
            return "mushroomIds/addIdRequest";
        }

        @PostMapping("/mushroomIds")
        public String create(@ModelAttribute MushroomId mushroomId) {
            mushroomRepository.save(mushroomId);
            return "redirect:/mushroomIds/idRequests";
        }
}
