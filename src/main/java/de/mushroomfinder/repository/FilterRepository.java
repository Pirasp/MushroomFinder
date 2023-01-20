package de.mushroomfinder.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.mushroomfinder.entities.Filter;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Integer>{
	
	@Query("select f from Filter f where f.user.id=:iduser")
	Optional<Filter> findFilterByIdUser(Integer iduser); 
}
