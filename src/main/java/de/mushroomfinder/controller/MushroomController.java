package de.mushroomfinder.controller;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.service.MushroomLexiconAddService;
import de.mushroomfinder.service.MushroomLexiconService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class MushroomController {

    private final MushroomLexiconAddService mushroomLexiconAddService;
    private final MushroomLexiconService mushroomLexiconService;

    public MushroomController(MushroomLexiconAddService mushroomLexiconAddService,
                              MushroomLexiconService mushroomLexiconService) {

        this.mushroomLexiconAddService = mushroomLexiconAddService;
        this.mushroomLexiconService = mushroomLexiconService;
    }

    @GetMapping("/lexicon")
    public ModelAndView lexicon(@RequestParam(value = "searchTerm", defaultValue = " ") String searchTerm){

        List<Mushroom> mushrooms = mushroomLexiconService.searchMushrooms(searchTerm);
        return new ModelAndView("lexicon", "mushrooms", mushrooms);
    }

    @GetMapping(value = "/mushrooms/picture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody String getMushroomPicture(@PathVariable("id") Long id) {

        Mushroom mushroom = mushroomLexiconService.getMushroomById(id);
        return mushroom.getPicture();
    }

    @GetMapping("/lexicon/add")
    public String addMushroom(Model model) {
        model.addAttribute("mushroom", new Mushroom());
        return "addLexiconEntry";
    }

    @PostMapping("/lexicon/add")
    public String addMushroom(@RequestParam("picture") MultipartFile picture,
                              @ModelAttribute Mushroom mushroom,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request) throws IOException {

        String fileName = picture.getOriginalFilename();
        String path = request.getServletContext().getRealPath("/");
        File uploadDir = new File(path + "/images/");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        // Create a file object to save the uploaded file
        File uploadFile = new File(uploadDir.getPath() + File.separator + fileName);
        try {
            // Save the uploaded file
            picture.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Set the file name as a redirect attribute
        redirectAttributes.addAttribute("fileName", fileName);

        mushroomLexiconAddService.addMushroom(mushroom);
        return "redirect:/lexicon";
    }
}