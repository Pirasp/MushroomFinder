package de.othr.im.service;

import de.othr.im.entities.Mushroom;
import de.othr.im.repository.MushroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MushroomLexiconAddService {

    private final MushroomRepository mushroomRepository;

    @Autowired
    public MushroomLexiconAddService(MushroomRepository mushroomRepository) {
        this.mushroomRepository = mushroomRepository;
    }

    public void addMushroom(Mushroom mushroom) {
        mushroomRepository.save(mushroom);
    }
}