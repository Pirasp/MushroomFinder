package de.mushroomfinder.controller;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.SpotVisitor;
import de.mushroomfinder.repository.SpotVisitorRepository;
import de.mushroomfinder.repository.SpotRepository;
import de.mushroomfinder.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


@Controller
public class SpotVisitorController {
    @Autowired
    private SpotVisitorRepository spotVisitorRepository;
    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private SpotService spotService;

/*    @RequestMapping("/spots/visitor/add")
    public String addMushroom(@PathVariable Long id, Model model) {
        model.addAttribute("spotvisitor", new SpotVisitor());
        return "visitor";
    }*/
    @GetMapping(value = "spots/visitor/add/{id}")
    public ModelAndView addVisitor(@PathVariable("id") Long id, Model model) {
        ModelAndView mv = new ModelAndView();
        SpotVisitor spotVisitor = new SpotVisitor();
        Optional<Spot> spotOpt = spotRepository.findById(id);
        if(spotOpt.isPresent()==false) {
            mv.setViewName("map");
            mv.addObject("error", "The spot to be visited does not exist");
            return mv;
        }

        mv.setViewName("visitor");
        spotVisitor.setSpot(spotOpt.get());
        mv.addObject("spotvisitor", spotVisitor);
        return mv;
    }

    @PostMapping("/spots/visitor/add")
    public String addVisitor(@ModelAttribute ("spotVisitor") SpotVisitor spotVisitor, @RequestParam String visitorname, Model model){
        Optional<Spot> spotOpt = spotRepository.findById(spotVisitor.getSpot().getId());
        spotVisitor.setSpot(spotOpt.get());
        spotVisitor.setDate(LocalDate.now());
        spotVisitor.setVisitorname(visitorname);
        spotVisitorRepository.save(spotVisitor);
        /*System.out.println("Visitor: " + spotVisitor1.getVisitorname() + " hat Spot besucht. Spot: " + spotVisitor1.getSpot());*/
        return "redirect:/spots";
    }

}
