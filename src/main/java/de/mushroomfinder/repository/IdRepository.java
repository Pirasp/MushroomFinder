package de.mushroomfinder.repository;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.entities.MushroomId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IdRepository extends CrudRepository<MushroomId, Long> {
    MushroomId getMushroomIdById(Long id);

    MushroomId searchById(@Param("id")Long id);

}
