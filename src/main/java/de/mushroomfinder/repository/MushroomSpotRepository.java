package de.mushroomfinder.repository;

import de.mushroomfinder.entities.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MushroomSpotRepository extends JpaRepository<Spot, Long> {
    /*List<MushroomSpot> findAll(@Param("id") Long id);*/
    Spot searchById(@Param("id") Long id);
}
