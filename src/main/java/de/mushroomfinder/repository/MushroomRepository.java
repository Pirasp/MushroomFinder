package de.mushroomfinder.repository;

import de.mushroomfinder.entities.Mushroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MushroomRepository extends JpaRepository<Mushroom, Long> {
    List<Mushroom> searchByName(@Param("name") String name);

    Mushroom searchById(@Param("id") Long id);

}
