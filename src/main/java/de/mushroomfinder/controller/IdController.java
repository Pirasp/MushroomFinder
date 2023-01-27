package de.mushroomfinder.controller;

import de.mushroomfinder.entities.MushroomId;
import de.mushroomfinder.entities.User;
import de.mushroomfinder.repository.IdRepository;
import de.mushroomfinder.repository.UserRepository;
import de.mushroomfinder.service.MushroomIdAddService;
import de.mushroomfinder.service.MushroomIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Controller
public class IdController {

    private final IdRepository idRepository;
    private final MushroomIdAddService mushroomIdAddService;
    private final MushroomIdService mushroomIdService;

    @Autowired
    UserRepository userRepository;

    public IdController(IdRepository idRepository, MushroomIdAddService mushroomIdAddService, MushroomIdService mushroomIdService) {
        this.idRepository = idRepository;
        this.mushroomIdAddService = mushroomIdAddService;
        this.mushroomIdService = mushroomIdService;
    }

    @GetMapping("/mushroomIds")
    public String list(Model model) {
        model.addAttribute("mushroomIds", idRepository.findAll());
        return "mushroomIds/idRequests";
    }

    @GetMapping("/mushroomIds/add")
    public String newMushroom(Model model) {
        model.addAttribute("mushroomId", new MushroomId());
        return "mushroomIds/addIdRequest";
    }

    @GetMapping(value = "/mushroomIds/picture/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getMushroomPicture(@PathVariable("id") Long id,
                                                   HttpServletResponse response) {

        response.setContentType("image/jpeg");
        MushroomId mushroomId = mushroomIdService.getMushroomIdbyId(id);
        return mushroomId.getPicture();
    }

    @PostMapping("/mushroomIds/add")
    public String create(@ModelAttribute MushroomId mushroomId,
                         @RequestParam("picture")MultipartFile picture,
                         Principal principal){
        try {
            byte [] tmp = picture.getBytes();

            if(tmp.length > 64000){
                throw new IOException();
            }
            mushroomId.setPicture(tmp);
        } catch (IOException e) {
            return "redirect:/mushroomIds/errorPictureSize";
        }
        mushroomId.setDate(LocalDateTime.now(ZoneOffset.UTC));
        mushroomId.setStatus(0);
        Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
        if(oLoggedUser.isPresent()) {
            mushroomId.setUserId(oLoggedUser.get().getId());
        }
        idRepository.save(mushroomId);
        return "redirect:/mushroomIds";
    }

    @GetMapping(value = "mushroomIds/errorPictureSize")
    public String errorPicSize(){
        return "mushroomIds/errorPictureSize";
    }

    @GetMapping("/mushroomIds/solve/{id}")
    public String viewMushroomId(@PathVariable Long id, Model model) {
        MushroomId mushroomId = idRepository.findById(id).orElse(null);
        model.addAttribute("mushroomId", mushroomId);
        return "mushroomIds/idAnswer";
    }

    @PostMapping("/mushroomIds/solve/{id}")
    public String saveMushroomId(@ModelAttribute MushroomId mushroomId,
                                 Principal principal) {

        Optional<User> oLoggedUser = userRepository.findUserByLogin(principal.getName());
        if(oLoggedUser.isPresent()) {
            mushroomId.setSolverid(oLoggedUser.get().getId());
        }

        MushroomId existingMushroomId = idRepository.findById(mushroomId.getId()).orElse(null);
        mushroomId.setUserId(existingMushroomId.getUserId());
        mushroomId.setLocation(existingMushroomId.getLocation());
        if(existingMushroomId != null) {
            mushroomId.setPicture(existingMushroomId.getPicture());
        }

        idRepository.save(mushroomId);
        return "redirect:/mushroomIds";
    }


    //this is needed for Spring to be able to use its Multipartfile to byte method, without it spring dies :D
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

}
