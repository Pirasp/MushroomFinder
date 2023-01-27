package de.mushroomfinder.controller;

import de.mushroomfinder.entities.Comment;
import de.mushroomfinder.repository.MushroomSpotRepository;
import de.mushroomfinder.service.SpotService;
import net.bytebuddy.matcher.StringMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.service.SpotService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class SpotController {
    @Autowired
    MushroomSpotRepository mushroomSpotRepository;

    private final SpotService spotService;

    public SpotController(SpotService spotService){
        this.spotService = spotService;
    }

    @GetMapping("/spots")
    public ModelAndView spots(@RequestParam(value = "searchTerm", defaultValue = "") String searchTerm){
        List<Spot> spots = spotService.searchSpots(searchTerm);
        if(searchTerm.isEmpty()){
            List<Spot> all_spots = spotService.listSpots();
            return new ModelAndView("spots", "spots", all_spots);
        }
        return new ModelAndView("spots", "spots", spots);
    }

    @GetMapping(value = "/spots/picture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getMushroomPicture(@PathVariable("id") Long id) {

        Spot spot = spotService.getSpotbyId(id);
        return spot.getPicture();
    }

    @GetMapping("/spots/{id}")
    public ModelAndView getShowSpot(@PathVariable Long id) throws IllegalArgumentException{
        Spot spot = mushroomSpotRepository.searchById(id);
        return new ModelAndView("showSpotEntry", "spot", spot);
    }

    @GetMapping("/spots/edit/{id}")
    public ModelAndView getEditSpot(@PathVariable Long id) throws IllegalArgumentException{
        Spot spot = mushroomSpotRepository.searchById(id);
        return new ModelAndView("modifySpotEntry", "spot", spot);
    }
    @PostMapping("/spots/edit/{id}")
    public String editSpot(@PathVariable("id") long id,
                               @ModelAttribute Spot spot,
                               @RequestParam("picture") MultipartFile picture,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) throws IOException {
        try {
            byte [] tmp = picture.getBytes();

            if(tmp.length > 64000){
                throw new IOException();
            }
            spot.setPicture(tmp);
        } catch (IOException e) {
            return "redirect:/spot/errorPictureSize";
        }
        spot.setId(id);
        mushroomSpotRepository.save(spot);
        return "redirect:/spots?searchTerm="+spot.getName();
    }

    @GetMapping("/spots/delete/{id}")
    public String deleteSpot(@PathVariable("id") Long id, Model model) {
        Optional<Spot> optSpot = mushroomSpotRepository.findById(id);
        Spot spot = mushroomSpotRepository.searchById(id);

        mushroomSpotRepository.delete(spot);
        return "redirect:/spots";
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }
}
