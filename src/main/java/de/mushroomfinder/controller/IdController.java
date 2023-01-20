package de.mushroomfinder.controller;

import de.mushroomfinder.entities.MushroomId;
import de.mushroomfinder.repository.IdRepository;
import de.mushroomfinder.service.MushroomIdAddService;
import de.mushroomfinder.service.MushroomIdService;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Controller
public class IdController {

    private final IdRepository mushroomRepository;
    private final MushroomIdAddService mushroomIdAddService;
    private final MushroomIdService mushroomIdService;

    public IdController(IdRepository mushroomRepository, MushroomIdAddService mushroomIdAddService, MushroomIdService mushroomIdService) {
        this.mushroomRepository = mushroomRepository;
        this.mushroomIdAddService = mushroomIdAddService;
        this.mushroomIdService = mushroomIdService;
    }

    @GetMapping("/mushroomIds")
    public String list(Model model) {
        model.addAttribute("mushroomIds", mushroomRepository.findAll());
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
                         @RequestParam("picture")MultipartFile picture){
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
        mushroomRepository.save(mushroomId);
        return "redirect:/mushroomIds";
    }

    @GetMapping(value = "mushroomIds/errorPictureSize")
    public String errorPicSize(){
        return "/mushroomIds/errorPictureSize";
    }

    //this is needed for Spring to be able to use its Multipartfile to byte method, without it spring dies :D
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

}
