package de.mushroomfinder.repository;

import de.mushroomfinder.entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Integer> {


}