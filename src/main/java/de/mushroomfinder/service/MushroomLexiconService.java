package de.mushroomfinder.service;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.repository.LexiconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MushroomLexiconService {
    private final LexiconRepository mushroomRepository;

    @Autowired
    public MushroomLexiconService(LexiconRepository mushroomRepository) {
        this.mushroomRepository = mushroomRepository;
    }

    public List<Mushroom> searchMushrooms(String searchTerm) {
        return mushroomRepository.searchByName(searchTerm);
    }

    public void save(Mushroom mushroom) {
        mushroomRepository.save(mushroom);
    }

    public Mushroom getMushroomById(Long id) {
        return mushroomRepository.searchById(id);
    }
}
