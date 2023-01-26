package de.mushroomfinder.repository;


import de.mushroomfinder.entities.Spot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

	/*
	 * @Query("select sp from Spot sp where sp.idmushroom in :mushroomIds")
	 * List<Spot> findSpotsByMushroomIds(Long[] mushroomIds);
	 */
}