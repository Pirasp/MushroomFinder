package de.mushroomfinder.service;

import de.mushroomfinder.entities.MushroomId;
import de.mushroomfinder.repository.IdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MushroomIdService {
    private final IdRepository idRepository;

    @Autowired
    public MushroomIdService(IdRepository idRepository) {
        this.idRepository = idRepository;
    }

    public MushroomId getMushroomIdbyId(Long id){
        return idRepository.searchById(id);
    }
}
