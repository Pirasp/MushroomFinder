package de.mushroomfinder.repository;

import de.mushroomfinder.entities.Mushroom;
import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.SpotVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SpotVisitorRepository extends JpaRepository<SpotVisitor, Long> {
    SpotVisitor findFirstBySpotOrderByVisitDateDesc(SpotVisitor spotVisitor);
    Long countBySpot(SpotVisitor spotVisitor);
    SpotVisitor searchById(@Param("idspot") Long id);
}
