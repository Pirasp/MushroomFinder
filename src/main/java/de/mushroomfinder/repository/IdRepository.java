package de.mushroomfinder.repository;

import de.mushroomfinder.entities.MushroomId;
import org.springframework.data.repository.CrudRepository;

public interface IdRepository extends CrudRepository<MushroomId, Long> {
}
