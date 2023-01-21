package de.mushroomfinder.repository;

import de.mushroomfinder.entities.MushroomSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MushroomSpotRepository extends JpaRepository<MushroomSpot, Long> {
    MushroomSpot searchById(@Param("id") Long id);
}
