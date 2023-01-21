package de.mushroomfinder.repository;

import de.mushroomfinder.entities.MushroomSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MushroomSpotRepository extends JpaRepository<MushroomSpot, Long> {
    /*List<MushroomSpot> findAll(@Param("id") Long id);*/
    MushroomSpot searchById(@Param("id") Long id);
}
