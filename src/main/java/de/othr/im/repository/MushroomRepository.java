package de.othr.im.repository;

import de.othr.im.entities.Mushroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MushroomRepository extends JpaRepository<Mushroom, Integer> {

    List<Mushroom> findByName(String name);
}
