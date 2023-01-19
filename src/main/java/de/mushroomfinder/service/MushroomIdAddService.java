package de.mushroomfinder.service;

import de.mushroomfinder.entities.MushroomId;
import de.mushroomfinder.repository.IdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MushroomIdAddService {
    private final IdRepository idRepository;

    @Autowired
    public MushroomIdAddService(IdRepository idRepository) {
        this.idRepository = idRepository;
    }

    public void addMushroomId (MushroomId mushroomId){
        idRepository.save(mushroomId);
    }
}
