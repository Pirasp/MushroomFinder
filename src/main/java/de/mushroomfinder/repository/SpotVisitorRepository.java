package de.mushroomfinder.repository;

import de.mushroomfinder.entities.Spot;
import de.mushroomfinder.entities.SpotVisitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotVisitorRepository extends JpaRepository<SpotVisitor, Long> {
    SpotVisitor findFirstBySpotOrderByVisitDateDesc(Spot spot);
    Long countBySpot(Spot spot);
}
