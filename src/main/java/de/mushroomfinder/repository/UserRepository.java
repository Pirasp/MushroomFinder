package de.mushroomfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import de.mushroomfinder.model.User;


/*@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


    Optional<User> findUserByLogin(String login);

    //Page<User> findAll(Pageable pageable);

    @Query("select u from User u where u.active=1 and ((u.name LIKE '%'||:name||'%') or (:name IS NULL) ) order by u.login asc")
    Page<User> findAllByNameWithPagination(String name, Pageable pageable);

}*/

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);
}