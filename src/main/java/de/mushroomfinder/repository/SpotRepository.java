package de.mushroomfinder.repository;


import de.mushroomfinder.entities.Spot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {


}