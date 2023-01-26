package de.mushroomfinder.service;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.repository.LexiconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MushroomLexiconAddService {

    private final LexiconRepository mushroomRepository;

    @Autowired
    public MushroomLexiconAddService(LexiconRepository mushroomRepository) {
        this.mushroomRepository = mushroomRepository;
    }

    public void addMushroom(Mushroom mushroom) {
        mushroomRepository.save(mushroom);
    }
}