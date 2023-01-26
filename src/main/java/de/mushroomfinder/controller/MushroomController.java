package de.mushroomfinder.controller;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.repository.LexiconRepository;
import de.mushroomfinder.service.MushroomLexiconAddService;
import de.mushroomfinder.service.MushroomLexiconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class MushroomController {

    private final MushroomLexiconAddService mushroomLexiconAddService;
    private final MushroomLexiconService mushroomLexiconService;

    @Autowired
    private LexiconRepository lexiconRepository;

    public MushroomController(MushroomLexiconAddService mushroomLexiconAddService,
                              MushroomLexiconService mushroomLexiconService) {

        this.mushroomLexiconAddService = mushroomLexiconAddService;
        this.mushroomLexiconService = mushroomLexiconService;
    }

    @GetMapping("/lexicon")
    public ModelAndView lexicon(@RequestParam(value = "searchTerm", defaultValue = " ") String searchTerm){
        if(searchTerm.equals(" ")){
            List<Mushroom> mushrooms = lexiconRepository.findAll();
            return new ModelAndView("lexicon", "mushrooms", mushrooms);
        }

        List<Mushroom> mushrooms = mushroomLexiconService.searchMushrooms(searchTerm);
        return new ModelAndView("lexicon", "mushrooms", mushrooms);
    }

    @GetMapping(value = "/mushrooms/picture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getMushroomPicture(@PathVariable("id") Long id) {

        Mushroom mushroom = mushroomLexiconService.getMushroomById(id);
        return mushroom.getPicture();
    }

    @GetMapping("/lexicon/add")
    public String addMushroom(Model model) {
        model.addAttribute("mushroom", new Mushroom());
        return "addLexiconEntry";
    }

    @PostMapping("/lexicon/add")
    public String create(@ModelAttribute Mushroom mushroom,
                         @RequestParam("picture")MultipartFile picture){
        try {
            byte [] tmp = picture.getBytes();

            if(tmp.length > 64000){
                throw new IOException();
            }
            mushroom.setPicture(tmp);
        } catch (IOException e) {
            return "redirect:/lexicon/add/errorPictureSize";
        }
        lexiconRepository.save(mushroom);
        return "redirect:/lexicon";
    }

    @PostMapping("/mushrooms/edit/{id}")
    public String editMushroom(@PathVariable("id") long id,
                               @ModelAttribute Mushroom mushroom,
                               @RequestParam("picture")MultipartFile picture,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request) throws IOException {
        try {
            byte [] tmp = picture.getBytes();

            if(tmp.length > 64000){
                throw new IOException();
            }
            mushroom.setPicture(tmp);
        } catch (IOException e) {
            return "redirect:/lexicon/add/errorPictureSize";
        }
        mushroom.setId(id);
        lexiconRepository.save(mushroom);
        return "redirect:/lexicon?searchTerm="+mushroom.getName();
    }

    @RequestMapping("/lexicon/add/errorPictureSize")
    public String errorPicsize(){
        return "/errorPictureSize";
    }

    @GetMapping("/lexicon/modify/{id}")
    public ModelAndView getMushroom(@PathVariable Long id) throws IllegalArgumentException{
        Mushroom mushroom = lexiconRepository.searchById(id);
        return new ModelAndView("modifyLexiconEntry", "mushroom", mushroom);
    }

    //this is needed for Spring to be able to use its Multipartfile to byte method, without it spring dies :D
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }
}