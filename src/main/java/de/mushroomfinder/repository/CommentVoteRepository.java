package de.mushroomfinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.mushroomfinder.entities.CommentVote;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
	
	/*
	 * @Query("select sum(cv.vote) from CommentVote cv where cv.commentid =:id")
	 * Optional<Integer> findVotesByCommentId(Integer id);
	 */
	
	@Query("select cv from CommentVote cv where cv.user.id =:userid and cv.comment.id=:commentid")
	Optional<CommentVote> findVotesByUserAndCommentId(Integer userid, Integer commentid);
}
