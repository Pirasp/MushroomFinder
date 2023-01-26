package de.mushroomfinder.service;

import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.repository.MushroomSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotService {
    private final MushroomSpotRepository mushroomSpotRepository;

    @Autowired
    public SpotService(MushroomSpotRepository mushroomSpotRepository){
        this.mushroomSpotRepository = mushroomSpotRepository;
    }

    public List<Spot> listSpots(){
        return mushroomSpotRepository.findAll();
    }
    public List<Spot> searchSpots(String searchTerm){
        return mushroomSpotRepository.searchByName(searchTerm);
    }

    public Spot getSpotbyId(Long id){
        return mushroomSpotRepository.searchById(id);
    }

    public void save (Spot spot) {
        mushroomSpotRepository.save(spot);
    }
}
